package us.lsi.alg.monedas;

import java.util.Optional;
import java.util.function.Predicate;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearch;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;

public class MonedasHeuristica {

	
	public static Double heuristica(MonedaVertex v1, Predicate<MonedaVertex> goal, MonedaVertex end) {
		
		if(goal.test(v1)) return 0.;
		
		EGraph<MonedaVertex, MonedaEdge> graph = Graphs2.simpleVirtualGraph(v1);
		
		GreedySearch<MonedaVertex, MonedaEdge> rr = GraphAlg.greedy(
				graph,
				MonedaVertex::accionHeuristica,
				goal);
		
		Optional<EGraphPath<MonedaVertex, MonedaEdge>> path = rr.search();
		return path.get().getWeight();
	}
	

}
