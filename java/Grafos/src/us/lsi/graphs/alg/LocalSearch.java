package us.lsi.graphs.alg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import us.lsi.graphs.virtual.EGraph;
import us.lsi.streams.Stream2;

public class LocalSearch<V,E> implements GraphAlg<V,E>, Iterator<V>, Iterable<V>{
	
	public static <V, E> LocalSearch<V, E> of(EGraph<V, E> graph, Function<V,V> nextVertex, Double error) {
		return new LocalSearch<V, E>(graph, nextVertex, error);
	}

	private EGraph<V,E> graph;
	private V actualVertex;
	private V startVertex;
	public Map<V,E> edgeToOrigin;
	private List<V> path;
	private Function<V,V> nextVertex;
	private V oldVertex;
	private Double error;
	private Boolean hasNext = true;
	
	LocalSearch(EGraph<V, E> graph, Function<V,V> nextVertex, Double error) {
		this.graph = graph;
		this.startVertex = graph.startVertex();
		this.actualVertex = this.startVertex;
		this.edgeToOrigin = new HashMap<>();
		this.path = new ArrayList<>();
		this.nextVertex = nextVertex;
		this.oldVertex = null;
		this.error = error;
		this.hasNext = true;
	}
	
	public V search() {
		return findEnd();
	}
	
	@Override
	public LocalSearch<V,E> copy(){
		return LocalSearch.of(this.graph,this.nextVertex,this.error);	
	}
	
	@Override
	public Stream<V> stream() {
		return Stream2.asStream(this);
	}
	
	public Iterator<V> iterator() {
		return this;
	}

	
	public E getEdgeToOrigin(V v) {
		return this.edgeToOrigin.get(v);
	}

	
	public boolean isSeenVertex(V v) {
		return this.edgeToOrigin.containsKey(v);
	}

	@Override
	public EGraph<V, E> getGraph() {
		return graph;
	}
	
	@Override
	public boolean hasNext() {
		return hasNext;
	}

	@Override
	public V next() {
		this.oldVertex = this.actualVertex;
		this.path.add(oldVertex);
		this.actualVertex = this.nextVertex.apply(this.oldVertex);
		this.hasNext = !this.actualVertex.equals(this.oldVertex) && !this.path.contains(this.actualVertex) &&
				Math.abs(graph.getVertexWeight(this.oldVertex) - graph.getVertexWeight(this.actualVertex)) >= this.error;
		return this.oldVertex;
	}
	
	@Override
	public V startVertex() {
		return this.startVertex;
	}
	
}
