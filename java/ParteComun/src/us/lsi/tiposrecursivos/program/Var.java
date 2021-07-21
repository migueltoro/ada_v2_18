package us.lsi.tiposrecursivos.program;

public class Var implements Exp {
	
	private String id;
	private Object value;
	private Type type;

	private Var(String id, Object value, Type type) {
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
	public static Var of(String id, Object value, Type type) {
		return new Var(id,value,type);
	}

	@Override
	public String toString() {
		return String.format("%s",this.id());
	}

}
