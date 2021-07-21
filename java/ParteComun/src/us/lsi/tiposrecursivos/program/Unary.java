package us.lsi.tiposrecursivos.program;

public record Unary(Exp operand, String op) implements Exp {

	public static Unary of(Exp operand, String op) {
		return new Unary(operand, op);
	}
	
	public Object value() {
		return null;
	}
	
	public Type type() {
		return null;
	}
	

	@Override
	public String toString() {
		return String.format("%s(%s)", this.op, this.operand);
	}
}
