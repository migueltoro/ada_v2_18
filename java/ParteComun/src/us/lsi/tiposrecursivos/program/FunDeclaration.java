package us.lsi.tiposrecursivos.program;

import java.util.List;
import java.util.stream.Collectors;

public record FunDeclaration(String id,Type resultType,List<ParamDeclaration> parameters) implements Declaration {

	public static FunDeclaration of(String id,Type resultType,List<ParamDeclaration> parameters) {
		return new FunDeclaration(id, resultType,parameters);
	}
	
	@Override
	public String toString() {
		String d = this.parameters.stream().map(x->x.toString()).collect(Collectors.joining(""));
		return String.format("%s(%s):%s",this.id,d,this.resultType);
	}

}
