package us.lsi.tiposrecursivos;

import us.lsi.common.Preconditions;

public class Constant<E> {
	
	public static <E> Constant<E> constant(E value){
		Preconditions.checkNotNull(value);
		return new Constant<>(value);
	}
	
	private E value;
	
	public Constant(E value) {
		super();
		this.value = value;
	}

	public E getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
