package us.lsi.graphs.search;


import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;

public class GreedySearchOnGraph<V,E> implements Search<V,E> {

	private Graph<V,E> graph;
	private V actualVertex;
	private V startVertex;
	public Map<V,E> edgeToOrigin;
	private Comparator<E> nextEdge;
	
	GreedySearchOnGraph(Graph<V, E> graph, V startVertex, Comparator<E> nextEdge) {
		this.graph = graph;
		this.actualVertex = null;
		this.startVertex = startVertex;
		this.edgeToOrigin = new HashMap<>();
		this.nextEdge = nextEdge; 
	}
	
	private V nextVertex() {
		Optional<E> edge = this.graph.edgesOf(this.actualVertex)
				.stream()
				.filter(e->!this.edgeToOrigin.containsKey(Graphs.getOppositeVertex(this.graph,e,this.actualVertex)))
				.min(this.nextEdge);
		return edge.isPresent()?Graphs.getOppositeVertex(this.graph,edge.get(),this.actualVertex):null;
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
		return true;
	}

	@Override
	public V next() {
		if(this.actualVertex == null) {
			this.edgeToOrigin.put(this.startVertex,null);
			this.actualVertex = this.startVertex;
		} else {
			V old = this.actualVertex;
			this.actualVertex = this.nextVertex();
			E edge = this.graph.getEdge(old, this.actualVertex);
			this.edgeToOrigin.put(this.actualVertex,edge);
		}
		return this.actualVertex;
	}

}
