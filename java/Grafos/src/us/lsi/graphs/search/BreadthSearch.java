package us.lsi.graphs.search;


import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;

public class BreadthSearch<V,E> implements Search<V,E> {
	
	private Graph<V,E> graph;
	private V startVertex;
	private Map<V,E> edgeToOrigin;
	private Queue<V> queue; 

	BreadthSearch(Graph<V, E> g, V startVertex) {
		this.graph = g;
		this.startVertex = startVertex;
		this.edgeToOrigin = new HashMap<>();
		this.edgeToOrigin.put(startVertex, null);
		this.queue = new LinkedList<>();
		this.queue.add(startVertex);
	}
	
	@Override
	public Iterator<V> iterator() {
		return this;
	}
	
	@Override
	public boolean isSeenVertex(V v) {
		return this.edgeToOrigin.containsKey(v);
	}
	
	public boolean hasNext() {
		return !this.queue.isEmpty();
	}

	@Override
	public V next() {
		V actual = this.queue.remove();
		for(V v:Graphs.neighborListOf(graph, actual)) {
			if(!this.edgeToOrigin.containsKey(v)) {
				this.queue.add(v);
				this.edgeToOrigin.put(v,graph.getEdge(actual, v));
			}
		}
		return actual;
	}

	@Override
	public E getEdgeToOrigin(V v) {
		return this.edgeToOrigin.get(v);
	}

	@Override
	public Graph<V, E> getGraph() {
		return this.graph;
	}
	
	@Override
	public V initialVertex() {
		return this.startVertex;
	}	
}
