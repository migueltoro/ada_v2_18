package us.lsi.alg.pack;


import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class Heuristica {
	
	public static Double heuristic(PackVertex v1, Predicate<PackVertex> goal, PackVertex v2) {
		EGraph<PackVertex, PackEdge> graph = 
				SimpleVirtualGraph.last(v1,PackVertex.goal(),v->(double)v.nc);

		GreedyOnGraph<PackVertex, PackEdge> rr = 
				GreedyOnGraph.of(graph, PackVertex::greedyEdge);
		GraphPath<PackVertex, PackEdge> p = rr.path();
		Double r = (double) p.getEndVertex().nc;
		return r;
	}

}
