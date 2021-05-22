package us.lsi.common;

public record Enumerate<E>(Integer counter, E element) {

	public static <E> Enumerate<E> of(Integer num, E element) {
		return new Enumerate<E>(num, element);
	}

	@Override
	public String toString() {
		return String.format("(%d,%s)", counter(), element().toString());
	}

}