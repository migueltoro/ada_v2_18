package us.lsi.graphs.search;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Stream;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import us.lsi.flujossecuenciales.Iterators;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.ToEGraph;
import us.lsi.path.EGraphPath.PathType;

public class DephtSearch<V, E> implements GSearch<V,E>, Iterator<V>, Iterable<V> {


	protected Map<V,E> edgeToOrigin;
	public Graph<V,E> graph;
	protected Stack<V> stack;
	protected V startVertex; 

	DephtSearch(Graph<V, E> g, V startVertex) {
		this.graph = g;
		this.startVertex = startVertex;
		this.edgeToOrigin = new HashMap<>();
		this.edgeToOrigin.put(startVertex, null);
		this.stack = new Stack<>();
		this.stack.add(startVertex);
	}
	
	@Override
	public Stream<V> stream() {
		return Iterators.asStream(this.iterator());
	}
	
	@Override
	public DephtSearch<V, E> copy() {
		return GSearch.depth(this.graph, this.startVertex);
	}
	
	public Iterator<V> iterator() {
		return this;
	}
	
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

	public E getEdgeToOrigin(V v) {
		return this.edgeToOrigin.get(v);
	}

	@Override
	public EGraph<V, E> getGraph() {
		return ToEGraph.of(this.graph,startVertex(),PathType.Sum);
	}
	
	@Override
	public V startVertex() {
		return this.startVertex;
	}	

}
