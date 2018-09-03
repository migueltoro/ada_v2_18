package us.lsi.common;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class InmutableCollector<E, B, R> {

	
	public static <E, B, R> InmutableCollector<E, B, R> of(B initialValue, BiFunction<B, E, B> accumulate,
			BinaryOperator<B> combiner, Function<B, R> finisher) {
		return new InmutableCollector<E, B, R>(initialValue, accumulate, combiner, finisher);
	}

	B initialValue;
	BiFunction<B, E, B> accumulator;
	BinaryOperator<B> combiner;
	Function<B, R> finisher;
	
	private InmutableCollector(B initialValue, BiFunction<B, E, B> accumulator, BinaryOperator<B> combiner,
			Function<B, R> finisher) {
		super();
		this.initialValue = initialValue;
		this.accumulator = accumulator;
		this.combiner = combiner;
		this.finisher = finisher;
	}

	
}

