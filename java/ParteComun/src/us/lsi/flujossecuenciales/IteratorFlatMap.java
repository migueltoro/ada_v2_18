package us.lsi.flujossecuenciales;

import java.util.Iterator;
import java.util.function.Function;

public class IteratorFlatMap<E,R> implements Iterator<R>, Iterable<R> {
	
	private Iterator<Iterator<R>> iterator;
	private Iterator<R> actual;
	
	public static <E,R> IteratorFlatMap<E,R> of(Iterator<E> iterator, Function<E,Iterator<R>> fmap){
		return new IteratorFlatMap<>(iterator,fmap);
	}
	
	IteratorFlatMap(Iterator<E> iterator, Function<E,Iterator<R>> fmap) {
		super();
		Iterator<Iterator<R>> tmp = IteratorMap.of(iterator, fmap);
		this.iterator = IteratorFilter.of(tmp,it->it.hasNext());
		this.actual = this.iterator.next();
	}

	@Override
	public boolean hasNext() {
		return this.actual.hasNext() || this.iterator.hasNext();
	}
	
	@Override
	public R next() {
		R old = this.actual.next();
		if(!this.actual.hasNext() && this.iterator.hasNext()) this.actual = this.iterator.next();
		return old;
	}

	@Override
	public Iterator<R> iterator() {
		return this;
	}
	
}

