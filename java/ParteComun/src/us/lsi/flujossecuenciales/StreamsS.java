package us.lsi.flujossecuenciales;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.common.Enumerate;
import us.lsi.common.Pair;

public class StreamsS {
	
	public static <T> Iterator<T> asIterator(Stream<T> stream) { 
        return stream.iterator(); 
    }
	
	public static <E> Stream<Pair<E,E>> cartesianProduct(Iterable<E> iterableA) {
		return Iterables.asStream(IteratorCartesianProduct.of(iterableA));
	}
	
	public static <E> Stream<Pair<E,E>> consecutivePairs(Iterable<E> iterator) {
		return Iterables.asStream(IteratorConsecutivePairs.of(iterator));
	}

	public static <A, B> Stream<Pair<A, B>> zip(Iterable<A> iteratorA, Iterable<B> iteratorB){
		return Iterables.asStream(IteratorZip.of(iteratorA, iteratorB));		
	}
	
	public static <E> Stream<Enumerate<E>> enumerate(Iterable<E> iterator) {
		return Iterables.asStream(IteratorEnumerate.of(iterator));
	}
	
	public static <E> Stream<E> fusionSorted(Iterable<E> iteratorA, Iterable<E> iteratorB, Comparator<E> cmp) {
		return Iterables.asStream(IteratorFusionOrdered.of(iteratorA,iteratorB,cmp));
	}
	
	public static <E> Stream<Pair<E,E>> cartesianProduct(Stream<E> stream) {
		List<E> ls = stream.collect(Collectors.toList());
		return Iterables.asStream(IteratorCartesianProduct.of(ls));
	}
	
	public static <E> Stream<Pair<E,E>> consecutivePairs(Stream<E> st) {
		return Iterables.asStream(IteratorConsecutivePairs.of(()->st.iterator()));
	}

	public static <A, B> Stream<Pair<A, B>> zip(Stream<A> sA, Stream<B> sB){
		return Iterables.asStream(IteratorZip.of(()->sA.iterator(), ()->sB.iterator()));		
	}
	
	public static <E> Stream<E> sorted(Stream<E> sA, Stream<E> sB, Comparator<E> cmp) {
		return Iterables.asStream(IteratorFusionOrdered.of(()->sA.iterator(),()->sB.iterator(),cmp));
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
	
	public static <E> Iterable<E> empty() {
		return IteratorEmpty.of();
	}
	

}
