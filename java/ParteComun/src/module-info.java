module partecomun {
	exports us.lsi.iterables;
	exports us.lsi.regularexpressions;
	exports us.lsi.basictypes;
	exports us.lsi.tiposrecursivos;
	exports us.lsi.tiposrecursivos.ast;
	exports us.lsi.math;
	exports us.lsi.tiposrecursivos.parsers;
	exports us.lsi.common;
	exports us.lsi.streams;

	requires commons.math3;
	requires org.antlr.antlr4.runtime;
	requires transitive org.jgrapht.core;
	requires transitive org.jgrapht.io;
}