package us.lsi.tiposrecursivos.ast;

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
		Integer n = Ast.getIndex(this,map,this.label(),file);
		Integer operand = Ast.getIndex(this.operand(),map,this.operand().label(),file);
		Ast.edge(n,operand, file);
		this.operand().toDot(file, map);
	}
}
