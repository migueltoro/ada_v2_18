package us.lsi.flujossecuenciales;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface SeqCollector<E, B, R> {
	
	Supplier<B> supplier();
	BiFunction<B,E,B> accumulator();
	Function<B,R> finisher();
	Predicate<B> isDone();
	
	public static <E,B,R> SeqCollector<E,B,R> of(
			Supplier<B> supplier, 
			BiFunction<B, E, B> accumulator, 
			Function<B,R> finisher,
			Predicate<B> isDone){
		return new SeqCollectorC<>(supplier,accumulator,finisher,isDone);
	}
	
	public static <E,B> SeqCollector<E,B,B> of(
			Supplier<B> supplier, 
			BiFunction<B, E, B> accumulator){
		return new SeqCollectorC<>(supplier,accumulator,x->x,x->false);
	}
	
	public static <E> SeqCollector<E,E,E> of(
			BinaryOperator<E> accumulator){
		return new SeqCollectorC<>(()->null,accumulator,x->x,x->false);
	}
	
	public static <E,B,R> SeqCollector<E,B,R> ofBaseMutable(
			Supplier<B> supplier, 
			BiConsumer<B, E> accumulator, 
			Function<B, R> finisher,
			Predicate<B> isDone){
		return SeqCollector.of(supplier,(b,e)->{accumulator.accept(b, e);return b;},finisher,isDone);
	}
	
	static class SeqCollectorC<E,B,R> implements SeqCollector<E,B,R> {
		private Supplier<B> supplier;;
		private BiFunction<B, E, B> accumulator;;
		private Function<B,R> finisher;
		private Predicate<B> isDone;
		public SeqCollectorC(Supplier<B> supplier, 
				BiFunction<B, E, B> accumulator, 
				Function<B, R> finisher,
				Predicate<B> isDone) {
			super();
			this.supplier = supplier;
			this.accumulator = accumulator;
			this.finisher = finisher;
			this.isDone = isDone;
		}
		@Override
		public Supplier<B> supplier() {
			return supplier;
		}
		@Override
		public BiFunction<B, E, B> accumulator() {
			return accumulator;
		}
		@Override
		public Function<B, R> finisher() {
			return finisher;
		}
		@Override
		public Predicate<B> isDone() {
			return isDone;
		}
		
	}

}

