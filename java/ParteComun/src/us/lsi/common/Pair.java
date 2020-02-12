package us.lsi.common;

public class Pair<A, B> {

	public A a;
	public B b;
	
	public static <A,B>  Pair<A,B> of(A a, B b){
		return new Pair<>(a,b);
	}
	
	protected Pair(A a, B b) {
		super();
		this.a = a;
		this.b = b;
	}
	
	@Override
	public String toString() {
		return String.format("(%s,%s)",a,b);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair<?,?> other = (Pair<?,?>) obj;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a))
			return false;
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!b.equals(other.b))
			return false;
		return true;
	}
	
	
	
}

