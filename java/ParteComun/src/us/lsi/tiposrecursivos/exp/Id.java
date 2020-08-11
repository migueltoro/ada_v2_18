package us.lsi.tiposrecursivos.exp;

public class Id extends Exp {

	public static Id of(String id) {
		return new Id(id);
	}

	public final String id;

	private Id(String id) {
		super();
		this.id = id;
	}

	@Override
	public String toString() {
		return id.toString();
	}

}
