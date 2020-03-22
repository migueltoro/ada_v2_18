package us.lsi.graphs.search;

import java.util.List;
import java.util.SortedSet;
import java.util.function.BiFunction;
import java.util.function.Function;

import us.lsi.graphs.search.BackTrackingSearch.BTType;
import us.lsi.graphs.virtual.EGraph;

public interface BTSearch<V, E, S extends Comparable<S>> {

	void search();

	S getSolution();

	SortedSet<S> getSolutions();

	public static <V, E, S extends Comparable<S>> BTSearch<V, E, S> backTracking(
			EGraph<V, E> graph, V initial, V end,
			BiFunction<V, V, Double> heuristic, 
			Function<List<E>,S> solution, 
			Function<V,V> copy, 
			BTType type)  {
		return new BackTrackingSearch<V, E, S>(graph, initial, end, heuristic, solution, copy, type);
	}

}