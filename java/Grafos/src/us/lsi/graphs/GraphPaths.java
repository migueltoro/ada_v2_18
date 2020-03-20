package us.lsi.graphs;

import org.jgrapht.Graph;
import org.jgrapht.graph.GraphWalk;

public class GraphPaths {
	
	public static <V,E> GraphWalk<V,E> empty(Graph<V,E> graph){
		return GraphWalk.emptyWalk(graph);
	}
	
	public static <V,E> GraphWalk<V,E> concat(GraphWalk<V,E> g1, GraphWalk<V,E> g2){
		return g1.concat(g2, gp->gp.getEdgeList().stream().mapToDouble(e->g1.getGraph().getEdgeWeight(e)).sum());
	}

}
