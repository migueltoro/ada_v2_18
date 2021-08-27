package us.lsi.tiposrecursivos.ast;

import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

import us.lsi.common.Set2;

public final class Var implements Exp, Declaration, Operator {
	
	private String name;
	private Object value;
	private Type type;

	private Var(String name, Type type, Object value) {
		super();
		this.name = name;
		this.value = value;
		this.type = type;
	}
	
	public static Var of(String name,Type type) {
		return new Var(name,type,null);
	}
	public String name() {
		return name;
	}
	public Object value() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public Type type() {
		return type;
	}
	public static Var of(String id, Type type, Object value) {
		return new Var(id,type,value);
	}

	@Override
	public String toString() {
		return String.format("%s",this.name());
	}
	
	@Override
	public String label() {
		return String.format("%s=%s",this.id(),this.value());
	}
	
	@Override
	public void toDot(PrintStream file, Map<Object, Integer> map) {
		Ast.getIndex(this,map,this.label(),file);
	}

	@Override
	public Operator operator() {
		return this;
	}

	@Override
	public Set<Var> vars() {
		return Set2.of(this);
	}

	@Override
	public void setValue(Map<String, Object> values) {
		this.setValue(values.get(this.name()));
	}

	@Override
	public OperatorId id() {
		return OperatorId.of0(name, type);
	}

	@Override
	public Type resultType() {
		return this.type();
	}

}
