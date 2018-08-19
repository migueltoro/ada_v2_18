package us.lsi.common;


public final class Tuple2<T1, T2> {

	public final T1 v1;
	public final T2 v2;
	
	protected Tuple2(T1 p1, T2 p2) {
		super();
		this.v1 = p1;
		this.v2 = p2;
	}
	public T1 getV1() {
		return v1;
	}
	public T2 getV2() {
		return v2;
	}
	
	public Tuple2<T1, T2> copy(){
		return Tuple.create(this.getV1(), this.getV2());
	}
	
	public Tuple2<T1,T2> setKey(T1 v){
		return Tuple.create(v,getV2());
	}
	
	public Tuple2<T1,T2> setValue(T2 v){
		return Tuple.create(getV1(),v);
	}
	
	@Override
	public String toString() {
		return "(" + v1 + "," + v2 + ")";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((v1 == null) ? 0 : v1.hashCode());
		result = prime * result + ((v2 == null) ? 0 : v2.hashCode());
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
		Tuple2<?,?> other = (Tuple2<?, ?>) obj;
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
		return true;
	}
	
	
	
}
