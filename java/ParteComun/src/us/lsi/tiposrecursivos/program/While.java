package us.lsi.tiposrecursivos.program;

public record While(Exp guard, Block block) implements Sentence {
	
	public static While of(Exp guard, Block block) {
		return new While(guard, block);
	}
	
	@Override
	public String toString() {
		return String.format("while (%s) {\n%s}\n", this.guard, this.block);
	}

}
