package us.lsi.graphs.search;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.jgrapht.Graph;

import us.lsi.common.TriFunction;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.virtual.ActionSimpleEdge;

public class GreedySearch<V, E extends ActionSimpleEdge<V,A>, A> implements Search<V,E> {
	
	private Graph<V,E> graph;
	private V actualVertex;
	private Map<V,E> edgeToOrigin;
	private TriFunction<V,V,A,E> factory;
	private Function<V,A> nextAction;	
	private BiFunction<V,A,V> nextVertex;
	
	GreedySearch(V startVertex, Function<V,A> nextAction,BiFunction<V,A,V> nextVertex, TriFunction<V,V,A,E> factory) {
		this.graph = Graphs2.simpleWeightedGraph();
		this.graph.addVertex(startVertex);
		this.actualVertex = startVertex;
		this.edgeToOrigin = new HashMap<>();
		this.edgeToOrigin.put(this.actualVertex, null);
		this.factory = factory;
		this.nextAction = nextAction;	
		this.nextVertex = nextVertex; 
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
		V old = this.actualVertex;
		A a = this.nextAction.apply(old);
		V v = this.nextVertex.apply(old,a);
		return !this.edgeToOrigin.containsKey(v);
	}

	@Override
	public V next() {
		V old = this.actualVertex;
		A a = this.nextAction.apply(old);
		this.actualVertex = this.nextVertex.apply(old,a);
		E edge = this.factory.apply(this.actualVertex,old,a);
		this.edgeToOrigin.put(old,edge);
		this.graph.addVertex(this.actualVertex);
		this.graph.addEdge(old, this.actualVertex, edge);
		return old;
	}

}
