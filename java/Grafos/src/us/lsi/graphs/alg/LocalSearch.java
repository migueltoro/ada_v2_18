package us.lsi.graphs.alg;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import org.jgrapht.Graphs;

import us.lsi.common.Preconditions;
import us.lsi.flujossecuenciales.Iterators;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;

public class LocalSearch<V,E> implements GraphAlg<V,E>, Iterator<V>, Iterable<V>{
	
	public static <V, E> LocalSearch<V, E> of(EGraph<V, E> graph, Double error) {
		return new LocalSearch<V, E>(graph, error);
	}

	private EGraph<V,E> graph;
	private V actualVertex;
	private V startVertex;
	public Map<V,E> edgeToOrigin;
	private Double weight;
	private EGraphPath<V,E> path;
	private E nextEdge;
	private Double error;
	
	LocalSearch(EGraph<V, E> graph, Double error) {
		this.graph = graph;
		this.startVertex = graph.startVertex();
		this.actualVertex = this.startVertex;
		this.edgeToOrigin = new HashMap<>();
		this.path = this.graph.initialPath();
		this.weight = path.getWeight();
		this.nextEdge = nextEdge(this.actualVertex);	
		this.error = error;
	}
	
	public V search() {
		return findEnd();
	}
	
	@Override
	public LocalSearch<V,E> copy(){
		return LocalSearch.of(this.graph,this.error);	
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
		Preconditions.checkNotNull(this.graph,"Graph null");
		Preconditions.checkNotNull(this.error, "Error null");
		return this.nextEdge != null && Math.abs(this.graph.getEdgeWeight(this.nextEdge)) > this.error;
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
			V old = this.actualVertex;
			E edge = this.nextEdge;
			this.nextEdge =	this.nextEdge(this.actualVertex);
			this.actualVertex = Graphs.getOppositeVertex(graph,edge,old);		
			this.edgeToOrigin.put(this.actualVertex,edge);
			this.weight = this.path.add(this.weight,this.actualVertex,edge,this.edgeToOrigin.get(old));
		}
		return this.actualVertex;
	}
	
	@Override
	public V startVertex() {
		return this.startVertex;
	}
	
}
