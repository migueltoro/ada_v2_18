package us.lsi.flujossecuenciales;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import us.lsi.common.Enumerate;
import us.lsi.common.Pair;
import us.lsi.common.Preconditions;
import us.lsi.common.View1;

public class Iterables {
	
	/**
	 * @param iterable Un iterable 
	 * @return Un iterador con todos los pares que formar el producto cartesiano de los elementos recorridos por el iterable 
	 */
	public static <E> Iterable<Pair<E,E>> cartesianProduct(Iterable<E> iterable) {
		return IteratorCartesianProduct.of(iterable);
	}
	
	/**
	 * @param iterableA Un iterable 
	 * @param iterableB Un iterable 
	 * @return Un iterador con todos los pares que formar el producto cartesiano de los elementos recorridos por el iterable 
	 */
	public static <A,B> Iterable<Pair<A,B>> cartesianProduct(Iterable<A> iterableA, Iterable<B> iterableB) {
		return IteratorCartesianProduct.of(iterableA,iterableB);
	}
	
	/**
	 * @param iterator Un iterador
	 * @return Un iterador con todos los pares que formar el producto cartesiano de los elementos recorridos por el iterador 
	 */
	public static <E> Iterable<Pair<E,E>> consecutivePairs(Iterable<E> iterator) {
		return IteratorConsecutivePairs.of(iterator);
	}

	/**
	 * @param iteratorA Un iterador
	 * @param iteratorB Un iterador
	 * @return Un iteradpr que recorre los pares formados en cremallera con los elementos del iteratorA y el iteratorB
	 */
	public static <A, B> Iterable<Pair<A, B>> zip(Iterable<A> iteratorA, Iterable<B> iteratorB){
		return IteratorZip.of(iteratorA, iteratorB);		
	}
	
	/**
	 * @param iterator Un iterador
	 * @return Un iterador con todos los pares los elementos del iterador de entrada y su ppsición empezando por cero
	 */
	public static <E> Iterable<Enumerate<E>> enumerate(Iterable<E> iterator) {
		return IteratorEnumerate.of(iterator);
	}
	
	public static <E> Iterable<E> fusionOrdered(Iterable<E> iteratorA, Iterable<E> iteratorB, Comparator<E> cmp) {
		return IteratorFusionOrdered.of(iteratorA,iteratorB,cmp);
	}
	
	/**
	 * @param file Un fichero
	 * @return Un iterable que recorre las líneas del fichero
	 */
	public static Iterable<String> file(String file) {
		return new IterableFile(file);	
	}
	
	/**
	 * @param e Una cadena de entrada
	 * @param delim Un delimimitador o una expresión regular expresando un conjunto de delimitadores
	 * @return Un iterador que recorre las partes de e delimitadas por elementos en delim
	 */
	public static Iterable<String> split(String e, String delim){
		String[] r = e.split(delim);
		Iterable<String> r2 = Arrays.asList(r);
		Iterator<String> r3 = IteratorMap.of(r2,x->x.trim()) ;
		return IteratorFilter.of(r3,x->x.length() > 0) ;
	}
	
	public static <E,R> Iterable<R> flatMap(Iterable<E> iterator, Function<E,Iterable<R>> fmap){
		return new IteratorFlatMap<E,R>(iterator.iterator(),fmap);
	}
	
	public static <E,R> Iterable<R> map(Iterable<E> iterator, Function<E, R> fmap) {
		return new IteratorMap<E,R>(iterator.iterator(), fmap);
	}
	
	public static <E> Iterable<E> filter(Iterable<E> iterator, Predicate<E> p){
		return new IteratorFilter<>(iterator.iterator(),p);
	}
	
	/**
	 * @return Un iterador vacío
	 */
	public static <E> Iterable<E> empty() {
		return IteratorEmpty.of();
	}
	
	/**
	 * @param iterable Un iterable
	 * @return La vista como stream del iterable
	 */
	public static <T> Stream<T> asStream(Iterable<T> iterable) { 
        Spliterator<T> spliterator = iterable.spliterator(); 
        return StreamSupport.stream(spliterator, false); 
    } 
	
	/**
	 * @param iterator Un iterator
	 * @return La vista como stream del iterator
	 */
	public static <T> Stream<T> iteratorAsStream(Iterator<T> iterator) {
        Iterable<T> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), false);
    }
	
	/**
	 * @param iterator Un iterador
	 * @return Una vista del mismo de tipo 1
	 */
	public static <T> View1<Iterator<T>,T> view(Iterator<T> iterator) {
		Preconditions.checkArgument(iterator.hasNext(),"El iterador está vacío");
		T e = iterator.next();
		return View1.of(e,iterator);
	}
	
	public static class IterableFile implements Iterator<String>, Iterable<String> {
		
		private BufferedReader bf;
		private String file;
		private String nextLine;

		private IterableFile(String file) {
			super();
			this.file = file;			
			try {
				this.bf = new BufferedReader(new FileReader(file));
				this.nextLine = this.bf.readLine();
			} catch (Exception e) {
				System.err.println(e);
			}
			
		}

		@Override
		public Iterator<String> iterator() {
			return new IterableFile(file);
		}

		@Override
		public boolean hasNext() {
			return this.nextLine != null;
		}

		@Override
		public String next() {
			String r = this.nextLine;
			try {
				this.nextLine = bf.readLine();
			} catch (IOException e) {
				System.err.println(e);
			}
			return r;
		}
	
	}
	
}
