package us.lsi.graphs.alg;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.common.TriFunction;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.EGraph;


public interface BT<V, E, S extends Comparable<S>> {

	void search();

	Optional<S> getSolution();

	Set<S> getSolutions();
	String toStringSolutions();

	public static <V, E, S extends Comparable<S>> BackTracking<V, E, S> backTracking(
			EGraph<V, E> graph,
			Predicate<V> goal,
			V end,
			Predicate<V> constraint,
			TriFunction<V, Predicate<V>, V, Double> heuristic,
			Function<GraphPath<V, E>,S> solution, 
			Function<V,V> copy, 
			BTType type)  {
		return new BackTracking<V, E, S>(graph, goal, end, constraint,heuristic,solution, copy, type);
	}
	
	public static <V, E, S extends Comparable<S>> BackTracking<V, E, S> backTracking(
			EGraph<V, E> graph,
			Predicate<V> goal,
			V end,
			TriFunction<V, Predicate<V>, V, Double> heuristic,
			Function<GraphPath<V, E>,S> solution, 
			Function<V,V> copy, 
			BTType type)  {
		return new BackTracking<V, E, S>(graph, goal,end,v->true,heuristic,solution, copy, type);
	}
	
	public static <V, E, S extends Comparable<S>> BackTrackingRandom<V, E, S> random(
			EGraph<V, E> graph, 
			Predicate<V> goal, 
			V end,
			Predicate<V> constraint,
			Function<GraphPath<V, E>, S> solution, 
			Function<V, V> copy, 
			BTType type,
			Function<V, Integer> size) {
		return new BackTrackingRandom<V, E, S>(graph, goal, end, constraint,solution, copy, type, size);
	}
	
	public static <V, E, S extends Comparable<S>> BackTrackingRandom<V, E, S> random(
			EGraph<V, E> graph, 
			Predicate<V> goal, 
			V end,
			Function<GraphPath<V, E>, S> solution, 
			Function<V, V> copy, 
			BTType type,
			Function<V, Integer> size) {
		return new BackTrackingRandom<V, E, S>(graph, goal,end, v->true,solution, copy, type, size);
	}


}