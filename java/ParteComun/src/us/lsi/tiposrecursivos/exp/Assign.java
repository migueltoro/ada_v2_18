package us.lsi.tiposrecursivos.exp;

public class Assign extends Sentence {

	public static Assign of(Exp id, Exp exp) {
		return new Assign(id, exp);
	}

	public final Exp id;
	public final Exp exp;
	
	private Assign(Exp id, Exp exp) {
		super();
		this.id = id;
		this.exp = exp;
	}
	
	@Override
	public String toString() {
		return String.format("%s = %s\n", this.id, this.exp);
	}
	
}
