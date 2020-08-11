package us.lsi.tiposrecursivos.exp;

public class IfThenElse extends Sentence {
	
	public static IfThenElse of(Exp guard, Block trueBlock, Block falseBlock) {
		return new IfThenElse(guard, trueBlock, falseBlock);
	}

	public final Exp guard;
	public final Block trueBlock;
	public final Block falseBlock;
	
	private IfThenElse(Exp guard, Block trueBlock, Block falseBlock) {
		super();
		this.guard = guard;
		this.trueBlock = trueBlock;
		this.falseBlock = falseBlock;
	}
	
	@Override
	public String toString() {
		return String.format("if (%s) {\n%s} else {\n%s}\n", this.guard, this.trueBlock,this.falseBlock);
	}

}
