package us.lsi.tiposrecursivos.program;

import java.util.List;
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

}
