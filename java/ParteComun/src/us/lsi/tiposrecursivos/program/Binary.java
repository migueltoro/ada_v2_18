package us.lsi.tiposrecursivos.program;

public record Binary(Exp left, Exp right, String op) implements Exp {
	
	public static Binary of(Exp left, Exp right, String op) {
		return new Binary(left, right, op);
	}
	
	public Object value() {
		return null;
	}
	
	public Type type() {
		return null;
	}
	
	@Override
	public String toString() {
		return String.format("(%s %s %s)", this.left, this.op, this.right);
	}

}
