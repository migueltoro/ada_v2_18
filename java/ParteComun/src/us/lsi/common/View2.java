package us.lsi.common;

public record View2<D,E>(E centralElement,D left,D right) {
	
	public static <D, E> View2<D, E> of(E centralElement, D left, D right) {
		return new View2<D, E>(centralElement, left, right);
	}

}
