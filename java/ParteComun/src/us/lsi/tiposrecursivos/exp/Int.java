package us.lsi.tiposrecursivos.exp;

public class Int extends Exp {
	
	public static Int of(String value) {
		return new Int(value);
	}

	public final String value;

	private Int(String value) {
		super();
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}

}
