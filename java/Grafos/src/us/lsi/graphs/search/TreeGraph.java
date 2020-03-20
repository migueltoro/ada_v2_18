package us.lsi.graphs.search;

import java.util.List;
import java.util.Map;

import us.lsi.graphs.hypergraphs.SimpleHyperEdge;
import us.lsi.graphs.search.DynamicProgrammingSearch.Sp;

public class TreeGraph<V, E extends SimpleHyperEdge<V,A>, A> {
	
	public static <V, E extends SimpleHyperEdge<V, A>, A> TreeGraph<V, E, A> of(Map<V, Sp<E>> tree) {
		return new TreeGraph<V, E, A>(tree);
	}

	private Map<V, Sp<E>> tree;

	private TreeGraph(Map<V, Sp<E>> tree) {
		super();
		this.tree = tree;
	}
	
	public Double weight(V vertex) {
		return tree.get(vertex).weight;
	}
	
	public List<V> neigbors(V vertex) {
		return tree.get(vertex).edge.targets;
	}
	
	public A action(V vertex) {
		return tree.get(vertex).edge.action;
	}
	
	public Boolean isBaseCase(V vertex) {
		return tree.get(vertex).edge == null;
	}
	
}
