package us.lsi.flujossecuenciales;

import java.util.Iterator;

import us.lsi.common.Pair;


public class IteratorEnumerate<E> implements Iterator<Pair<E,Integer>>,Iterable<Pair<E,Integer>>{
	
	public static <E> Iterator<Pair<E,Integer>> of(Iterator<E> iterator) {
		return new IteratorEnumerate<E>(iterator);
	}

	private Iterator<E> iterator;
	private Integer index;
	
	private IteratorEnumerate(Iterator<E> iterator) {
		super();
		this.iterator = iterator;
		this.index = 0;
	}
	
	@Override
	public Iterator<Pair<E,Integer>> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	@Override
	public Pair<E,Integer> next() {
		Integer oldIndex = this.index;
		this.index = this.index +1;
		return Pair.of(this.iterator.next(), oldIndex);
	}
	
}
