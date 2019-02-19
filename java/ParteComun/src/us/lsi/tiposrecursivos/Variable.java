package us.lsi.tiposrecursivos;

import us.lsi.common.Preconditions;

public class Variable<E> {
	
	public static <E> Variable<E> var(String name){
		Preconditions.checkNotNull(name);
		return new Variable<>(name);
	}
	private String name;
	private E value;
	
	public Variable(String name) {
		super();
		this.name = name;
		this.value = null;
	}

	public E getValue() {
		return value;
	}

	public void setValue(E value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

}
