package us.lsi.tiposrecursivos.program;

public class Str extends Exp {

	public static Str of(String value) {
		return new Str(value);
	}

	public final String value;

	private Str(String value) {
		super();
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}

}
