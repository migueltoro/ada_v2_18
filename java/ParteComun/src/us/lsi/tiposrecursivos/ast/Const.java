package us.lsi.tiposrecursivos.ast;

import java.io.PrintStream;
import java.util.Map;

public record Const(Type type, Object value) implements Exp {

	public static Const of(Type type, Object value) {
		return new Const(type,value);
	}

	@Override
	public String toString() {
		return String.format("%s",this.value);
	}
	
	@Override
	public String label() {
		return this.value.toString();
	}
	
	@Override
	public void toDot(PrintStream file, Map<Object, Integer> map) {
		Ast.getIndex(this,map,this.label(),file);
	}
	
}
