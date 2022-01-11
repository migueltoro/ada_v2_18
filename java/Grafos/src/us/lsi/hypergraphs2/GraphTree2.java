package us.lsi.hypergraphs2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface GraphTree2<V extends HyperVertex2<V, E, A, S>, E extends HyperEdge2<V, E, A, S>, A, S> {

	public static <V extends HyperVertex2<V, E, A, S>, E extends HyperEdge2<V, E, A, S>, A, S> 
		GraphTree2<V, E, A, S> empty(V v) {
		return new GT<>(v, null);
	}

	public static <V extends HyperVertex2<V, E, A, S>, E extends HyperEdge2<V, E, A, S>, A, S> 
		GraphTree2<V, E, A, S> of(V v, A a) {
		return new GT<>(v, a);
	}

	public V vertex();

	public A action();
	
	public default E hyperEdge() {
		return vertex().edge(action());
	}

	public default Double weight() {
		return this.vertex().solutionWeight().weight();
	}

	public default S solution() {
		return this.vertex().solution();
	}

	public default List<GraphTree2<V, E, A, S>> neighbors() {
		if(this.action() == null ) return List.of();
		return this.vertex().neighbors(this.action()).stream()
				.map(v -> v.isBaseCase() ? GraphTree2.empty(v) : GraphTree2.of(v, v.solutionWeight().edge().action()))
				.toList();
	}
	
	public default String string() {
		String label;
		if(vertex().isBaseCase()) label = "["+vertex().toString()+"]";
		else {
			label = "["+vertex().toString()+","+action().toString()+"]";
			label += neighbors().stream().map(g->g.string())
				.collect(Collectors.joining(",","(",")"));
		}
		return label;
	}
	
	public default Set<V> vertices(){
		Set<V> r = new HashSet<V>();
		r.add(this.vertex());
		for(GraphTree2<V,E,A,S> gt: this.neighbors()) {
			r.addAll(gt.vertices());
		}
		return r;
	}
	
	public default Set<E> hyperEdges(){
		Set<E> r = new HashSet<E>();
		for(V v: vertices()) {
			if (!v.isBaseCase()) {
				GraphTree2<V, E, A, S> gt = v.graphTree();
				r.add(gt.hyperEdge());
			}
		}
		return r;
	}

	public static record GT<V extends HyperVertex2<V, E, A, S>, E extends HyperEdge2<V, E, A, S>, A, S> (
			V vertex, A action) implements GraphTree2<V, E, A, S> {		
	}
}
