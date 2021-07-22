package us.lsi.tiposrecursivos.program;

import java.io.PrintStream;
import java.util.Map;

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
	
	@Override
	public String label() {
		return this.op;
	}
	
	@Override
	public void toDot(PrintStream file, Map<Object, Integer> map) {
		Integer n = Program.getIndex(this,map,this.label(),file);
		Integer operand = Program.getIndex(this.operand(),map,this.operand().label(),file);
		Program.edge(n,operand, file);
		this.operand().toDot(file, map);
	}
}
