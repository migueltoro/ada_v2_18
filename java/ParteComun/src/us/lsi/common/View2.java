package us.lsi.common;

public class View2<D,E> {
	
	public static <D, E> View2<D, E> of(E centralElement, D left, D right) {
		return new View2<D, E>(centralElement, left, right);
	}

	public final E centralElement;
	public final D left;
	public final D right;
	
	private View2(E centralElement, D left, D right) {
		super();
		this.centralElement = centralElement;
		this.left = left;
		this.right = right;
	}

}
