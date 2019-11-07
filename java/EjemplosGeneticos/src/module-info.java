


module ejemplos_geneticos {
	exports us.lsi.ag.reinas;
	exports us.lsi.ag.mochila;
	exports us.lsi.ag.expression;
	exports us.lsi.ag.real;
	exports us.lsi.ag.sudoku;
	exports us.lsi.ag.anuncios;

	requires transitive geneticos;
	requires transitive commons.math3;
	requires transitive datos_compartidos;
	requires transitive partecomun;
}