package us.lsi.tiposrecursivos.ast;

import java.io.PrintStream;
import java.util.Map;

public sealed interface Declaration permits Var, FunDeclaration  {
	String name();
	void toDot(PrintStream file, Map<Object,Integer> map);
}
