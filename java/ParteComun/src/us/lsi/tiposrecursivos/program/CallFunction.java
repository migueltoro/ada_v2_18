package us.lsi.tiposrecursivos.program;

import java.util.List;
import java.util.stream.Collectors;

public class CallFunction extends Exp {
	
	public static CallFunction of(String id, List<Exp> parameters) {
		return new CallFunction(id, parameters);
	}

	public final String id;
	public final List<Exp> parameters;
	
	private CallFunction(String id, List<Exp> parameters) {
		super();
		this.id = id;
		this.parameters = parameters;
	}
	
	@Override
	public String toString() {
		String d = this.parameters.stream().map(x->x.toString()).collect(Collectors.joining(""));
		return String.format("%s(%s)",this.id,d);
	}

}
