package us.lsi.common;

public class MutableType<T> {

	public static <T> MutableType<T> create(T e) {
		return new MutableType<T>(e);
	}
	
	public T e;
	private MutableType(T e) {
		super();
		this.e = e;
	}
	@Override
	public String toString() {
		return e.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((e == null) ? 0 : e.hashCode());
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
		if (e == null) {
			if (other.e != null)
				return false;
		} else if (!e.equals(other.e))
			return false;
		return true;
	}
	
}
