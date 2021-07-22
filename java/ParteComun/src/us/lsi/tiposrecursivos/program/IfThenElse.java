package us.lsi.tiposrecursivos.program;

import java.io.PrintStream;
import java.util.Map;

public record IfThenElse(Exp guard,Block trueBlock,Block falseBlock) implements Sentence {
	
	public static IfThenElse of(Exp guard, Block trueBlock, Block falseBlock) {
		return new IfThenElse(guard, trueBlock, falseBlock);
	}
	
	@Override
	public String toString() {
		return String.format("if (%s) {\n%s} else {\n%s}\n", this.guard, this.trueBlock,this.falseBlock);
	}
	
	@Override
	public String label() {
		return "ifThenElse";
	}
	
	@Override
	public void toDot(PrintStream file, Map<Object, Integer> map) {
		Integer n = Program.getIndex(this,map,this.label(),file);
		Integer guard = Program.getIndex(this.guard(),map,this.guard().label(),file);
		Integer trueBlock = Program.getIndex(this.trueBlock().sentences().get(0),map,
				this.trueBlock().sentences().get(0).label(),file);
		Integer falseBlock = Program.getIndex(this.falseBlock().sentences().get(0),map,
				this.falseBlock().sentences().get(0).label(),file);
		Program.edge(n,guard,"Guarda",file);
		Program.edge(n,trueBlock,"True",file);
		Program.edge(n,falseBlock,"False",file);
		this.guard().toDot(file,map);
		this.trueBlock().toDot(file,map);
		this.falseBlock().toDot(file,map);
	}

}
