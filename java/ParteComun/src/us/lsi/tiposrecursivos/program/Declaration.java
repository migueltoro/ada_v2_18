package us.lsi.tiposrecursivos.program;

import java.io.PrintStream;
import java.util.Map;

public sealed interface Declaration permits VarDeclaration, FunDeclaration  {
	String label();
	void toDot(PrintStream file, Map<Object,Integer> map);
}
