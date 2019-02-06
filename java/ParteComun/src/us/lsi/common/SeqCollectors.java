package us.lsi.common;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;


public class SeqCollectors {


	public static <E> SeqInmutableCollector<E,Boolean, Boolean> all(Predicate<E> p) {
		return SeqInmutableCollector.of(true, (b, x) -> p.test(x), b -> b, b -> !b);
	}

	public static <E> SeqInmutableCollector<E, Boolean, Boolean> any(Predicate<E> p) {
		return SeqInmutableCollector.of(false, (b, x) -> p.test(x), b -> b, b -> b);
	}

	static class Joining {
		public StringBuffer text; 
		public Boolean first;
		public Joining(StringBuffer element, Boolean first) {
			super();
			this.text = element;
			this.first = first;
		}
		public Joining() {
			super();
			this.text = new StringBuffer();
			this.first = true;
		}		
	}
	
	public static SeqCollector<String, Joining, String> joiningAccumulator(String separator, String prefix, String sufix) {		
		return SeqCollector.of(
				()-> new Joining(), 
				(b, e)->{if(b.first) b.text.append(e); else {b.text.append(separator); b.text.append(e);}; b.first = false;},
				x-> prefix+x.text.toString()+sufix,
				x->false);
	}

	public static SeqCollector<String,?, String> joiningAccumulator(String separator) {
		return joiningAccumulator(separator,"","");
	}

	public static <E> SeqInmutableCollector<E, E, E> reduce(E initial, BinaryOperator<E> bo) {
		return SeqInmutableCollector.of(initial,(x,y)->bo.apply(x,y),x->x,x->false);
	}
	
	static class First<E> {
		Optional<E> element; 
		Boolean found;
		public First(Optional<E> element, Boolean found) {
			super();
			this.element = element;
			this.found = found;
		}
		public First() {
			super();
			this.element = Optional.empty();
			this.found = false;
		}
	}
	
	public static <E> SeqCollector<E,First<E>,Optional<E>> first() {		
		return SeqCollector.<E,First<E>,Optional<E>>of(
				()-> new First<E>(),
				(b,e)-> {b.element = Optional.of(e); b.found = true;},
				x->x.element,
				x->x.found);
	}

	static class SeqMutableCollectorImpl<E, B, R> implements SeqCollector<E, B, R> {
		private BiConsumer<B,E> accumulator;
		private Function<B, R> finisher;
		private Predicate<B> isDone;
		private Supplier<B> supplier;
		
		public SeqMutableCollectorImpl(Supplier<B> supplier, BiConsumer<B, E> accumulator, Function<B, R> finisher, Predicate<B> isDone) {
			super();
			this.accumulator = accumulator;
			this.finisher = finisher;
			this.isDone = isDone;
			this.supplier = supplier;
		}

		public BiConsumer<B, E> accumulator() {
			return accumulator;
		}

		public Function<B, R> finisher() {
			return finisher;
		}

		public Predicate<B> isDone() {
			return isDone;
		}

		public Supplier<B> supplier() {
			return supplier;
		}
	}
	
	public static class SeqInmutableCollectorImpl<E, B, R>  implements SeqInmutableCollector<E, B, R>{

		B initial;
		BiFunction<B, E, B> accumulator;
		Function<B, R> finisher;		
		Predicate<B> isDone;
		
		
		public SeqInmutableCollectorImpl(B initial, 
				BiFunction<B, E, B> accumulator, 
				Function<B, R> finisher,
				Predicate<B> isDone) {
			super();
			this.accumulator = accumulator;
			this.finisher = finisher;
			this.initial = initial;
			this.isDone =isDone;
		}


		public B initial() {
			return initial;
		}


		public BiFunction<B, E, B> accumulator() {
			return accumulator;
		}


		public Function<B, R> finisher() {
			return finisher;
		}


		public Predicate<B> isDone() {
			return isDone;
		}
			
	}
}
