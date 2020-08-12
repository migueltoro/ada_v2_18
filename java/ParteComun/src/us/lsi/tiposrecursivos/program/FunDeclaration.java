package us.lsi.tiposrecursivos.program;

import java.util.List;
import java.util.stream.Collectors;

public class FunDeclaration extends Declaration {

	public static FunDeclaration of(String id, Type type, List<IdType> parameters) {
		return new FunDeclaration(id, type, parameters);
	}

	public final String id;
	public final Type type;
	public final List<IdType> parameters;
	
	private FunDeclaration(String id, Type type, List<IdType> parameters) {
		super();
		this.id = id;
		this.type = type;
		this.parameters = parameters;
	}
	
	@Override
	public String toString() {
		String d = this.parameters.stream().map(x->x.toString()).collect(Collectors.joining(","));
		return String.format("%s(%s):%s",this.id,d,this.type);
	}
	
}
