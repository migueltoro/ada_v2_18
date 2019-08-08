package us.lsi.flujossecuenciales;

import java.util.Iterator;
import java.util.List;

public class ListIterator<E> implements Iterator<E> {
	private List<E> ls;
	private Integer i;
	public ListIterator(List<E> ls) {
		super();
		this.ls = ls;
		this.i = 0;
	}
	@Override
	public boolean hasNext() { 
		return i < ls.size(); 
	}
	@Override
	public E next() {
		Integer old = i;
		i = i+1;
		return ls.get(old);
	}
}

