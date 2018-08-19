/**
 * 
 */
/**
 * @author Miguel Toro
 *
 */
module us.lsi.grafos {
	
	exports us.lsi.graphs;
	exports us.lsi.flowgraph;
	exports us.lsi.astar;
	exports us.lsi.grafos.test;
	exports us.lsi.graphcolors;

	requires transitive org.jgrapht.core;
	requires transitive org.jgrapht.io;
	requires transitive us.lsi.lpsolve;
	requires transitive us.lsi.partecomun;
}