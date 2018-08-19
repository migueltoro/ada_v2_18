package us.lsi.common;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;


public class Accumulators {

	public static <E, B> Accumulator<E, B, B> createMutable(Supplier<B> initial, Consumer<E> consumer){
		return new AccumulatorMutable<>(initial, consumer, x->x, x->false);
	}

	public static <E, B> Accumulator<E, B, B> createInmutable(B initial, BiFunction<B, E, B> f) {
		return new AccumulatorInmutable<>(initial, f, x->x, x->false);
	}

	public static Accumulator<Integer, Integer, Integer> sum() {
		return Accumulators.createInmutable(0, (x, y) -> x + y);
	}

	public static <E> Accumulator<E, Boolean, Boolean> all(Predicate<E> p) {
		return new AccumulatorInmutable<>(true, (b, x) -> p.test(x), b->b, b -> !b);
	}
	public static <E> Accumulator<E, Boolean,Boolean> any(Predicate<E> p) {
		return new AccumulatorInmutable<>(false, (b, x) -> p.test(x), b->b, b -> b);
	}
	public static Accumulator<String, String, String> joining(String separator, String begin, String end) {
		return new Joining(separator,begin,end);
	}
	public static Accumulator<String, String,String> joining(String separator) {
		return new Joining(separator,"","");
	}
	public static <E> Accumulator<E, E,E> reduce(E initial, BinaryOperator<E> bo) {
		return new Reduce<>(initial, bo);
	}

	private static class AccumulatorMutable<E, B, R> implements Accumulator<E, B, R> {
		private B base;
		private Supplier<B> initial;
		private Consumer<E> consumer;
		private Function<B,R> result;
		private Predicate<B> finish;

		public AccumulatorMutable(Supplier<B> initial, 
				Consumer<E> consumer, 
				Function<B,R> result,
				Predicate<B> finish) {
			super();
			this.base = null;
			this.consumer = consumer;
			this.initial = initial;
			this.result = result;
			this.finish = finish;
		}

		@Override
		public void setInitial() {
			this.base = this.initial.get();
		}

		@Override
		public void add(E e) {
			this.consumer.accept(e);
		}

		@Override
		public R result() {
			return this.result.apply(this.base);
		}

		@Override
		public Boolean finish() {
			return this.finish.test(base);
		}
	}

	private static class AccumulatorInmutable<E, B, R> implements Accumulator<E, B, R> {
		private B base;
		private B initial;
		private BiFunction<B, E, B> f;
		private Function<B,R> result;
		private Predicate<B> finish;

		public AccumulatorInmutable(B initial, 
				BiFunction<B, E, B> f, 
				Function<B,R> result,
				Predicate<B> finish) {
			super();
			this.base = null;
			this.f = f;
			this.initial = initial;
			this.result = result;
			this.finish = finish;
		}

		@Override
		public void setInitial() {
			this.base = this.initial;
		}

		@Override
		public void add(E e) {
			this.base = this.f.apply(base, e);
		}

		@Override
		public R result() {
			return this.result.apply(this.base);
		}

		@Override
		public Boolean finish() {
			return this.finish.test(base);
		}
	}
	
	private static class Joining implements Accumulator<String, String, String> {
		private Integer n = 0;	
		private String base;
		private String separator;
		private String begin;
		private String end;

		public Joining(String separator, String begin, String end) {
			super();
			this.n = 0;
			this.base = "";
			this.separator = separator;
			this.begin = begin;
			this.end = end;
		}

		@Override
		public void setInitial() {
			this.base = begin;
		}

		@Override
		public void add(String e) {
			if(n>0) {
				this.base = this.base +this.separator+e;
			} else {
				this.base = this.base+e;
			}
			this.n++;
		}

		@Override
		public String result() {
			return this.base+end;
		}

		@Override
		public Boolean finish() {
			return false;
		}
			
	}
	
	public static class Reduce<E> implements Accumulator<E,E,E> {

		private E b;
		private E initial;
		private BinaryOperator<E> bo;		
		
		public Reduce(E initial, BinaryOperator<E> bo) {
			super();
			this.initial = initial;
			this.bo = bo;
		}

		@Override
		public void setInitial() {
			this.b=this.initial;
		}

		@Override
		public void add(E e) {
			this.b = this.bo.apply(this.b, e);
		}

		@Override
		public E result() {
			return this.b;
		}

		@Override
		public Boolean finish() {
			return false;
		}
		
	}

}
