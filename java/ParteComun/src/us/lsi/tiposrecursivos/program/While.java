package us.lsi.tiposrecursivos.program;

import java.io.PrintStream;
import java.util.Map;

public record While(Exp guard, Block block) implements Sentence {
	
	public static While of(Exp guard, Block block) {
		return new While(guard, block);
	}
	
	@Override
	public String toString() {
		return String.format("while (%s) {\n%s}\n", this.guard, this.block);
	}

	@Override
	public String label() {
		return "while";
	}
	
	@Override
	public void toDot(PrintStream file, Map<Object, Integer> map) {
		Integer n = Program.getIndex(this,map,this.label(),file);
		Integer guard = Program.getIndex(this.guard(),map,this.guard().label(),file);
		Integer block = Program.getIndex(this.block().sentences().get(0),map,
				this.block().sentences().get(0).label(),file);
		Program.edge(n,guard, file);
		Program.edge(n,block, file);
		this.guard().toDot(file, map);
		this.block().toDot(file, map);
	}

}
