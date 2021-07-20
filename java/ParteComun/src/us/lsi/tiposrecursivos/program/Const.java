package us.lsi.tiposrecursivos.program;

public record Const(Type type, Object value) implements Exp {

	public static Const of(Type type, Object value) {
		return new Const(type,value);
	}

	@Override
	public String toString() {
		return String.format("%s",this.value);
	}
	
}
