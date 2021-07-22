package us.lsi.tiposrecursivos.program;

import java.io.PrintStream;
import java.util.Map;

public sealed interface Sentence permits IfThenElse, Assign, While, Block {
	
	String label();
	void toDot(PrintStream file, Map<Object,Integer> map);

}
