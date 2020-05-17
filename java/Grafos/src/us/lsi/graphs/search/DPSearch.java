package us.lsi.graphs.search;


import java.util.Map;

import us.lsi.graphs.search.DynamicProgramming.PDType;
import us.lsi.hypergraphs.GraphTree;
import us.lsi.hypergraphs.SimpleHyperEdge;
import us.lsi.hypergraphs.SimpleVirtualHyperGraph;
import us.lsi.hypergraphs.VirtualHyperVertex;
import us.lsi.graphs.search.Sp;

public interface DPSearch<V extends VirtualHyperVertex<V, E, A>, E extends SimpleHyperEdge<V, A>, A> {

	Sp<E> search(V actual);
	SimpleVirtualHyperGraph<V, E, A> getGraph();
	Map<V, Sp<E>> getSolutionsTree();
	GraphTree<V,E,A> tree(V vertex);
	
	public static <V extends VirtualHyperVertex<V, E, A>, E extends SimpleHyperEdge<V, A>, A> 
		DPSearch<V, E, A> dynamicProgrammingSearch(
			SimpleVirtualHyperGraph<V, E, A> graph, 
			PDType type) {
		return new DynamicProgramming<V, E, A>(graph, type);
	}
}