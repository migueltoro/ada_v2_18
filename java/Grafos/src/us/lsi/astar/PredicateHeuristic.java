package us.lsi.astar;

import java.util.function.Predicate;

public interface PredicateHeuristic<V> {
	
	Double apply(V vertex,Predicate<V> vertices);

}
