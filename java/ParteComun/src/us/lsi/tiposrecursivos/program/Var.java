package us.lsi.tiposrecursivos.program;

import java.io.PrintStream;
import java.util.Map;

public final class Var implements Exp {
	
	private String id;
	private Object value;
	private Type type;

	private Var(String id, Type type, Object value) {
		super();
		this.id = id;
		this.value = value;
		this.type = type;
	}
	
	public static Var of(String id,Type type) {
		return new Var(id,null,type);
	}
	
	public Object value() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String id() {
		return id;
	}
	public Type type() {
		return type;
	}
	public static Var of(String id, Type type, Object value) {
		return new Var(id,type,value);
	}
	
	public static Var of(VarDeclaration d) {
		return new Var(d.id(),d.type(),d.value());
	}

	@Override
	public String toString() {
		return String.format("%s",this.id());
	}
	
	@Override
	public String label() {
		return String.format("%s=%s",this.id(),this.value());
	}
	
	@Override
	public void toDot(PrintStream file, Map<Object, Integer> map) {
		Program.getIndex(this,map,this.label(),file);
	}

}
