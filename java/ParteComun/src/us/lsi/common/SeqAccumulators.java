package us.lsi.common;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;


public class SeqAccumulators {

	public static <E, B, R> SeqAccumulator<E, B, R> createMutable(
			Supplier<B> initial, 
			BiConsumer<B,E> consumer,
			Function<B, R> result, 
			Predicate<B> isDone) {
		return new SeqMutableAccumulator<>(initial, consumer, result, isDone);
	}
	
	public static <E, B> SeqAccumulator<E, B, B> createMutable(
			Supplier<B> initial, 
			BiConsumer<B,E> consumer) {
		return new SeqMutableAccumulator<>(initial, consumer, x->x,x->false);
	}

	public static <E, B, R> SeqAccumulator<E, B, R> createInmutable(
			B initial, 
			BiFunction<B, E, B> accumulate,
			Function<B, R> result, 
			Predicate<B> isDone) {
		return new SeqInmutableAccumulator<>(initial, accumulate, result, isDone);
	}
	
	public static <E, B> SeqAccumulator<E, B, B> createInmutable(
			B initial, 
			BiFunction<B, E, B> accumulate) {
		return new SeqInmutableAccumulator<>(initial, accumulate, x->x, x->false);
	}

	public static <E> SeqAccumulator<E, Boolean, Boolean> all(Predicate<E> p) {
		return new SeqInmutableAccumulator<>(true, (b, x) -> p.test(x), b -> b, b -> !b);
	}

	public static <E> SeqAccumulator<E, Boolean, Boolean> any(Predicate<E> p) {
		return new SeqInmutableAccumulator<>(false, (b, x) -> p.test(x), b -> b, b -> b);
	}

	public static SeqAccumulator<String, Tuple2<String,Integer>, String> joiningAccumulator(String separator, String prefix, String sufix) {
		Tuple2<String,Integer> initial = Tuple.create(prefix, 0);
		BiFunction<Tuple2<String,Integer>, String, Tuple2<String,Integer>> accumulate = 
				(Tuple2<String,Integer> b, String e)->b.v2>0?
					Tuple.create(b.v1+separator+e,b.v2+1):
					Tuple.create(b.v1+e,b.v2+1);
		Function<Tuple2<String,Integer>,String>	result = x->x.v1+sufix;	
		return createInmutable(
				initial, 
				accumulate,
				result, 
				x->false);
	}

	public static SeqAccumulator<String, Tuple2<String,Integer>, String> joiningAccumulator(String separator) {
		return joiningAccumulator(separator,"","");
	}

	public static <E> SeqAccumulator<E, E, E> reduce(E initial, BinaryOperator<E> bo) {
		return createInmutable(initial,bo,x->x,x->false);
	}
	
	public static <E> SeqAccumulator<E,Tuple2<Optional<E>,Boolean>, Optional<E>> first() {
		return createInmutable(
				Tuple.<Optional<E>,Boolean>create(Optional.empty(),false),
				(b,e)->Tuple.create(Optional.of(e),true),
				x->x.v1,
				x->x.v2);
	}
	
	public static <E,R> SeqAccumulator<E,E,R> general(Function<E,R> result, Predicate<E> isDone) {
		return createInmutable(
				null,
				(b,e)->e,
				result,
				isDone);
	}

	private static class SeqMutableAccumulator<E, B, R> implements SeqAccumulator<E, B, R> {
		private B base;
		private BiConsumer<B,E> accumulate;
		private Function<B, R> result;
		private Predicate<B> isDone;

		public SeqMutableAccumulator(Supplier<B> initial, 
				BiConsumer<B,E> accumulate, 
				Function<B, R> result,
				Predicate<B> isDone) {
			super();
			this.base = initial.get();
			this.accumulate = accumulate;
			this.result = result;
			this.isDone = isDone;
		}

		@Override
		public void add(E e) {
			this.accumulate.accept(this.base,e);
		}

		@Override
		public R result() {
			return this.result.apply(this.base);
		}

		@Override
		public Boolean isDone() {
			return this.isDone.test(this.base);
		}
	}

	private static class SeqInmutableAccumulator<E, B, R> implements SeqAccumulator<E, B, R> {
		private B base;
		private BiFunction<B, E, B> accumulate;
		private Function<B, R> result;
		private Predicate<B> isDone;

		public void add(E e) {
			this.base = accumulate.apply(this.base, e);
		}

		public R result() {
			return result.apply(this.base);
		}

		public Boolean isDone() {
			return isDone.test(this.base);
		}

		public SeqInmutableAccumulator(B initial, BiFunction<B, E, B> accumulate, Function<B, R> result,
				Predicate<B> finish) {
			super();
			this.base = initial;
			this.accumulate = accumulate;
			this.result = result;
			this.isDone = finish;
		}

	}
}
