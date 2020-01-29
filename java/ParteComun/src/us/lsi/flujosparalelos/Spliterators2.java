package us.lsi.flujosparalelos;

import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Spliterators2 {
	
	public static <T> Spliterator<T> asSpliterator(Stream<T> stream) { 
        return stream.spliterator(); 
    }
	
	public static <T> Stream<T> asStream(Spliterator<T> iterator) {
        return StreamSupport.stream(iterator, true);
    }

}
