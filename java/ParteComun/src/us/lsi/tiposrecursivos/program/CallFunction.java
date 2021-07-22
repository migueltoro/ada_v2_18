package us.lsi.tiposrecursivos.program;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record CallFunction(String id,List<Exp> parameters) implements Exp {
	
	public static CallFunction of(String id, List<Exp> parameters) {
		return new CallFunction(id, parameters);
	}
	
	public Object value() {
		return null;
	}
	
	public Type type() {
		return null;
	}
	
	@Override
	public String toString() {
		String d = this.parameters.stream().map(x->x.toString()).collect(Collectors.joining(","));
		return String.format("%s(%s)",this.id,d);
	}

	@Override
	public String label() {
		return this.id();
	}
	
	@Override
	public void toDot(PrintStream file, Map<Object,Integer> map) {
		Integer pn = Program.getIndex(this,map,this.label(), file);
		for(Exp e:this.parameters()) {
			Integer dn = Program.getIndex(e,map,e.label(),file);
			Program.edge(pn, dn, file);
			e.toDot(file, map);
		}
	}

}
