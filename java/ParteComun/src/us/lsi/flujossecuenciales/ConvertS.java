package us.lsi.flujossecuenciales;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class ConvertS<E> {
	
	public static <T> Iterator<T> asIterator(Stream<T> stream) { 
        return stream.iterator(); 
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

