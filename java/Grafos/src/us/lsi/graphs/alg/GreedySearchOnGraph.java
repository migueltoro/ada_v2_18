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

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import us.lsi.common.Preconditions;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;
import us.lsi.streams.Stream2;

public class GreedySearchOnGraph<V,E> implements GraphAlg<V,E>, Iterator<V>, Iterable<V> {

	private EGraph<V,E> graph;
	private V actualVertex;
	private V startVertex;
	public Map<V,E> edgeToOrigin;
	private Double weight;
	private EGraphPath<V,E> path;
	private Function<V,E> nextEdge;
	private Predicate<V> goal;
	private Predicate<V> constraint;
	private Boolean hasNext;
	public Graph<V,E> outGraph;
	public Boolean withGraph = false;
	
	GreedySearchOnGraph(EGraph<V, E> graph, Function<V,E> nextEdge) {
		this.graph = graph;
		this.startVertex = graph.startVertex();
		this.actualVertex = this.startVertex;		
		this.edgeToOrigin = new HashMap<>();
		this.edgeToOrigin.put(this.actualVertex,null);
		this.nextEdge = nextEdge; 
		this.goal = graph.goal();
		this.constraint = graph.constraint();
		this.path = this.graph.initialPath();
		this.weight = path.getWeight();
		this.hasNext = true;
		Preconditions.checkNotNull(goal,"El predicado no puede ser null");
	}
	
	@Override
	public Stream<V> stream() {
		if(this.withGraph) outGraph = new SimpleDirectedWeightedGraph<>(null,null);
		return Stream2.asStream(this);
	}
	
	@Override
	public GreedySearchOnGraph<V,E> copy() {
		return GraphAlg.greedy(this.graph,this.nextEdge);
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
		if(this.withGraph) outGraph.addVertex(this.actualVertex);
		V old = this.actualVertex;
		this.hasNext = !this.goal.test(old);
		if (this.hasNext) {
			E edge = this.nextEdge.apply(old);
			this.actualVertex = Graphs.getOppositeVertex(this.graph,edge,old);
			if(this.withGraph) {
				outGraph.addVertex(old);
				outGraph.addEdge(old,this.actualVertex,graph.getEdge(old, this.actualVertex));
			}
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

	public Optional<Double> weightToEnd() {
		return this.search().map(g->g.getWeight());
	}
	
	
	public Optional<EGraphPath<V, E>> search() {
		EGraphPath<V, E> ePath = graph.initialPath();
		V startVertex = graph.startVertex();
		if (this.goal.test(startVertex))
			return Optional.of(ePath);
		Optional<V> last = this.stream().filter(this.goal).findFirst();
		if (!last.isPresent()) return Optional.empty();
		V end = last.get();
		if (!this.constraint.test(end)) return Optional.empty();
		E edge = this.getEdgeToOrigin(end);
		List<E> edges = new ArrayList<>();
		while (edge != null) {
			edges.add(edge);
			end = Graphs.getOppositeVertex(graph, edge, end);
			edge = this.getEdgeToOrigin(end);
		}
		Collections.reverse(edges);
		for (E e : edges) {
			ePath.add(e);
		}
		return Optional.of(ePath);
	}
}
