



module ejemplos_pl {
	exports us.lsi.pli.gurobi;
	exports us.lsi.pli.examples.test;
	exports us.lsi.pli.lpsolve;

	requires transitive datos_compartidos;
	requires transitive solve;
	requires transitive grafos;
	requires transitive partecomun;
	requires transitive org.jgrapht.io;
}