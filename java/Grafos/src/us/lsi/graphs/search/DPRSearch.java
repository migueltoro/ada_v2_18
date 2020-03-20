package us.lsi.graphs.search;

import org.jgrapht.graph.GraphWalk;
import us.lsi.graphs.search.DynamicProgrammingReductionSearch.SpR;

public interface DPRSearch<V, E, S> {	
	SpR<E> search(); 
	GraphWalk<V,E> path(V v);
	S getSolution(V vertex);
}