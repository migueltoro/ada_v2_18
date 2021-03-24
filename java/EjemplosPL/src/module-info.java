



module ejemplos_pl {
	exports us.lsi.pli.gurobi;

	requires transitive datos_compartidos;
	requires transitive solve;
	requires transitive grafos;
	requires transitive partecomun;
	requires transitive org.jgrapht.io;
}