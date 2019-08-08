package us.lsi.iterators;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface SeqCollector<E, B, R> {
	
	Supplier<B> supplier();
	BiConsumer<B,E> accumulator();
	Function<B,R> finisher();
	Predicate<B> isDone();
	
	public static <E,B,R> SeqCollector<E,B,R> of(
			Supplier<B> supplier, 
			BiConsumer<B, E> accumulator, 
			Function<B, R> finisher,
			Predicate<B> isDone){
		return new SeqCollectorC<>(supplier,accumulator,finisher,isDone);
	}
	
	static class SeqCollectorC<E,B,R> implements SeqCollector<E,B,R> {
		private Supplier<B> supplier;;
		private BiConsumer<B,E> accumulator;;
		private Function<B,R> finisher;
		private Predicate<B> isDone;
		public SeqCollectorC(Supplier<B> supplier, 
				BiConsumer<B, E> accumulator, 
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
		public BiConsumer<B, E> accumulator() {
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

