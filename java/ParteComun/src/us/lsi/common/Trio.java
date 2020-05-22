package us.lsi.common;

public class Trio<A, B, C> {

	public static <A, B, C> Trio<A, B, C> of(A first, B second, C third) {
		return new Trio<A, B, C>(first, second, third);
	}

	public A first;
	public B second;
	public C third;
	
	protected Trio(A first, B second, C third) {
		super();
		this.first = first;
		this.second = second;
		this.third = third;
	}



	@Override
	public String toString() {
		return String.format("(%s,%s,%s)",first,second,third);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		result = prime * result + ((third == null) ? 0 : third.hashCode());
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
		Trio<?,?,?> other = (Trio<?,?,?>) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		if (third == null) {
			if (other.third != null)
				return false;
		} else if (!third.equals(other.third))
			return false;
		return true;
	}
	
}
