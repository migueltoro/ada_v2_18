package us.lsi.flujossecuenciales;

import java.util.Iterator;

public class IteratorWithSeeNext<E> implements Iterator<E>,Iterable<E> {

	private final Iterator<E> iterator;
	private E next = null;
	
	public static <H> IteratorWithSeeNext<H> of(Iterator<H> iterator){
		return new IteratorWithSeeNext<>(iterator);
	}
	
	public IteratorWithSeeNext(Iterator<E> iterator) {
		super();
		this.iterator = iterator;
		if(this.iterator.hasNext()) this.next = this.iterator.next();
		else this.next = null;
	}

	@Override
	public boolean hasNext() {
		return this.next != null;
	}

	@Override
	public E next() {
		E old = next;
		if(this.iterator.hasNext()) this.next = this.iterator.next();
		else this.next = null;
		return old;
	}
	
	public E seeNext() {
		return next;
	}
	
	@Override
	public Iterator<E> iterator() {
		return this;
	}
	
}


