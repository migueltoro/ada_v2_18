package us.lsi.flujossecuenciales;

import java.util.Iterator;

import us.lsi.common.Pair;


public class IteratorCartesianProduct<A, B> implements Iterator<Pair<A,B>>,Iterable<Pair<A,B>>{
	
	public static <A> Iterator<Pair<A,A>> of(Iterable<A> iterableA) {
		Iterator<Pair<A,A>> r = IteratorEmpty.of();
		if(iterableA.iterator().hasNext()) {
			r = new IteratorCartesianProduct<A, A>(iterableA.iterator(), iterableA);
		}
		return r;
	}
	
	
	public static <A, B> Iterator<Pair<A,B>> of(Iterable<A> iterableA, Iterable<B> iterableB) {
		Iterator<Pair<A,B>> r = IteratorEmpty.of();
		if(iterableA.iterator().hasNext() && iterableB.iterator().hasNext()) {
			r = new IteratorCartesianProduct<A, B>(iterableA.iterator(), iterableB);
		}
		return r;
	}
	
	public static <A, B> Iterator<Pair<A,B>> of(Iterator<A> iteratorA, Iterable<B> iterableB) {
		Iterator<Pair<A,B>> r = IteratorEmpty.of();
		if(iteratorA.hasNext() && iterableB.iterator().hasNext()) {
			r = new IteratorCartesianProduct<A, B>(iteratorA, iterableB);
		}
		return r;
	}

	private Iterator<A> iteratorA;
	private A actualA;
	private Iterable<B> iterableB;
	private Iterator<B> iteratorB;
	
	private IteratorCartesianProduct(Iterator<A> iteratorA, Iterable<B> iterableB) {
		super();
		this.iteratorA = iteratorA;
		this.actualA = this.iteratorA.next();
		this.iterableB = iterableB;
		this.iteratorB = this.iterableB.iterator();
	}

	@Override
	public Iterator<Pair<A,B>> iterator() {
		return this;
	}
	
	@Override
	public boolean hasNext() {
		return this.iteratorA.hasNext() || this.iteratorB.hasNext();
	}
	
	@Override
	public Pair<A,B> next() {
		if(!this.iteratorB.hasNext()) {
			this.iteratorB = this.iterableB.iterator();
			this.actualA = this.iteratorA.next();
		}
		return Pair.of(this.actualA,this.iteratorB.next());
	}
}
