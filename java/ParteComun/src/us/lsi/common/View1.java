package us.lsi.common;

public class View1<D,E> {
	
	public static <D, E> View1<D, E> of(E elemen, D rest) {
		return new View1<D, E>(elemen, rest);
	}

	public final E element;
	public final D rest;
	
	private View1(E elemen, D rest) {
		super();
		this.element = elemen;
		this.rest = rest;
	}

}
