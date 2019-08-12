/**
 * 
 */
/**
 * @author migueltoro
 *
 */
module grafos {
	exports us.lsi.graphs;
	exports us.lsi.graphs.views;
	exports us.lsi.graphs.virtual;
	exports us.lsi.flowgraph;
	exports us.lsi.astar;
	exports us.lsi.graphs.manual;
	exports us.lsi.grafos.test;
	exports us.lsi.graphcolors;
	exports us.lsi.graphs.tour;

	requires transitive lpsolve;
	requires transitive org.jgrapht.core;
	requires transitive org.jgrapht.io;
	requires transitive partecomun;
}