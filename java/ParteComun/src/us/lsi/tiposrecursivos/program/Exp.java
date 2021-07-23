package us.lsi.tiposrecursivos.program;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import us.lsi.common.Printers;

public sealed interface Exp permits Var, Const, Binary, Unary, CallFunction {
	
	Object value();
	Type type();
	String label();
	void toDot(PrintStream file, Map<Object,Integer> map);
	
	public static void toDot(String file, Exp e) {
		PrintStream p = Printers.file(file);
		Map<Object,Integer> map = new HashMap<>();
		map.put("maxValue",0);
		String txt = "digraph Exp { \n \n"; 
		p.println(txt);
		e.toDot(p,map);
		p.println("}");
		p.close();
	}

}
