package us.lsi.tiposrecursivos.program;

public record IfThenElse(Exp guard,Block trueBlock,Block falseBlock) implements Sentence {
	
	public static IfThenElse of(Exp guard, Block trueBlock, Block falseBlock) {
		return new IfThenElse(guard, trueBlock, falseBlock);
	}
	
	@Override
	public String toString() {
		return String.format("if (%s) {\n%s} else {\n%s}\n", this.guard, this.trueBlock,this.falseBlock);
	}

}
