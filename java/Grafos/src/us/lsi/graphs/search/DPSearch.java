package us.lsi.graphs.search;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import us.lsi.graphs.hypergraphs.SimpleHyperEdge;
import us.lsi.graphs.hypergraphs.SimpleVirtualHyperGraph;
import us.lsi.graphs.hypergraphs.GraphTree;
import us.lsi.graphs.hypergraphs.VirtualHyperVertex;
import us.lsi.graphs.search.DynamicProgrammingSearch.PDType;
import us.lsi.graphs.search.DynamicProgrammingSearch.Sp;

public interface DPSearch<V extends VirtualHyperVertex<V, E, A>, E extends SimpleHyperEdge<V, A>, A> {

	Sp<E> search(V actual);
	SimpleVirtualHyperGraph<V, E, A> getGraph();
	Map<V, Sp<E>> getSolutionsTree();
	PDType getType();
	GraphTree<V,E,A> tree(V vertex);
	
	public static <V extends VirtualHyperVertex<V, E, A>, E extends SimpleHyperEdge<V, A>, A> 
		DPSearch<V, E, A> dynamicProgrammingSearch(
			SimpleVirtualHyperGraph<V, E, A> graph, 
			Function<List<Double>, 
			Double> addSolution, 
			PDType type) {
		return new DynamicProgrammingSearch<V, E, A>(graph, addSolution, type);
	}
}