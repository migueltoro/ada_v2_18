package us.lsi.tiposrecursivos.program;

public record Assign(Exp id, Exp exp) implements Sentence {

	public static Assign of(Exp id, Exp exp) {
		return new Assign(id, exp);
	}
	
	@Override
	public String toString() {
		return String.format("%s = %s\n", this.id, this.exp);
	}
	
}
