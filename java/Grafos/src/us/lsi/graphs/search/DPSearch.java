package us.lsi.graphs.search;

import java.util.Map;

import us.lsi.graphs.hypergraphs.SimpleHyperEdge;
import us.lsi.graphs.hypergraphs.SimpleVirtualHyperGraph;
import us.lsi.graphs.hypergraphs.VirtualHyperVertex;
import us.lsi.graphs.search.DynamicProgrammingSearch.PDType;
import us.lsi.graphs.search.DynamicProgrammingSearch.Sp;
import us.lsi.graphs.search.TreeGraph;

public interface DPSearch<V extends VirtualHyperVertex<V, E, A>, E extends SimpleHyperEdge<V, A>, A> {

	Sp<E> search(V actual);
	SimpleVirtualHyperGraph<V, E, A> getGraph();
	Map<V, Sp<E>> getSolutionsTree();
	PDType getType();
	TreeGraph<V,E,A> tree(V vertex);
}