package us.lsi.graphs.views;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.GraphType;

import us.lsi.common.TriFunction;

@SuppressWarnings("deprecation")
public class CompleteGraphView<V, E, G extends Graph<V,E>> implements Graph<V,E> {

	@SuppressWarnings("unchecked")
	public static <V, E, G extends Graph<V,E>> G of(G graph, TriFunction<V, V, Double, E> edgeWeightFactory,
			Double weightDefault, Function<E, Double> weight, Function<E,V> source, Function<E,V> target) {
		return (G) new CompleteGraphView<V, E, G>(graph, edgeWeightFactory, weightDefault, weight, source, target);
	}

	private CompleteGraphView(G graph, TriFunction<V, V, Double, E> edgeWeightFactory, Double weightValue, Function<E, Double> weight,
			Function<E,V> source, Function<E,V> target) {
		super();
		this.graph = graph;
		this.edgeWeightFactory = edgeWeightFactory;
		this.weightDefault = weightValue;
		this.weight = weight;
		this.source = source;
		this.target = target;
		this.n= graph.vertexSet().size();
	}

	private G graph;
	private TriFunction<V, V, Double, E> edgeWeightFactory;
	private Double weightDefault;
	private Function<E,Double> weight;
	private Function<E,V> source;
	private Function<E,V> target;
	private final Integer n;

	public Graph<V, E> getGraph() {
		return graph;
	}

	public Double getWeightValue() {
		return weightDefault;
	}

	public TriFunction<V, V, Double, E> getEdgeWeightFactory() {
		return this.edgeWeightFactory;
	}

	public boolean addEdge(V arg0, V arg1, E arg2) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public E addEdge(V arg0, V arg1) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public V addVertex() {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public boolean addVertex(V arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public boolean containsEdge(E e) {
		return graph.containsVertex(this.getEdgeSource(e)) && graph.containsVertex(this.getEdgeSource(e));
	}

	public boolean containsEdge(V v0, V v1) {
		return graph.containsVertex(v0) && graph.containsVertex(v1);
	}

	public boolean containsVertex(V v) {
		return graph.containsVertex(v);
	}

	public int degreeOf(V v) {
		return n-1;
	}

	public Set<E> edgeSet() {
		return graph.vertexSet().stream().<E>flatMap(x->edgesOf(x).stream()).collect(Collectors.toSet());
	}

	public Set<E> edgesOf(V v) {
		return graph.vertexSet().stream().filter(x->!x.equals(v)).map(x->getEdge(v,x)).collect(Collectors.toSet());
	}

	public Set<E> getAllEdges(V v0, V v1) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public E getEdge(V v0, V v1) {
		E edge = null;
		if(graph.containsEdge(v0, v1))
			edge = graph.getEdge(v0, v1);
		else 
			edge = this.edgeWeightFactory.apply(v0,v1,this.weightDefault);
		return edge;
	}
	
	public EdgeFactory<V, E> getEdgeFactory() {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public V getEdgeSource(E e) {
		V v = null;
		if(graph.containsEdge(e))
			v = graph.getEdgeSource(e);
		else 
			v = this.source.apply(e);
		return v;
	}

	public Supplier<E> getEdgeSupplier() {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public V getEdgeTarget(E e) {
		V v = null;
		if(graph.containsEdge(e))
			v = graph.getEdgeTarget(e);
		else 
			v = this.target.apply(e);
		return v;
	}

	public double getEdgeWeight(E e) {
		return weight.apply(e);
	}

	public GraphType getType() {
		return graph.getType();
	}

	public Supplier<V> getVertexSupplier() {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public int inDegreeOf(V arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public Set<E> incomingEdgesOf(V arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public int outDegreeOf(V arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public Set<E> outgoingEdgesOf(V arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public boolean removeAllEdges(Collection<? extends E> arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public Set<E> removeAllEdges(V arg0, V arg1) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public boolean removeAllVertices(Collection<? extends V> arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public boolean removeEdge(E arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public E removeEdge(V arg0, V arg1) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public boolean removeVertex(V arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public void setEdgeWeight(E arg0, double arg1) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public Set<V> vertexSet() {
		return graph.vertexSet();
	}	

}
