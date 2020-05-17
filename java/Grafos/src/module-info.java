


module grafos {
	exports us.lsi.graphs;
	exports us.lsi.graphs.views;
	exports us.lsi.graphs.virtual;
	exports us.lsi.flowgraph;
	exports us.lsi.astar;
	exports us.lsi.walks.manual;
	exports us.lsi.colors;
	exports us.lsi.graphs.tour;
	exports us.lsi.graphs.search;
	exports us.lsi.hypergraphs;
	exports us.lsi.path;

	requires transitive solve;
	requires transitive org.jgrapht.core;
	requires transitive org.jgrapht.io;
	requires transitive partecomun;
}