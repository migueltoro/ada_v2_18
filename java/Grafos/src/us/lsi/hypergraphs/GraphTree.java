package us.lsi.hypergraphs;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import us.lsi.common.Lists2;
import us.lsi.graphs.search.Sp;

public class GraphTree<V, E extends SimpleHyperEdge<V,A>, A> {
	
	public static <V, E extends SimpleHyperEdge<V, A>, A> GraphTree<V, E, A> of(Map<V, Sp<E>> tree,V vertex) {
		return new GraphTree<V, E, A>(tree,vertex);
	}

	private Map<V, Sp<E>> tree;
	private V vertex;

	private GraphTree(Map<V, Sp<E>> tree, V vertex) {
		super();
		this.tree = tree;
		this.vertex = vertex;
	}
	
	public V vertex() {
		return vertex;
	}
	
	public Double weight() {
		return tree.get(this.vertex).weight;
	}
	
	public A action() {	
		A r = null;
		if(tree.get(this.vertex).edge != null) r = tree.get(this.vertex).edge.action;
		return r;
	}
	
	public List<GraphTree<V, E, A>> children() {
		return tree.get(this.vertex).edge.targets.stream()
				.map(v->GraphTree.of(tree, v))
				.collect(Collectors.toList());
	}
	
	public Boolean isBaseCase() {
		return tree.get(this.vertex).edge == null;
	}
	
	private static <V, E extends SimpleHyperEdge<V, A>, A> List<GraphTree<V, E, A>> nextLevel(List<GraphTree<V, E, A>> level){
		return level.stream()
				.filter(t->!t.isBaseCase())
				.flatMap(t->t.children().stream())
				.collect(Collectors.toList());
	}
	
	private static <V, E extends SimpleHyperEdge<V, A>, A> String string(GraphTree<V, E, A> tree) {
		return String.format("(%s,%s,%.2f)",tree.vertex(),tree.action()==null?"_":tree.action(),tree.weight());
	}
	
	@Override
	public String toString() {
		Integer n = 0;
		String r = String.format("%3d  [%s]",n,string(this));
		List<GraphTree<V, E, A>> children = nextLevel(Lists2.of(this));		
		while(!children.isEmpty()) {
			n++;
			r = String.format("%s\n%3d  %s",r,n,children.stream().map(t->string(t)).collect(Collectors.joining(",","[","]")));
			children = nextLevel(children);			
		}
		return r;		
	}
	
}
