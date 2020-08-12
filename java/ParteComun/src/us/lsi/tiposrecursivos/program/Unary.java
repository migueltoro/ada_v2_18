package us.lsi.tiposrecursivos.program;

public class Unary extends Exp {

	public static Unary of(String op, Exp left) {
		return new Unary(op, left);
	}

	public final String op;
	public final Exp operand;

	private Unary(String op, Exp left) {
		super();
		this.op = op;
		this.operand = left;
	}

	@Override
	public String toString() {
		return String.format("%s(%s)", this.op, this.operand);
	}
}
