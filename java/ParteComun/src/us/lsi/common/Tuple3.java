package us.lsi.common;

public final class Tuple3<T1, T2, T3> {
	
	
	public final T1 v1;
	public final T2 v2;
	public final T3 v3;
	
	Tuple3(T1 p1, T2 p2, T3 p3) {
		super();
		this.v1 = p1;
		this.v2 = p2;
		this.v3 = p3;
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

	public Tuple3<T1, T2, T3> copy(){
		return Tuple.create(this.getV1(), this.getV2(), this.getV3());
	}
	
	public Tuple3<T1,T2,T3> setP1(T1 v){
		return Tuple.create(v,getV2(),getV3());
	}
	
	public Tuple3<T1,T2,T3> setP2(T2 v){
		return Tuple.create(getV1(),v,getV3());
	}
	
	public Tuple3<T1,T2,T3> setP3(T3 v){
		return Tuple.create(getV1(),getV2(),v);
	}
	
	@Override
	public String toString() {
		return "(" + v1 + "," + v2 + "," + v3 + ")";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((v1 == null) ? 0 : v1.hashCode());
		result = prime * result + ((v2 == null) ? 0 : v2.hashCode());
		result = prime * result + ((v3 == null) ? 0 : v3.hashCode());
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
		Tuple3<?, ?, ?> other = (Tuple3<?, ?, ?>) obj;
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
		return true;
	}
	
	
}
