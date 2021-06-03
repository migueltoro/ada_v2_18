package us.lsi.graphs.alg;

import java.util.Optional;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.common.TriFunction;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.virtual.EGraph;

public interface DPR<V, E> {	
	
	Optional<GraphPath<V, E>> search();
	Optional<GraphPath<V, E>> search(V vertex);
	
	
	public static <V, E> DynamicProgrammingReduction<V, E> dynamicProgrammingReduction(
			EGraph<V, E> graph, 
			TriFunction<V, Predicate<V>, V, Double> heuristic,
			PDType type) {
		return new DynamicProgrammingReduction<V, E>(graph, heuristic, type);
	}
	
	
}