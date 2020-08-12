package us.lsi.tiposrecursivos.program;

public class Binary extends Exp {
	
	public static Binary of(String op, Exp left, Exp right) {
		return new Binary(op, left, right);
	}
	public final String op;
	public final Exp left;
	public final Exp right;
	
	private Binary(String op, Exp left, Exp right) {
		super();
		this.op = op;
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String toString() {
		return String.format("(%s %s %s)", this.left, this.op, this.right);
	}

}
