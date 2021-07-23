package us.lsi.tiposrecursivos.ast;

import java.io.PrintStream;
import java.util.Map;

public sealed interface Declaration permits Var, FunDeclaration  {
	String label();
	void toDot(PrintStream file, Map<Object,Integer> map);
}
