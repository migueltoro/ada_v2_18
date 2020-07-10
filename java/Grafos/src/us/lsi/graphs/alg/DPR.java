package us.lsi.graphs.alg;

import java.util.function.Function;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.common.TriFunction;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;

public interface DPR<V, E, S> {	
	
	EGraphPath<V,E> pathFrom(V vertex);
	S getSolution(EGraphPath<V,E> path);
	
	
	public static <V, E, S> DynamicProgrammingReduction<V, E, S> dynamicProgrammingReduction(
			EGraph<V, E> graph, 
			Predicate<V> goal,
			V end, 
			TriFunction<V, Predicate<V>, V, Double> heuristic,
			Function<GraphPath<V,E>, S> solution,
			PDType type) {
		return new DynamicProgrammingReduction<V, E, S>(graph, goal,end, heuristic, solution, type);
	}
	
	public static <V, E, S> DynamicProgrammingReduction<V, E, S> dynamicProgrammingReductionGoal(
			EGraph<V, E> graph, 
			Predicate<V> goal,
			TriFunction<V, Predicate<V>, V, Double> heuristic,
			Function<GraphPath<V,E>, S> solution,
			PDType type) {
		return new DynamicProgrammingReduction<V, E, S>(graph, goal,null, heuristic, solution, type);
	}
	
	public static <V, E, S> DynamicProgrammingReduction<V, E, S> dynamicProgrammingReductionEnd(
			EGraph<V, E> graph, 
			V end, 
			TriFunction<V, Predicate<V>, V, Double> heuristic,
			Function<GraphPath<V,E>, S> solution,
			PDType type) {
		return new DynamicProgrammingReduction<V, E, S>(graph,e->e.equals(end),end, heuristic, solution, type);
	}
	
	
}