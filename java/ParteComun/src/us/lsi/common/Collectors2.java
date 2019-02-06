package us.lsi.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Collectors2 {
	
	public static <E> Collector<E,Multiset<E>,Multiset<E>> toMultiset() {
		return Collector.of(
				()->Multiset.create(), 
				(x,e)->x.add(e), 
				(x,y)->Multiset.add(x, y), 
				x->x, 
				Collector.Characteristics.UNORDERED, 
				Collector.Characteristics.IDENTITY_FINISH);
	}
	
	public static <E> Collector<E,List<E>,List<E>> mergeSort(Comparator<? super E> cmp) {
		return Collector.of(
				()->new ArrayList<>(), 
				(x,e)->Lists2.insertOrdered(x,e,cmp), 
				(x,y)->Lists2.mergeOrdered(x,y,cmp), 
				x->x, 
				Collector.Characteristics.UNORDERED, 
				Collector.Characteristics.IDENTITY_FINISH
				);
	}
	
	public static <E extends Comparable<? super E>> Collector<E,List<E>,List<E>> mergeSort() {
		return mergeSort(Comparator.naturalOrder());
	}
	
	
	public static <E> Collector<E,MutableType<Boolean>,Boolean> all(Predicate<E> p){
		return InmutableCollector.toMutable(
				true, 
				(b,e)->p.test(e), 
				(b1,b2)->b1&&b2, 
				b->b, 
				Collector.Characteristics.UNORDERED, 
				Collector.Characteristics.IDENTITY_FINISH
				);
	}
	
	public static <E> Collector<E,MutableType<Boolean>,Boolean> any(Predicate<E> p){
		return InmutableCollector.toMutable(
				false, 
				(b,e)->p.test(e), 
				(b1,b2)-> b1 || b2, 
				b->b, 
				Collector.Characteristics.UNORDERED, 
				Collector.Characteristics.IDENTITY_FINISH
				);
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
	
	public static <E> Collector<E,First<E>,Optional<E>> first() {		
		return Collector.<E,First<E>,Optional<E>>of(
				()-> new First<E>(),
				(b,e)-> {if(!b.found) {b.element = Optional.of(e); b.found = true;}},
				(b1,b2) -> {if(b1.element.isPresent()) return b1; else return b2;},
				x->x.element
				);
	}
	
	static class InmutableCollectorImpl<E, B, R>  implements InmutableCollector<E, B, R>{

		private B initial;
		private BiFunction<B, E, B> accumulator;
		private BinaryOperator<B> combiner;
		private Function<B, R> finisher;
		
		public InmutableCollectorImpl(B initial, 
				BiFunction<B, E, B> accumulator, 
				BinaryOperator<B> combiner,
				Function<B, R> finisher) {
			super();
			this.initial = initial;
			this.accumulator = accumulator;
			this.combiner = combiner;
			this.finisher = finisher;
		}

		public B initial() {
			return initial;
		}

		public BiFunction<B, E, B> accumulator() {
			return accumulator;
		}

		public BinaryOperator<B> combiner() {
			return combiner;
		}

		public Function<B, R> finisher() {
			return finisher;
		}
		
	}
	
	static class InmutableToMutableCollector<E, B, R>  implements Collector<E, MutableType<B>, R>{

		BiConsumer<MutableType<B>, E> accumulator;
		BinaryOperator<MutableType<B>> combiner;
		Function<MutableType<B>, R> finisher;
		Supplier<MutableType<B>> supplier;
		Set<Collector.Characteristics>	characteristics;
		
		
		public InmutableToMutableCollector(B initial, BiFunction<B, E, B> accumulator, 
				BinaryOperator<B> combiner, 
				Function<B, R> finisher,
				Collector.Characteristics... characteristics) {
			super();
			this.accumulator = (m,e)-> m.newValue(accumulator.apply(m.value, e));
			this.combiner = (m1,m2)->MutableType.create(combiner.apply(m1.value, m2.value));
			this.finisher = m->finisher.apply(m.value);
			this.supplier = ()->MutableType.create(initial);
			this.characteristics = Arrays.asList(characteristics).stream().collect(Collectors.toSet());
		}


		public BiConsumer<MutableType<B>, E> accumulator() {
			return accumulator;
		}


		public BinaryOperator<MutableType<B>> combiner() {
			return combiner;
		}


		public Function<MutableType<B>, R> finisher() {
			return finisher;
		}


		public Supplier<MutableType<B>> supplier() {
			return supplier;
		}


		public Set<Collector.Characteristics> characteristics() {
			return characteristics;
		}
		
	}
	
}
