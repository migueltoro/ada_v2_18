package us.lsi.flujosparalelos;

import java.util.Spliterator;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class CollectP {
	
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
		while(flow.tryAdvance(x->c.accumulator().accept(b,x)));
		return b;
	}


}

