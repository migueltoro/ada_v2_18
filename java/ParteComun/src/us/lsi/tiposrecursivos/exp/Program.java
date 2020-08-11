package us.lsi.tiposrecursivos.exp;

import java.util.List;
import java.util.stream.Collectors;

public class Program {
	
	public static Program of(List<Declaration> declarations, Block block) {
		return new Program(declarations, block);
	}

	public final List<Declaration> declarations;	
	public final Block block;
	
	private Program(List<Declaration> declarations, Block block) {
		super();
		this.declarations = declarations;
		this.block = block;
	}
	

	@Override
	public String toString() {
		String s = this.declarations.stream().map(x->x.toString()).collect(Collectors.joining("\n"));
		return String.format("Declaraciones\n%s\nCodigo\n%s",s,this.block);
	}
}
