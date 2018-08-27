package us.lsi.common;

public class MutableType<T> {

	public static <T> MutableType<T> create(T e) {
		return new MutableType<T>(e);
	}
	
	public T value;
	
	public T newValue(T newValue) {
		T old = value;
		this.value = newValue;
		return old;
	}
	
	private MutableType(T e) {
		super();
		this.value = e;
	}
	@Override
	public String toString() {
		return value.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MutableType))
			return false;
		MutableType<?> other = (MutableType<?>) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
}
