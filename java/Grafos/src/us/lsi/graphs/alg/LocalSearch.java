package us.lsi.graphs.alg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.streams.Stream2;

public class LocalSearch<V,E> implements  Iterator<V>, Iterable<V>{
	
	public static <V, E> LocalSearch<V, E> of(EGraph<V, E> graph, Double error, Integer n) {
		return new LocalSearch<V, E>(graph, error, n);
	}

	private EGraph<V,E> graph;
	private V actualVertex;
	private V startVertex;
	private List<V> path;
	private V oldVertex;
	private Double error;
	private Boolean hasNext = true;
	private Integer n;
	
	LocalSearch(EGraph<V, E> graph, Double error, Integer n) {
		this.graph = graph;
		this.startVertex = graph.startVertex();
		this.actualVertex = this.startVertex;
		this.path = new ArrayList<>();
		this.oldVertex = null;
		this.error = error;
		this.hasNext = true;
		this.n = n;
	}
	
	public LocalSearch<V,E> copy(){
		return LocalSearch.of(this.graph,this.error,this.n);	
	}
	
	public Stream<V> stream() {
		return Stream2.of(this);
	}
	
	public Optional<V> search(){
		return Stream2.findLast(this.stream());
	}
	
	public Iterator<V> iterator() {
		return this;
	}

	public V nextVertex(V v) {
		List<E> ls1 = graph.edgesListOf(v).stream()
			.filter(e -> this.graph.getVertexWeight(v) > this.graph.getVertexWeight(this.graph.oppositeVertex(e, v)))
			.collect(Collectors.toList());
		List<E> ls2 = List2.random(ls1, n);
		return ls2.stream().map(e -> this.graph.oppositeVertex(e, v))
				.min(Comparator.comparing(x -> this.graph.getVertexWeight(x)))
				.orElse(null);
	}

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
		this.actualVertex = this.nextVertex(this.oldVertex);
		this.hasNext = this.actualVertex != null && !this.path.contains(this.actualVertex) && 
				Math.abs(graph.getVertexWeight(this.oldVertex) - graph.getVertexWeight(this.actualVertex)) >= this.error;
		return this.oldVertex;
	}
	
	public V startVertex() {
		return this.startVertex;
	}
	
}
