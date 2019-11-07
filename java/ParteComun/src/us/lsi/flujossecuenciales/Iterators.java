package us.lsi.flujossecuenciales;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import us.lsi.common.Pair;

public class Iterators {
	
	public static <E> Iterator<Pair<E,E>> cartesianProduct(Iterable<E> iterableA) {
		return IteratorCartesianProduct.of(iterableA);
	}
	
	public static <E> Iterator<Pair<E,E>> consecutivePairs(Iterator<E> iterator) {
		return IteratorConsecutivePairs.of(iterator);
	}

	public static <A, B> Iterator<Pair<A, B>> zip(Iterator<A> iteratorA, Iterator<B> iteratorB){
		return IteratorZip.of(iteratorA, iteratorB);		
	}
	
	public static <E> Iterator<Pair<E,Integer>> enumerate(Iterator<E> iterator) {
		return IteratorEnumerate.of(iterator);
	}
	
	public static <E> Iterator<E> ordered(Iterator<E> iteratorA, Iterator<E> iteratorB, Comparator<E> cmp) {
		return IteratorOrdered.of(iteratorA,iteratorB,cmp);
	}
	
	public static Iterator<String> file(String file) {
		Iterator<String> r = null;
		try {
			r =  Files.lines(Path.of(file)).iterator();
		} catch (IOException e) {			
			System.err.println(String.format("No se ha encontrado %s", file));
		}
		return r;
	}
	
	public static Iterator<String> split(String e, String delim){
		String[] r = e.split(delim);
		Iterator<String> r2 = Arrays.asList(r).iterator();
		Iterator<String> r3 = IteratorMap.of(r2,x->x.trim()) ;
		return IteratorFilter.of(r3,x->x.length() > 0) ;
	}
	
	public static <E> Iterator<E> empty() {
		return IteratorEmpty.of();
	}
	
	public static <T> Stream<T> asStream(Iterable<T> iterable) { 
        Spliterator<T> spliterator = iterable.spliterator(); 
        return StreamSupport.stream(spliterator, false); 
    } 
	
	public static <T> Stream<T> asStream(Iterator<T> iterator) {
        Iterable<T> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), false);
    }
	
	
	
}
