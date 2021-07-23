package us.lsi.tiposrecursivos.program;

import java.io.PrintStream;
import java.util.Map;

public record VarDeclaration(String id, Type type, Object value) implements Declaration {
	
	public static VarDeclaration of(String id, Type type, Object value) {
		return new VarDeclaration(id, type, value);
	}
	
	public static VarDeclaration of(String id, Type type) {
		return new VarDeclaration(id, type,null);
	}
	
	@Override
	public String toString() {
		return String.format("%s:%s%s",this.id,this.type,this.value==null?"":"="+this.value.toString());
	}

	@Override
	public String label() {
		return String.format("%s:%s",this.id(),this.type());
	}

	@Override
	public void toDot(PrintStream file, Map<Object, Integer> map) {
		Program.getIndex(this,map,this.label(),file);
	}
}
