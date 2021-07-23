package us.lsi.tiposrecursivos.ast;

import java.io.PrintStream;
import java.util.Map;

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
	
	@Override
	public String label() {
		return this.op;
	}

	@Override
	public void toDot(PrintStream file, Map<Object, Integer> map) {
		Integer n = Ast.getIndex(this,map,this.label(),file);
		Integer left = Ast.getIndex(this.left(),map,this.left().label(),file);
		Integer right = Ast.getIndex(this.right(),map,this.right().label(),file);
		Ast.edge(n,left, file);
		Ast.edge(n,right, file);
		this.left().toDot(file, map);
		this.right().toDot(file, map);
	}

}
