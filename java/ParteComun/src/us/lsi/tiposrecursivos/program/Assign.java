package us.lsi.tiposrecursivos.program;

import java.io.PrintStream;
import java.util.Map;

public record Assign(Exp id, Exp exp) implements Sentence {

	public static Assign of(Exp id, Exp exp) {
		return new Assign(id, exp);
	}
	
	@Override
	public String toString() {
		return String.format("%s = %s\n", this.id, this.exp);
	}

	@Override
	public String label() {
		return "<==";
	}
	
	@Override
	public void toDot(PrintStream file, Map<Object, Integer> map) {
		Integer n = Program.getIndex(this,map,this.label(),file);
		Integer id = Program.getIndex(this.id(),map,this.id.label(),file);
		Integer exp = Program.getIndex(this.exp(),map,this.exp().label(),file);
		Program.edge(n,id,"left",file);
		Program.edge(n,exp,"right",file);
		this.id().toDot(file, map);
		this.exp().toDot(file, map);
	}
	
}
