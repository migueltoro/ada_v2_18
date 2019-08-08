package us.lsi.flujossecuenciales;

public class Pair<A, B> {

	public A a;
	public B b;
	
	public static <A,B>  Pair<A,B> of(A a, B b){
		return new Pair<>(a,b);
	}
	
	private Pair(A a, B b) {
		super();
		this.a = a;
		this.b = b;
	}
	
	@Override
	public String toString() {
		return String.format("(%s,%s)",a,b);
	}
}

