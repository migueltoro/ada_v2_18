package us.lsi.tiposrecursivos.program;

public record Id(String id, Object value) implements Exp {

	public static Id of(String id, Object value) {
		return new Id(id,value);
	}
	
	public static Id of(String id) {
		return new Id(id,null);
	}

	@Override
	public String toString() {
		return String.format("%s",this.id());
	}

}
