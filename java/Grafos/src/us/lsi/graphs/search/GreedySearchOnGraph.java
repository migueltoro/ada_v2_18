package us.lsi.graphs.search;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;

import org.jgrapht.Graph;

public class GreedySearchOnGraph<V,E> implements Search<V,E> {

	private Graph<V,E> graph;
	private V actualVertex;
	public Map<V,E> edgeToOrigin;
	private Function<V,V> nextVertex;
	
	GreedySearchOnGraph(Graph<V, E> graph, V startVertex,Function<V,V> nextVertex) {
		this.graph = graph;
		this.actualVertex = startVertex;
		this.edgeToOrigin = new HashMap<>();
		this.edgeToOrigin.put(this.actualVertex, null);
		this.nextVertex = nextVertex; 
	}
	
	@Override
	public Iterator<V> iterator() {
		return this;
	}

	@Override
	public E getEdgeToOrigin(V v) {
		return this.edgeToOrigin.get(v);
	}

	@Override
	public boolean isSeenVertex(V v) {
		return this.edgeToOrigin.containsKey(v);
	}

	@Override
	public Graph<V, E> getGraph() {
		return this.graph;
	}
	
	@Override
	public boolean hasNext() {
		V v = this.nextVertex.apply(this.actualVertex);
		return !this.edgeToOrigin.containsKey(v);
	}

	@Override
	public V next() {
		V old = this.actualVertex;
		this.actualVertex = this.nextVertex.apply(old);
		E edge = this.graph.getEdge(old, this.actualVertex);
		this.edgeToOrigin.put(old,edge);
		return old;
	}

}
