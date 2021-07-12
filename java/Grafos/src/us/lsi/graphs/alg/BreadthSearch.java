package us.lsi.graphs.alg;



import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import us.lsi.flujossecuenciales.Iterables;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.virtual.EGraph;

public class BreadthSearch<V,E> implements GraphAlg<V,E>, Iterator<V>, Iterable<V> {
	
	private Graph<V,E> graph;
	private V startVertex;
	public Map<V,E> edgeToOrigin;
	public Queue<V> queue;
	public Map<V,Integer> position;
	private Integer n = 0;
	public Graph<V,E> outGraph;
	public Boolean withGraph = false;
	

	BreadthSearch(Graph<V, E> g, V startVertex) {
		this.graph = g;
		this.startVertex = startVertex;
		this.edgeToOrigin = new HashMap<>();
		this.edgeToOrigin.put(startVertex, null);
		this.queue = new LinkedList<>();
		this.queue.add(startVertex);
		this.position = new HashMap<>();
	}

	@Override
	public Stream<V> stream() {
		if(this.withGraph) outGraph = new SimpleDirectedWeightedGraph<>(null,null);
		return Iterables.asStream(this);
	}
	
	@Override
	public BreadthSearch<V,E> copy() {
		return GraphAlg.breadth(this.graph, this.startVertex);
	}
	
	public Iterator<V> iterator() {
		return this;
	}


	public boolean isSeenVertex(V v) {
		return this.edgeToOrigin.containsKey(v);
	}
	
	public boolean hasNext() {
		return !this.queue.isEmpty();
	}

	@Override
	public V next() {
		V actual = this.queue.remove();
		if(this.withGraph) outGraph.addVertex(actual);
		for(V v:Graphs.neighborListOf(graph, actual)) {
			if(!this.edgeToOrigin.containsKey(v)) {
				this.queue.add(v);
				this.edgeToOrigin.put(v,graph.getEdge(actual, v));
				if(this.withGraph) {
					outGraph.addVertex(v);
					outGraph.addEdge(actual,v,graph.getEdge(actual, v));
				}
			}
		}
		this.position.put(actual,n);
		n++;
		return actual;
	}

	
	public E getEdgeToOrigin(V v) {
		return this.edgeToOrigin.get(v);
	}

	@Override
	public EGraph<V, E> getGraph() {
		return Graphs2.eGraphSum(this.graph,startVertex());
	}
	
	@Override
	public V startVertex() {
		return this.startVertex;
	}	
	
	public Double distanceToOrigen(V vertex) {
		Double r = 0.;
		while(!vertex.equals(startVertex)) {		
			E edge = this.getEdgeToOrigin(vertex);
			vertex = Graphs.getOppositeVertex(graph, edge,vertex);
			r = r+1;		
		}
		return r;
	}
	
	public Set<E> edges() {
		return this.edgeToOrigin.values().stream().collect(Collectors.toSet());
	}
}
