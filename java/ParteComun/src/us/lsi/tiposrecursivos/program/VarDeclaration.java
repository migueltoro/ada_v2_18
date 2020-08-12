package us.lsi.tiposrecursivos.program;

public class VarDeclaration extends Declaration {

	public static VarDeclaration of(String id, Type type, Exp value) {
		return new VarDeclaration(id, type, value);
	}
	
	public static VarDeclaration of(String id, Type type) {
		return new VarDeclaration(id, type, null);
	}

	public final String id;
	public final Type type;
	public final Exp value;
	
	private VarDeclaration(String id, Type type, Exp value) {
		super();
		this.id = id;
		this.type = type;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return String.format("%s : %s %s", this.id, this.type,this.value != null ? "= "+this.value : "");
	}
}
