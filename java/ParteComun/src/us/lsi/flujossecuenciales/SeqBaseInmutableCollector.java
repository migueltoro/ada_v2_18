package us.lsi.flujossecuenciales;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface SeqBaseInmutableCollector<E, B, R> {
	
	Supplier<B> supplier();
	BiFunction<B,E,B> accumulator();
	Function<B,R> finisher();
	Predicate<B> isDone();
	
	public static <E,B,R> SeqBaseInmutableCollector<E,B,R> of(
			Supplier<B> supplier, 
			BiFunction<B, E, B> accumulator, 
			Function<B, R> finisher,
			Predicate<B> isDone){
		return new SeqCollectorBIC<>(supplier,accumulator,finisher,isDone);
	}
	
	static class SeqCollectorBIC<E,B,R> implements SeqBaseInmutableCollector<E,B,R> {
		private Supplier<B> supplier;;
		private BiFunction<B,E,B> accumulator;;
		private Function<B,R> finisher;
		private Predicate<B> isDone;
		public SeqCollectorBIC(Supplier<B> supplier, 
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
