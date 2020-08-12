package us.lsi.tiposrecursivos.program;

public class IdType {
	
	public static IdType of(String id, Type type) {
		return new IdType(id, type);
	}

	public final String id;
	public final Type type;
	
	private IdType(String id, Type type) {
		this.id = id;
		this.type = type;
	}
	
	@Override
	public String toString() {
		return String.format("%s : %s", this.id,this.type);
	}

}
