package us.lsi.tiposrecursivos.exp;

public class While extends Sentence {
	
	public static While of(Exp guard, Block block) {
		return new While(guard, block);
	}
	public final Exp guard;
	public final Block block;
	
	private While(Exp guard, Block block) {
		super();
		this.guard = guard;
		this.block = block;
	}
	
	@Override
	public String toString() {
		return String.format("while (%s) {\n%s}\n", this.guard, this.block);
	}

}
