package us.lsi.iterators;

import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class Collect {
	
	public static <E,B,R> R seqCollectLeft(Iterator<E> s, SeqBaseInmutableCollector<E,B,R> a) {
		B b = a.supplier().get();
		while(s.hasNext() && !a.isDone().test(b)){
		   E e = s.next();
		   b = a.accumulator().apply(b,e);
		}
		return a.finisher().apply(b);
	}
	
	public static <E,B,R> R seqCollectLeft(Iterator<E> s, SeqCollector<E,B,R> a) {	
		B b = a.supplier().get();
		while(s.hasNext() && !a.isDone().test(b)){
		   E e = s.next();
		   a.accumulator().accept(b,e);
		}
		return a.finisher().apply(b);
	}
	
	public static <E,B,R> R seqCollectLeftRecursivo(Iterator<E> s, SeqBaseInmutableCollector<E,B,R> a) {
		B b = a.supplier().get();
		b = seqCollectLeftRecursivo(s,a,b);
		return a.finisher().apply(b);
	}
	
	private static <E,B,R> B seqCollectLeftRecursivo(Iterator<E> s, SeqBaseInmutableCollector<E,B,R> a, B b) {
		if(s.hasNext() && !a.isDone().test(b)){
		   E e = s.next();
		   b = a.accumulator().apply(b,e);
		   b = seqCollectLeftRecursivo(s,a,b);
		}
		return b;
	}
	
	public static <E,B,R> R seqCollectLeftRecursivo(Iterator<E> s, SeqCollector<E,B,R> a) {
		B b = a.supplier().get();
		b = seqCollectLeftRecursivo(s,a,b);
		return a.finisher().apply(b);
	}
	
	private static <E,B,R> B seqCollectLeftRecursivo(Iterator<E> s, SeqCollector<E,B,R> a, B b) {
		if(s.hasNext() && !a.isDone().test(b)){
		   E e = s.next();
		   a.accumulator().accept(b,e);
		   b = seqCollectLeftRecursivo(s,a,b);
		}
		return b;
	}

	public static <E,R> Optional<R> reduceLeft(Iterator<E> s, SeqBaseInmutableCollector<E,E,R> a) {
		if(!s.hasNext()) return Optional.empty();
		E b = s.next();
		while(s.hasNext()){
		   E e = s.next();
		   b = a.accumulator().apply(b,e);
		}
		return Optional.of(a.finisher().apply(b));
	}
	
	public static <E,R> Optional<R> reduceLeft(Iterator<E> s, SeqCollector<E,E,R> a) {
		if(!s.hasNext()) return Optional.empty();
		E b = s.next();
		while(s.hasNext()){
		   E e = s.next();
		   a.accumulator().accept(b,e);
		}
		return Optional.of(a.finisher().apply(b));
	}

	public static <E,B,R> R seqCollectRight(Iterator<E> s, SeqBaseInmutableCollector<E,B,R> a) {
		B b = seqCollectRightP(s,a);
		return a.finisher().apply(b);
	}

	private static <E,B,R> B seqCollectRightP(Iterator<E> s, SeqBaseInmutableCollector<E,B,R> a) {
		B b = a.supplier().get(); 
		if(s.hasNext()){
			E e = s.next();
			b = seqCollectRightP(s,a);
			if(!a.isDone().test(b)){
	 	 	   b = a.accumulator().apply(b, e);
	 		}
		} 
		return b;
	}
	
	public static <E,B,R> R seqCollectRight(Iterator<E> s, SeqCollector<E,B,R> a) {
		B b = seqCollectRightP(s,a);
		return a.finisher().apply(b);
	}

	private static <E,B,R> B seqCollectRightP(Iterator<E> s, SeqCollector<E,B,R> a) {
		B b = a.supplier().get(); 
		if(s.hasNext()){
			E e = s.next();
			b = seqCollectRightP(s,a);
			if(!a.isDone().test(b)){
	 	 	   a.accumulator().accept(b, e);
	 		}
		} 
		return b;
	}


	public static <E,R> Optional<R> reduceRight(Iterator<E> s, SeqBaseInmutableCollector<E,E,R> a) {
		if(!s.hasNext()) return Optional.empty();
		E b = reduceRightP(s,a);
		return Optional.of(a.finisher().apply(b));
	}

	private static <E, R> E reduceRightP(Iterator<E> s, SeqBaseInmutableCollector<E, E, R> a) {
		E b = null;
		if (s.hasNext()) {
			E e = s.next();
			b = reduceRightP(s,a);
			if (b==null) b = e;
			if (!a.isDone().test(b)) {
				b = a.accumulator().apply(b, e);
			}
		} 
		return b;
	}
	
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
