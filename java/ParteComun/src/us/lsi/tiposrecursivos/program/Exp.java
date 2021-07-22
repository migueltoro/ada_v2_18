package us.lsi.tiposrecursivos.program;

import java.io.PrintStream;
import java.util.Map;

public sealed interface Exp permits Var, Const, Binary, Unary, CallFunction {
	
	Object value();
	Type type();
	String label();
	void toDot(PrintStream file, Map<Object,Integer> map);

}
