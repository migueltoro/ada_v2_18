/**
 * 
 */
/**
 * @author migueltoro
 *
 */
module partecomun {
	
	exports us.lsi.regularexpressions;
	exports us.lsi.basictypes;
	exports us.lsi.tiposrecursivos;
	exports us.lsi.tiposrecursivos.parsers;
	exports us.lsi.tiposrecursivos.program;
	exports us.lsi.iterables;
	exports us.lsi.streams;
	exports us.lsi.math;
	exports us.lsi.common;
	
	requires transitive commons.math3;
	requires transitive org.antlr.antlr4.runtime;
}