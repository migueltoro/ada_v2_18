package us.lsi.tiposrecursivos.program;

public class Bool extends Exp {

	public static Bool of(String value) {
		return new Bool(value);
	}

	public final String value;

	private Bool(String value) {
		super();
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}

}
