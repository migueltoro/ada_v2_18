package us.lsi.graphs.alg;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
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
		if (hasNext) {
			E edge = this.nextEdge.apply(this.actualVertex);
			this.actualVertex = this.graph.getEdgeTarget(edge);
			this.edgeToOrigin.put(this.actualVertex, edge);
			this.weight = this.path.add(this.weight, edge);
			this.path.add(edge);
		}
//		System.out.println(old);
		return old;
	}

	@Override
	public V startVertex() {
		return this.startVertex;
	}

	@Override	
	public Optional<Double> weight() {
		Optional<V> s = this.find(goal);
		Optional<Double> r = Optional.empty();
		if(s.isPresent()) r = Optional.of(this.weight);
		return r;
	}
	
	
	public EGraphPath<V,E> search() {	
		this.find(goal);
		return this.path;
	}

}
