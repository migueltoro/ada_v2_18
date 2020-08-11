package us.lsi.tiposrecursivos.exp;

public class Real extends Exp {
	
	public static Real of(String value) {
		return new Real(value);
	}

	public final String value;

	private Real(String value) {
		super();
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}

}
