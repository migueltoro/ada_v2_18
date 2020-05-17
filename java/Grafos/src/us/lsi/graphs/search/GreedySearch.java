package us.lsi.graphs.search;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import us.lsi.flujossecuenciales.Iterators;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;

public class GreedySearch<V,E> implements GSearch<V,E>, Iterator<V>, Iterable<V> {

	private EGraph<V,E> graph;
	private V actualVertex;
	private V startVertex;
	public Map<V,E> edgeToOrigin;
	private Double weight;
	private EGraphPath<V,E> path;
	private Function<V,E> nextEdge;
	private Predicate<V> goal;
	private Boolean hashNext;
	
	GreedySearch(EGraph<V, E> graph, Function<V,E> nextEdge, Predicate<V> goal) {
		this.graph = graph;
		this.actualVertex = null;
		this.startVertex = graph.startVertex();
		this.edgeToOrigin = new HashMap<>();
		this.nextEdge = nextEdge; 
		this.goal = goal;
		this.hashNext = true;
	}
	
	@Override
	public Stream<V> stream() {
		return Iterators.asStream(this.iterator());
	}
	
	@Override
	public GreedySearch<V,E> copy() {
		return GSearch.greedy(this.graph,this.nextEdge,this.goal);
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
		return this.hashNext;
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
			E edge = this.nextEdge.apply(this.actualVertex);
			this.actualVertex = this.graph.getEdgeTarget(edge);		
			this.edgeToOrigin.put(this.actualVertex,edge);
			this.weight = this.path.add(this.weight,edge);
		}
		this.hashNext = !this.goal.test(this.actualVertex);
		return this.actualVertex;
	}
	
	@Override
	public V startVertex() {
		return this.startVertex;
	}
	
	@Override	
	public Double weightToEnd() {
		this.findEnd();
		return this.weight;
	}

}
