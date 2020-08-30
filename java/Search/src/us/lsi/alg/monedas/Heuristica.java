package us.lsi.alg.monedas;

import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearch;
import us.lsi.graphs.virtual.EGraph;

public class Heuristica {
	
	public static Double heuristica(MonedaVertex v1, Predicate<MonedaVertex> goal, MonedaVertex end) {
		
		EGraph<MonedaVertex, MonedaEdge> graph = Graphs2.simpleVirtualGraph(v1);
		
		GreedySearch<MonedaVertex, MonedaEdge> rr = GraphAlg.greedy(
				graph,
				MonedaVertex::accionHeuristica,
				e->e.equals(end));
		
		GraphPath<MonedaVertex, MonedaEdge> path = rr.search();
		return path.getWeight();
	}
	

}
