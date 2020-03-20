package us.lsi.graphs.hypergraphs;

import java.util.List;
import java.util.Map;

import us.lsi.graphs.search.DynamicProgrammingSearch.Sp;

public class GraphTree<V, E extends SimpleHyperEdge<V,A>, A> {
	
	public static <V, E extends SimpleHyperEdge<V, A>, A> GraphTree<V, E, A> of(Map<V, Sp<E>> tree) {
		return new GraphTree<V, E, A>(tree);
	}

	private Map<V, Sp<E>> tree;

	private GraphTree(Map<V, Sp<E>> tree) {
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
