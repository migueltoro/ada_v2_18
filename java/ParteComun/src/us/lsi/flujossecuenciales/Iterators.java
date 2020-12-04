package us.lsi.flujossecuenciales;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

public class Iterators {
	
	/**
	 * @param iterable Un iterable 
	 * @return Un iterador con todos los pares que formar el producto cartesiano de los elementos recorridos por el iterable 
	 */
	public static <E> Iterator<Pair<E,E>> cartesianProduct(Iterable<E> iterable) {
		return IteratorCartesianProduct.of(iterable);
	}
	
	/**
	 * @param iterator Un iterador
	 * @return Un iterador con todos los pares que formar el producto cartesiano de los elementos recorridos por el iterador 
	 */
	public static <E> Iterator<Pair<E,E>> consecutivePairs(Iterator<E> iterator) {
		return IteratorConsecutivePairs.of(iterator);
	}

	/**
	 * @param iteratorA Un iterador
	 * @param iteratorB Un iterador
	 * @return Un iteradpr que recorre los pares formados en cremallera con los elementos del iteratorA y el iteratorB
	 */
	public static <A, B> Iterator<Pair<A, B>> zip(Iterator<A> iteratorA, Iterator<B> iteratorB){
		return IteratorZip.of(iteratorA, iteratorB);		
	}
	
	/**
	 * @param iterator Un iterador
	 * @return Un iterador con todos los pares los elementos del iterador de entrada y su ppsición empezando por cero
	 */
	public static <E> Iterator<Enumerate<E>> enumerate(Iterator<E> iterator) {
		return IteratorEnumerate.of(iterator);
	}
	
	public static <E> Iterator<E> ordered(Iterator<E> iteratorA, Iterator<E> iteratorB, Comparator<E> cmp) {
		return IteratorOrdered.of(iteratorA,iteratorB,cmp);
	}
	
	/**
	 * @param file Un fichero
	 * @return Un iterador que recorre las líneas del fichero
	 */
	public static Iterator<String> file(String file) {
		Iterator<String> r = null;
		try {
			r =  Files.lines(Path.of(file)).iterator();
		} catch (IOException e) {			
			System.err.println(String.format("No se ha encontrado %s", file));
		}
		return r;
	}
	
	/**
	 * @param e Una cadena de entrada
	 * @param delim Un delimimitador o una expresión regular expresando un conjunto de delimitadores
	 * @return Un iterador que recorre las partes de e delimitadas por elementos en delim
	 */
	public static Iterator<String> split(String e, String delim){
		String[] r = e.split(delim);
		Iterator<String> r2 = Arrays.asList(r).iterator();
		Iterator<String> r3 = IteratorMap.of(r2,x->x.trim()) ;
		return IteratorFilter.of(r3,x->x.length() > 0) ;
	}
	
	public static <E,R> Iterator<R> flatMap(Iterator<E> iterator, Function<E,Iterator<R>> fmap){
		return new IteratorFlatMap<E,R>(iterator,fmap);
	}
	
	public static <E,R> Iterator<R> map(Iterator<E> iterator, Function<E, R> fmap) {
		return new IteratorMap<E,R>(iterator, fmap);
	}
	
	public static <E> Iterator<E> filter(Iterator<E> iterator, Predicate<E> p){
		return new IteratorFilter<>(iterator,p);
	}
	
	/**
	 * @return Un iterador vacío
	 */
	public static <E> Iterator<E> empty() {
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
	public static <T> Stream<T> asStream(Iterator<T> iterator) {
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
	
	
	
	
}
