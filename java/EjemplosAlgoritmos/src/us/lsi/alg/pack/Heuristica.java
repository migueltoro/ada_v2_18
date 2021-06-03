package us.lsi.alg.pack;

import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearchOnGraph;
import us.lsi.graphs.virtual.EGraph;

public class Heuristica {
	
	public static Double heuristic(PackVertex v1, Predicate<PackVertex> goal, PackVertex v2) {
		EGraph<PackVertex, PackEdge> graph = 
				Graphs2.simpleVirtualGraphLast(v1,PackVertex.goal(),PackVertex.last(), v->true,v->(double)v.nc);

		GreedySearchOnGraph<PackVertex, PackEdge> rr = 
				GraphAlg.greedy(graph, PackVertex::greedyEdge);
		GraphPath<PackVertex, PackEdge> p = rr.search().orElse(null);
		Double r = (double) p.getEndVertex().nc;
		return r;
	}

}
