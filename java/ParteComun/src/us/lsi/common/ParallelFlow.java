package us.lsi.common;

import java.util.List;
import java.util.stream.Collector;

public interface ParallelFlow<E> {
	
	int size();
	
	int limit();
	
	default int numberOfParts() {
		return 2;
	}
	
	Boolean hasNext();
	
	E next();
	
	List<ParallelFlow<E>> split();
	
	default <B,R> R acummulate(Collector<E,B,R> c) {
		 B base = acummulate(this,c);
		return c.finisher().apply(base);
	}
	
	default <B,R> R acummulate(InmutableCollector<E,B,R> c) {
		 B base = acummulate(this,c);
		return c.finisher.apply(base);
	}
	
	default <B,R> B acummulate(ParallelFlow<E> flow, Collector<E,B,R> c) {		
		B base;
		if(flow.size() > flow.limit()) {
			List<ParallelFlow<E>> flows = flow.split();
			B base1 = acummulate(flows.get(0),c);
			B base2 = acummulate(flows.get(1),c);	
			base = c.combiner().apply(base1, base2);
		} else {
			base = c.supplier().get();
			while(flow.hasNext()) {
				c.accumulator().accept(base,flow.next());	
			}
		}	
		return base;
	}
	
	default <B,R> B acummulate(ParallelFlow<E> flow, InmutableCollector<E,B,R> c) {		
		B base;
		if(flow.size() > flow.limit()) {
			List<ParallelFlow<E>> flows = flow.split();
			B base1 = acummulate(flows.get(0),c);
			B base2 = acummulate(flows.get(1),c);	
			base = c.combiner.apply(base1, base2);
		} else {
			base = c.initialValue;
			while(flow.hasNext()) {
				base = c.accumulator.apply(base,flow.next());	
			}
		}	
		return base;
	}
	
}

