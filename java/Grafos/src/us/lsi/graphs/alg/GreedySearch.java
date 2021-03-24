package us.lsi.graphs.alg;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.jgrapht.Graphs;
import us.lsi.flujossecuenciales.Iterators;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;

public class GreedySearch<V,E> implements GraphAlg<V,E>, Iterator<V>, Iterable<V> {

	private EGraph<V,E> graph;
	private V actualVertex;
	private V startVertex;
	public Map<V,E> edgeToOrigin;
	private Double weight;
	private EGraphPath<V,E> path;
	private Function<V,E> nextEdge;
	private Predicate<V> goal;
	private Boolean hasNext;
	
	GreedySearch(EGraph<V, E> graph, Function<V,E> nextEdge, Predicate<V> goal) {
		this.graph = graph;
		this.startVertex = graph.startVertex();
		this.actualVertex = this.startVertex;		
		this.edgeToOrigin = new HashMap<>();
		this.edgeToOrigin.put(this.actualVertex,null);
		this.nextEdge = nextEdge; 
		this.goal = goal;
		this.path = this.graph.initialPath();
		this.weight = path.getWeight();
		this.hasNext = true;
	}
	
	@Override
	public Stream<V> stream() {
		return Iterators.asStream(this.iterator());
	}
	
	@Override
	public GreedySearch<V,E> copy() {
		return GraphAlg.greedy(this.graph,this.nextEdge,this.goal);
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
		return this.hasNext;
	}

	@Override
	public V next() {
		V old = this.actualVertex;
		this.hasNext = !this.goal.test(old);
		if (this.hasNext) {
			E edge = this.nextEdge.apply(old);
			this.actualVertex = Graphs.getOppositeVertex(this.graph,edge,old);
			this.edgeToOrigin.put(this.actualVertex,edge);
			E edgeToOrigin = this.edgeToOrigin.get(old);
			if(edgeToOrigin == null) this.weight = this.path.getWeight();
			else this.weight = this.path.add(this.weight,this.actualVertex,edge,edgeToOrigin);			
			this.path.add(edge);
		}
		return old;
	}

	@Override
	public V startVertex() {
		return this.startVertex;
	}

	public Double weightToEnd() {
		return search().getWeight();
	}
	
	
	public EGraphPath<V,E> search() {
		EGraphPath<V,E> ePath = graph.initialPath();
		V startVertex = graph.startVertex();
		if(this.goal.test(startVertex)) return ePath;
		Optional<V> last = this.stream().filter(this.goal).findFirst();
		if(last.isPresent()) {
			V end = last.get();
			E edge = this.getEdgeToOrigin(end);
			List<E> edges = new ArrayList<>();		
			while(edge!=null) {				
				edges.add(edge);
				end = Graphs.getOppositeVertex(graph, edge, end);
				edge = this.getEdgeToOrigin(end);			
			}
			Collections.reverse(edges);
			for(E e:edges) {
				ePath.add(e);
			}
		}
		return ePath;
	}
}
