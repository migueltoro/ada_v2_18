package us.lsi.flujossecuenciales;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Stream;

import us.lsi.common.Enumerate;
import us.lsi.common.Pair;

public class StreamsS {
	
	
	
	public static <T> Iterator<T> asIterator(Stream<T> stream) { 
        return stream.iterator(); 
    }
	
	public static <E> Stream<Pair<E,E>> cartesianProduct(Iterable<E> iterableA) {
		return Iterators.asStream(IteratorCartesianProduct.of(iterableA));
	}
	
	public static <E> Stream<Pair<E,E>> consecutivePairs(Iterator<E> iterator) {
		return Iterators.asStream(IteratorConsecutivePairs.of(iterator));
	}

	public static <A, B> Stream<Pair<A, B>> zip(Iterator<A> iteratorA, Iterator<B> iteratorB){
		return Iterators.asStream(IteratorZip.of(iteratorA, iteratorB));		
	}
	
	public static <E> Stream<Enumerate<E>> enumerate(Iterator<E> iterator) {
		return Iterators.asStream(IteratorEnumerate.of(iterator));
	}
	
	public static <E> Stream<E> sorted(Iterator<E> iteratorA, Iterator<E> iteratorB, Comparator<E> cmp) {
		return Iterators.asStream(IteratorOrdered.of(iteratorA,iteratorB,cmp));
	}
	
	public static <E> Stream<Pair<E,E>> consecutivePairs(Stream<E> st) {
		return Iterators.asStream(IteratorConsecutivePairs.of(st.iterator()));
	}

	public static <A, B> Stream<Pair<A, B>> zip(Stream<A> sA, Stream<B> sB){
		return Iterators.asStream(IteratorZip.of(sA.iterator(), sB.iterator()));		
	}
	
	public static <E> Stream<E> sorted(Stream<E> sA, Stream<E> sB, Comparator<E> cmp) {
		return Iterators.asStream(IteratorOrdered.of(sA.iterator(),sB.iterator(),cmp));
	}
	
	
	public static Stream<String> file(String file)  {
		Stream<String> r = null;
		try {
			r =  Files.lines(Path.of(file));
		} catch (IOException e) {			
			System.err.println(String.format("No se ha encontrador %s", file));
		}
		return r;
	}
	
	public static Stream<String> split(String e, String delim){
		String[] r = e.split(delim);
		return Arrays.stream(r).<String> map(x -> x.trim())
				.filter(x-> x.length() > 0);
	}
	
	public static <E> Iterator<E> empty() {
		return IteratorEmpty.of();
	}
	

}
