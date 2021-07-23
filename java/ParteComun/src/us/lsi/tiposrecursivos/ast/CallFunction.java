package us.lsi.tiposrecursivos.ast;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record CallFunction(String id,List<Exp> parameters, FunDeclaration funDeclaration) implements Exp {
	
	public static CallFunction of(String id, List<Exp> parameters,FunDeclaration funDeclaration) {
		return new CallFunction(id, parameters,funDeclaration);
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
		Integer pn = Ast.getIndex(this,map,this.label(), file);
		for(Exp e:this.parameters()) {
			Integer dn = Ast.getIndex(e,map,e.label(),file);
			Ast.edge(pn, dn, file);
			e.toDot(file, map);
		}
	}

}
