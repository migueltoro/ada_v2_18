/**
 * 
 */
/**
 * @author migueltoro
 *
 */
module ejemplos_de_varias_tecnicas {
	exports us.lsi.geneticosbt.sudoku;
	exports us.lsi.astar.mochila;
	exports us.lsi.astar.puzzle;
	exports us.lsi.astar.vuelos;
	exports us.lsi.dyv.ejemplos;
	exports us.lsi.pd.secuencias;
	exports us.lsi.astar.laberinto;
	exports us.lsi.bt.mochila;
	exports us.lsi.astar.reinas;
	exports us.lsi.bt.jarras;
	exports us.lsi.pd.floyd;
	exports us.lsi.pd.mochila;
	exports us.lsi.pd.jarras;
	exports us.lsi.astar.jarras;

	requires transitive algoritmos_recursivos;
	requires transitive commons.math3;
	requires transitive datos_compartidos;
	requires transitive geneticos;
	requires transitive grafos;
	requires org.jgrapht.core;
	requires transitive partecomun;
}