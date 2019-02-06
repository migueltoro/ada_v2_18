package us.lsi.common;


import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;

public interface InmutableCollector<E, B, R> {
	
	BiFunction<B, E, B> accumulator();
	BinaryOperator<B> combiner();
	Function<B, R> finisher();
	B initial();
	
	public static  <E,B,R> Collector<E, MutableType<B>, R> toMutable(
			B initial, 
			BiFunction<B, E, B> accumulator, 
			BinaryOperator<B> combiner, 
			Function<B, R> finisher,
			Collector.Characteristics... characteristics) {
		return new Collectors2.InmutableToMutableCollector<>(initial,accumulator,combiner,finisher,characteristics); 
	}
	
	public static  <E,B,R> InmutableCollector<E, B, R> of(
			B initial, 
			BiFunction<B, E, B> accumulator, 
			BinaryOperator<B> combiner, 
			Function<B, R> finisher) {
		return new Collectors2.InmutableCollectorImpl<E,B,R>(initial,accumulator,combiner,finisher); 
	}
}
