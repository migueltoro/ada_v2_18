package us.lsi.graphs.alg;


import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Stream;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import us.lsi.flujossecuenciales.Iterators;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath.PathType;

public class BreadthSearch<V,E> implements GraphAlg<V,E>, Iterator<V>, Iterable<V> {
	
	private Graph<V,E> graph;
	private V startVertex;
	private Map<V,E> edgeToOrigin;
	public Queue<V> queue; 

	BreadthSearch(Graph<V, E> g, V startVertex) {
		this.graph = g;
		this.startVertex = startVertex;
		this.edgeToOrigin = new HashMap<>();
		this.edgeToOrigin.put(startVertex, null);
		this.queue = new LinkedList<>();
		this.queue.add(startVertex);
	}

	@Override
	public Stream<V> stream() {
		return Iterators.asStream(this.iterator());
	}
	
	@Override
	public BreadthSearch<V,E> copy() {
		return GraphAlg.breadth(this.graph, this.startVertex);
	}
	
	public Iterator<V> iterator() {
		return this.copy();
	}


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

	
	public E getEdgeToOrigin(V v) {
		return this.edgeToOrigin.get(v);
	}

	@Override
	public EGraph<V, E> getGraph() {
		return Graphs2.eGraph(this.graph,startVertex(),PathType.Sum);
	}
	
	@Override
	public V startVertex() {
		return this.startVertex;
	}	
	
}
