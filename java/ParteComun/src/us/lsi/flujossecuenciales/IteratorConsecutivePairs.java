package us.lsi.flujossecuenciales;

import java.util.Iterator;

import us.lsi.common.Pair;

public class IteratorConsecutivePairs<E> implements Iterator<Pair<E,E>>,Iterable<Pair<E,E>>{

	public static <E> Iterator<Pair<E,E>> of(Iterator<E> iterator) {
		Iterator<Pair<E,E>> r = IteratorEmpty.of();
		E last;
		if(iterator.hasNext()) {
			last = iterator.next();
			if(iterator.hasNext()) {
				r = new IteratorConsecutivePairs<E>(iterator, last);
			}			
		}	
		return r;
	}

	private Iterator<E> iterator;
	private E last;
	
	private IteratorConsecutivePairs(Iterator<E> iterator,E last) {
		super();
		this.iterator = iterator;
		this.last = last;
	}

	@Override
	public Iterator<Pair<E,E>> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	@Override
	public Pair<E,E> next() {
		E oldLast = this.last;
		this.last = this.iterator.next();
		return Pair.of(oldLast,this.last);
	}
	
}

