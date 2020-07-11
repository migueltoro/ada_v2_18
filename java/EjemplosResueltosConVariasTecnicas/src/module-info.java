


module ejemplos_de_varias_tecnicas {
	exports us.lsi.geneticosbt.sudoku;
	exports us.lsi.astar.puzzle;
	exports us.lsi.astar.vuelos;
	exports us.lsi.astar.laberinto;

	requires transitive algoritmos_recursivos;
	requires transitive datos_compartidos;
	requires transitive geneticos;
	requires transitive grafos;
	requires transitive partecomun;
}