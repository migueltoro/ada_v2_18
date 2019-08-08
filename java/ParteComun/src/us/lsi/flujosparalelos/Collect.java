package us.lsi.flujosparalelos;

import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

import us.lsi.common.MutableType;

public class Collect {
	
	public static <E,B,R> R collect(Stream<E> flow, Collector<E,B,R> c) {
		B b = collect(flow.spliterator(),c);
		return c.finisher().apply(b);
	}
	
	private static <E,B,R> B collect(Spliterator<E> flow, Collector<E,B,R> c) {		
		B b;
		Spliterator<E> f2;
		if(flow.estimateSize() > 5 && (f2= flow.trySplit())!=null) {
				B b1 = collect(f2,c);
				B b2 = collect(flow,c);
				b = c.combiner().apply(b1,b2);
		} else {
			b = c.supplier().get();
		}
		flow.forEachRemaining(x->c.accumulator().accept(b,x));
		return b;
	}
	
	public static <E,B, R> Collector<E,MutableType<B>,R> ofBaseInmutable(
			Supplier<B> supplier,
			BiFunction<B,E,B> consumer,
			BinaryOperator<B> combiner,
			Function<B,R> finisher){
		return Collector.of(()->MutableType.of(supplier.get()),
				(b,e)->b.newValue(consumer.apply(b.value,e)),
				(x,y)->MutableType.of(combiner.apply(x.value,y.value)),
				x->finisher.apply(x.value));
	}

}

