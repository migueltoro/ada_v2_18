package us.lsi.graphs.alg;


import java.util.Map;

import us.lsi.graphs.alg.Sp;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.hypergraphs.GraphTree;
import us.lsi.hypergraphs.SimpleHyperEdge;
import us.lsi.hypergraphs.SimpleVirtualHyperGraph;
import us.lsi.hypergraphs.VirtualHyperVertex;

public interface DP<V extends VirtualHyperVertex<V, E, A>, E extends SimpleHyperEdge<V, A>, A> {

	GraphTree<V,E,A> searchTree(V actual);
	SimpleVirtualHyperGraph<V, E, A> getGraph();
	Map<V, Sp<E>> getSolutionsTree();
	Sp<E> search();

	
	public static <V extends VirtualHyperVertex<V, E, A>, E extends SimpleHyperEdge<V, A>, A> 
		DP<V, E, A> dynamicProgrammingSearch(
			SimpleVirtualHyperGraph<V, E, A> graph, 
			PDType type) {
		return new DynamicProgramming<V, E, A>(graph, type);
	}
}