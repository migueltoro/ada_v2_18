package us.lsi.common;


public final class Tuple5<T1, T2, T3, T4, T5> {
	
	public final T1 v1;
	public final T2 v2;
	public final T3 v3;
	public final T4 v4;
	public final T5 v5;
	
	protected Tuple5(T1 p1, T2 p2, T3 p3, T4 p4, T5 p5) {
		super();
		this.v1 = p1;
		this.v2 = p2;
		this.v3 = p3;
		this.v4 = p4;
		this.v5 = p5;
	}

	public T1 getV1() {
		return v1;
	}

	public T2 getV2() {
		return v2;
	}

	public T3 getV3() {
		return v3;
	}

	public T4 getV4() {
		return v4;
	}

	public T5 getV5() {
		return v5;
	}

	public Tuple5<T1, T2, T3, T4, T5> copy(){
		return Tuple.create(this.getV1(), this.getV2(), this.getV3(), this.getV4(), this.getV5());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((v1 == null) ? 0 : v1.hashCode());
		result = prime * result + ((v2 == null) ? 0 : v2.hashCode());
		result = prime * result + ((v3 == null) ? 0 : v3.hashCode());
		result = prime * result + ((v4 == null) ? 0 : v4.hashCode());
		result = prime * result + ((v5 == null) ? 0 : v5.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Tuple5))
			return false;
		Tuple5<?,?,?,?,?> other = (Tuple5<?,?,?,?,?>) obj;
		if (v1 == null) {
			if (other.v1 != null)
				return false;
		} else if (!v1.equals(other.v1))
			return false;
		if (v2 == null) {
			if (other.v2 != null)
				return false;
		} else if (!v2.equals(other.v2))
			return false;
		if (v3 == null) {
			if (other.v3 != null)
				return false;
		} else if (!v3.equals(other.v3))
			return false;
		if (v4 == null) {
			if (other.v4 != null)
				return false;
		} else if (!v4.equals(other.v4))
			return false;
		if (v5 == null) {
			if (other.v5 != null)
				return false;
		} else if (!v5.equals(other.v5))
			return false;
		return true;
	}		

	@Override
	public String toString() {
		return "(" + v1 + "," + v2 + "," + v3 + ","
				+ v4 +","+ v5+")";
	}
	
}
