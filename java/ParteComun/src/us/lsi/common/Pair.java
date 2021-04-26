package us.lsi.common;

public class Pair<A, B> {

	private A first;
	private B second;
	
	public static <A,B>  Pair<A,B> of(A a, B b){
		return new Pair<>(a,b);
	}
	
	protected Pair(A a, B b) {
		super();
		this.first = a;
		this.second = b;
	}
	
	
	
	public A first() {
		return first;
	}

	public B second() {
		return second;
	}

	@Override
	public String toString() {
		return String.format("(%s,%s)",first,second);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
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
		return true;
	}
	
	
	
}

