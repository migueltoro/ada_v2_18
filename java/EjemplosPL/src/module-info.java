



module ejemplos_pl {
	exports us.lsi.pli.gurobi;
	exports us.lsi.pli.examples.test;

	requires transitive datos_compartidos;
	requires transitive solve;
	requires transitive grafos;
	requires partecomun;
}