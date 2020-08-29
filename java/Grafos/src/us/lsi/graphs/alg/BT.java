package us.lsi.graphs.alg;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.common.TriFunction;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.EGraph;


public interface BT<V, E, S> {

	void search();

	S getSolution();

	Set<S> getSolutions();

	public static <V, E, S> BackTracking<V, E, S> backTracking(
			EGraph<V, E> graph,
			Predicate<V> goal,
			V end,
			TriFunction<V, Predicate<V>, V, Double> heuristic,
			Function<GraphPath<V, E>,S> solution, 
			Function<V,V> copy, 
			BTType type)  {
		return new BackTracking<V, E, S>(graph, goal, end, heuristic,solution, copy, type);
	}
	
	public static <V, E, S> BackTracking<V, E, S> backTrackingGoal(
			EGraph<V, E> graph,
			Predicate<V> goal,
			TriFunction<V, Predicate<V>, V, Double> heuristic,
			Function<GraphPath<V, E>,S> solution, 
			Function<V,V> copy, 
			BTType type)  {
		return new BackTracking<V, E, S>(graph, goal,null, heuristic,solution, copy, type);
	}
	
	public static <V, E, S> BackTracking<V, E, S> backTrackingEnd(
			EGraph<V, E> graph,
			V end,
			TriFunction<V, Predicate<V>, V, Double> heuristic,
			Function<GraphPath<V, E>,S> solution, 
			Function<V,V> copy, 
			BTType type)  {
		return new BackTracking<V, E, S>(graph,e->e.equals(end), end, heuristic,solution, copy, type);
	}
	
	public static <V, E, S> BackTrackingRandom<V, E, S> random(
			EGraph<V, E> graph, 
			Predicate<V> goal, 
			V end,
			Function<GraphPath<V, E>, S> solution, 
			Function<V, V> copy, 
			BTType type,
			Function<V, Integer> size) {
		return new BackTrackingRandom<V, E, S>(graph, goal, end, solution, copy, type, size);
	}
	
	public static <V, E, S> BackTrackingRandom<V, E, S> randomGoal(
			EGraph<V, E> graph, 
			Predicate<V> goal, 
			Function<GraphPath<V, E>, S> solution, 
			Function<V, V> copy, 
			BTType type,
			Function<V, Integer> size) {
		return new BackTrackingRandom<V, E, S>(graph, goal,null, solution, copy, type, size);
	}
	
	public static <V, E, S> BackTrackingRandom<V, E, S> randomEnd(
			EGraph<V, E> graph, 
			V end,
			Function<GraphPath<V, E>, S> solution, 
			Function<V, V> copy,
			BTType type,
			Function<V, Integer> size) {
		return new BackTrackingRandom<V, E, S>(graph,e->e.equals(end), end, solution, copy, type, size);
	}

}