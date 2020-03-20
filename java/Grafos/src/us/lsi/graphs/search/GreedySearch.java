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

public class GreedySearch<V, E extends ActionSimpleEdge<V,A>, A> implements GraphSearch<V,E> {
	
	private Graph<V,E> graph;
	private V actualVertex;
	private V startVertex;
	private Map<V,E> edgeToOrigin;
	private TriFunction<V,V,A,E> nextEdge;
	private Function<V,A> nextAction;	
	private BiFunction<V,A,V> nextVertex;
	
	GreedySearch(V startVertex, Function<V,A> nextAction,BiFunction<V,A,V> nextVertex,TriFunction<V,V,A,E> nextEdge) {
		this.graph = Graphs2.simpleWeightedGraph();
		this.graph.addVertex(startVertex);
		this.actualVertex = null;
		this.startVertex = startVertex;
		this.edgeToOrigin = new HashMap<>();
//		this.edgeToOrigin.put(startVertex, null);
		this.nextEdge = nextEdge;
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
		return true;
	}

	@Override
	public V next() {
		if(this.actualVertex == null) {
			this.edgeToOrigin.put(this.startVertex,null);
			this.actualVertex = this.startVertex;
			this.graph.addVertex(this.actualVertex);
		} else {
			V old = this.actualVertex;
			A a = this.nextAction.apply(old);
			this.actualVertex = this.nextVertex.apply(old,a);
			E edge = this.nextEdge.apply(old,this.actualVertex,a);
			this.edgeToOrigin.put(this.actualVertex,edge);
			this.graph.addVertex(this.actualVertex);
			this.graph.addEdge(old, this.actualVertex, edge);
			this.graph.setEdgeWeight(edge,edge.getEdgeWeight());
		}
		return this.actualVertex;
	}
	
	@Override
	public V initialVertex() {
		return this.startVertex;
	}	

}
