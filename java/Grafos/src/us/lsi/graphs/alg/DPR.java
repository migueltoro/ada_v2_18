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
			Predicate<V> goal,
			V end, 
			Predicate<V> constraint,
			TriFunction<V, Predicate<V>, V, Double> heuristic,
			PDType type) {
		return new DynamicProgrammingReduction<V, E>(graph, goal,end,constraint, heuristic, type);
	}
	
	public static <V, E, S> DynamicProgrammingReduction<V, E> dynamicProgrammingReductionGoal(
			EGraph<V, E> graph, 
			Predicate<V> goal,
			TriFunction<V, Predicate<V>, V, Double> heuristic,
			PDType type) {
		return new DynamicProgrammingReduction<V, E>(graph, goal,null,v->true,heuristic, type);
	}
	
	public static <V, E, S> DynamicProgrammingReduction<V, E> dynamicProgrammingReductionEnd(
			EGraph<V, E> graph, 
			V end, 
			TriFunction<V, Predicate<V>, V, Double> heuristic,
			PDType type) {
		return new DynamicProgrammingReduction<V, E>(graph,e->e.equals(end),end, v->true,heuristic, type);
	}
	
	
}