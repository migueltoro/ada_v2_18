package us.lsi.common;

public record View1<D,E>(E element,D rest) {
	
	public static <D, E> View1<D, E> of(E elemen, D rest) {
		return new View1<D, E>(elemen, rest);
	}

}
