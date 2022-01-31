package us.lsi.tiposrecursivos.ast;

import java.io.PrintStream;
import java.util.Map;

public interface Declaration  {
	String name();
	void toDot(PrintStream file, Map<Object,Integer> map);
}
