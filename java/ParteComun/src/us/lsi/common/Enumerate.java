package us.lsi.common;

public class Enumerate<E> {

	public static <E> Enumerate<E> of(Integer num, E element) {
		return new Enumerate<E>(num, element);
	}

	public final Integer counter;
	public final E value;

	private Enumerate(Integer num, E element) {
		super();
		this.counter = num;
		this.value = element;
	}

	@Override
	public String toString() {
		return String.format("(%d,%s)", counter, value.toString());
	}

}