package us.lsi.flujosparalelos;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import us.lsi.common.Enumerate;
import us.lsi.common.IntPair;
import us.lsi.common.IntTrio;
import us.lsi.common.MutableType;
import us.lsi.common.Pair;

public class Streams2 {
	
	/**
	 * @param first Un stream 
	 * @param b Una secuencia adicional de stream
	 * @param <A> El tipo de los elementos de la secuencia
	 * @param <B> El tipo de los elementos de la secuencia
	 * @param <C> El tipo de los elementos de la secuencia resultante
	 * @param zipper Fución de combinación
	 * @return Un stream formado por los elementos obtenidos combinando 
	 * uno a uno los elementos de las secuencias de entrada
	 */	
	public static <L, R, T> Stream<T> zip(Stream<L> leftStream, Stream<R> rightStream, BiFunction<L, R, T> combiner) {
		Spliterator<L> lefts = leftStream.spliterator();
		Spliterator<R> rights = rightStream.spliterator();
		return StreamSupport.stream(Spliterators2.zip(lefts, rights, combiner), leftStream.isParallel() || rightStream.isParallel());
	}
	
	
	
	/**
	 * @param sm Un String
	 * @param start Primer indice de la enumrracion
	 * @param <E> El tipo de los elementos de la secuencia
	 * @return Un stream formado por los pares de elementos 
	 * formados por un elemento y el entero que indica su posición
	 */


	public static <E> Stream<Enumerate<E>> enumerate(Stream<E> stream, Integer start) {
		Stream<Integer> st = Stream.iterate(start, e -> e + 1);
		return zip(stream, st, (e, n) -> Enumerate.of(n, e));
	}
	
	/**
	 * @param sm Un String
	 * @param <E> El tipo de los elementos de la secuencia
	 * @return Un stream formado por los pares de elementos 
	 * formados por un elemento y el entero que indica su posición
	 */

	public static <E> Stream<Enumerate<E>> enumerate(Stream<E> stream) {
		return enumerate(stream, 0);
	}
	
	/**
	 * @param <T> Tipo de los elementos del primer stream
	 * @param <U> Tipo de los elementos del segundo stream
	 * @param <R> Tipo de los elementos del stream resultado
	 * @param s1 Un Stream
	 * @param s2 Un Segundo Stream
	 * @param f Una Bifunction que opera un elemento del primer stream con un del segundo para 
	 * dar un resultado
	 * @return El resultado de operar los pares posibles de s1 y s2 con la bifunción f
	 */
	public static <T, U, R> Stream<R> cartesianProduct(Stream<T> s1, Stream<U> s2, BiFunction<T, U, R> f) {
		List<U> ls = s2.collect(Collectors.toList());
		return s1.flatMap(x->ls.stream().map(y->f.apply(x,y)));
	}
	
	public static <E> Stream<Pair<E,E>> cartesianProduct(Stream<E> s1) {
		List<E> ls = s1.collect(Collectors.toList());
		return ls.stream().flatMap(x->ls.stream().map(y->Pair.of(x,y)));
	}

	/**
	 * @param <T> Tipo de los elementos del primer stream
	 * @param <U> Tipo de los elementos del segundo stream
	 * @param <R> Tipo de los elementos del stream resultado
	 * @param s1 Una colección
	 * @param s2 Una segunda colección
	 * @param f Una Bifunction que opera un elemento del primer stream con un del segundo para 
	 * dar un resultado
	 * @return El resultado de operar los pares posibles de s1 y s2 con la bifunción f
	 */
	public static <T, U, R> Stream<R> cartesianProduct(Collection<T> s1, Collection<U> s2, BiFunction<T, U, R> f) {
		return s1.stream().flatMap(x->s2.stream().map(y->f.apply(x,y)));
	}

	public static <T> Stream<Pair<T,T>> cartesianProduct(Collection<T> s1) {
		return cartesianProduct(s1,s1,(x,y)->Pair.of(x, y));
	}
	
	/**
	 * @param sm Un String
	 * @param <E> El tipo de los elementos de la secuencia
	 * @return Un stream formado por los pares de elementos formados por un elemento y el siguiente en sm
	 */
	public static <E> Stream<Pair<E,E>> consecutivePairs(Stream<E> sm) {
		MutableType<E> rf = MutableType.of(null);
		Stream<Pair<E,E>> r = sm.map(e->Pair.of(rf.newValue(e), e)).filter(p->p.first!=null);
		return r;
	}
		
	/**
	 * @param n
	 * @param m
	 * @return Un stream con todos los pares de enteros (i,j) con i en 0 a n-1 y j en 0 a m-1
	 */
	public static Stream<IntPair> allPairs(Integer n, Integer m) {
		return IntStream.range(0, n).boxed()
				.flatMap(i -> IntStream.range(0, m).boxed().map(j -> IntPair.of(i, j)));
	}
	
	public static Stream<IntPair> allPairs(Integer n1, Integer n2, Integer m1, Integer m2){
		return IntStream.range(n1, n2).boxed()
				.flatMap(x->IntStream.range(m1, m2).boxed().map(y->IntPair.of(x,y)));
	}
	
	public static Stream<IntTrio> allPairs(Integer n, Integer m, Integer r) {
		return IntStream.range(0, n).boxed()
				.flatMap(i -> IntStream.range(0, m).boxed()
						            .flatMap(j->IntStream.range(0, r).boxed().map(k -> IntTrio.of(i, j, k))));
	}
	
	/**
	 * @param s Un stream 
	 * @param s1 Una secuencia adicional de stream
	 * @param <T> El tipo de los elementos de la secuencia
	 * @return Un stream fromado con los parámetros concatenados
	 */
	@SafeVarargs
	public static <T> Stream<T> concat(Stream<T>... s1) {
		return Arrays.stream(s1).flatMap(x->x);
	}

	/**
	 * @param s1 Un stream
	 * @param s2 Un segundo stream
	 * @param f1 Una función que calcula una clave para los elementos de stream1
	 * @param f2 Una función que calcula una clave para los elementos de stream2
	 * @param fr Una función que calcula un nuevo valor a partir  de uno procedente de stream1 y otro del stream2
	 * @param <T> El tipo de los elementos de la primera secuencia
	 * @param <U> El tipo de los elementos de la segunda secuencia
	 * @param <K> El tipo de los elementos de la clave
	 * @param <R> El tipo de los elementos de la secuencia resultante
	 * @return Un stream resultado del joint de stream1 y stream2
	 */
	public static <T, U, K, R> Stream<R> join(
			Stream<T> s1,
			Stream<U> s2,
			Function<? super T, ? extends K> f1,
			Function<? super U, ? extends K> f2, 
			BiFunction<T, U, R> fr) {
		
		return s1.flatMap(e1->s2.filter(e2->f1.apply(e1).equals(f2.apply(e2)))
								.map(e2->fr.apply(e1, e2)));
	}
	
	
}
