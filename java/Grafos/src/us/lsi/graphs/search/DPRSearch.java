package us.lsi.graphs.search;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.jgrapht.graph.GraphWalk;

import us.lsi.graphs.search.DynamicProgrammingReductionSearch.PDRType;
import us.lsi.graphs.search.DynamicProgrammingReductionSearch.SpR;
import us.lsi.graphs.virtual.EGraph;

public interface DPRSearch<V, E, S> {	
	
	SpR<E> search(); 
	GraphWalk<V,E> path(V v);
	S getSolution(V vertex);
	
	
	public static <V, E, S> DPRSearch<V, E, S> dynamicProgrammingReduction(
			EGraph<V, E> g, V startVertex, V end, 
			BiFunction<V, V, Double> heuristic, 
			Function<List<E>, S> solution,
			PDRType type) {
		return new DynamicProgrammingReductionSearch<V, E, S>(g, startVertex, end, heuristic, solution, type);
	}
	
	
}