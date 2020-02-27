package us.lsi.graphs.search;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;

public class DephtSearch<V, E> implements Search<V,E> {


	private Map<V,E> edgeToOrigin;
	public Graph<V,E> graph;
	private Stack<V> stack;
//	private V startVertex; 

	DephtSearch(Graph<V, E> g, V startVertex) {
		this.graph = g;
//		this.startVertex = startVertex;
		this.edgeToOrigin = new HashMap<>();
		this.edgeToOrigin.put(startVertex, null);
		this.stack = new Stack<>();
		this.stack.add(startVertex);
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
		return !stack.isEmpty();
	}

	@Override
	public V next() {
		V actual = stack.pop();
		for(V v:Graphs.neighborListOf(graph, actual)) {
			if(!this.edgeToOrigin.containsKey(v)) {
				stack.add(v);
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
	
}
