package us.lsi.graphs.search;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import us.lsi.common.Preconditions;
import us.lsi.flujossecuenciales.Iterators;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;

public class LocalSearch<V,E> implements GSearch<V,E>, Iterator<V>, Iterable<V>{
	
	public static <V, E> LocalSearch<V, E> of(EGraph<V, E> graph, Predicate<E> stop) {
		return new LocalSearch<V, E>(graph, stop);
	}

	private EGraph<V,E> graph;
	private V actualVertex;
	private V startVertex;
	public Map<V,E> edgeToOrigin;
	private Double weight;
	private EGraphPath<V,E> path;
	private Predicate<E> stop;
	private E nextEdge;
	
	LocalSearch(EGraph<V, E> graph, Predicate<E> stop) {
		this.graph = graph;
		this.startVertex = graph.startVertex();
		this.actualVertex = this.startVertex;
		this.edgeToOrigin = new HashMap<>();
		this.stop = stop;
		this.path = this.graph.initialPath();
		this.weight = path.getWeight();
		this.nextEdge = nextEdge(this.actualVertex);	
	}
	
	@Override
	public LocalSearch<V,E> copy(){
		return LocalSearch.of(this.graph,this.stop);	
	}
	
	@Override
	public Stream<V> stream() {
		return Iterators.asStream(this.iterator());
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
		return this.nextEdge != null && !this.stop.test(this.nextEdge);
	}
	
	private E nextEdge(V vertex) {
		Preconditions.checkNotNull(this.graph);
		Preconditions.checkNotNull(this.actualVertex);
		Preconditions.checkNotNull(this.path);
		Preconditions.checkNotNull(this.weight);
		return this.graph.edgesListOf(this.actualVertex).stream()
				.min(Comparator.comparing(e->graph.getEdgeWeight(e)))
				.get();
	}

	@Override
	public V next() {
		if(this.actualVertex == null) {
			this.edgeToOrigin.put(this.startVertex,null);
			this.actualVertex = this.startVertex;
			this.edgeToOrigin.put(this.actualVertex,null);
			this.path = this.graph.initialPath();
			this.weight = path.getWeight();
		} else {
			E edge = this.nextEdge;
			this.nextEdge =	nextEdge(this.actualVertex);
			this.actualVertex = this.graph.getEdgeTarget(edge);		
			this.edgeToOrigin.put(this.actualVertex,edge);
			this.weight = this.path.add(this.weight,edge);
		}
		return this.actualVertex;
	}
	
	@Override
	public V startVertex() {
		return this.startVertex;
	}
	
	

}
