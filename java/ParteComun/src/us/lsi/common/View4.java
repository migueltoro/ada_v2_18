package us.lsi.common;

public class View4<D> {
	
	public static <D> View4<D> of(D a, D b, D c, D d) {
		return new View4<D>(a, b, c, d);
	}

	public final D a;
	public final D b;
	public final D c;
	public final D d;
	
	private View4(D a, D b, D c, D d) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

}
