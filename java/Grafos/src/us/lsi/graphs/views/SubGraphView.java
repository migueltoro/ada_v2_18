package us.lsi.graphs.views;

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.GraphType;

@SuppressWarnings("deprecation")
public class SubGraphView<V, E, G extends Graph<V,E>> implements Graph<V, E> {

	@SuppressWarnings("unchecked")
	public static <V, E, G extends Graph<V,E>> G of(G graph, Predicate<V> vertices, Predicate<E> edges) {
		return (G) new SubGraphView<V, E, G>(graph, vertices, edges);
	}

	@SuppressWarnings("unchecked")
	public static <V, E, G extends Graph<V,E>> G of(G graph, Set<V> vertices) {
		return (G) new SubGraphView<V, E, G>(graph, vertices);
	}
	
	private G graph;
	private Predicate<V> vertices;
	private Predicate<E> edges;
	
	private SubGraphView(G graph, Set<V> vertices) {
		super();
		this.graph = graph;
		this.vertices = v->vertices.contains(v);
		this.edges = e->true;
	}

	private SubGraphView(G graph, Predicate<V> vertices, Predicate<E> edges) {
		super();
		this.graph = graph;
		this.vertices = vertices==null?v->true:vertices;
		this.edges = edges==null?e->true:edges;
	}

	public boolean addEdge(V v0, V v1, E e) {
		return graph.addEdge(v0, v1,e);
	}

	public E addEdge(V v0, V v1) {
		return graph.addEdge(v0, v1);
	}

	public V addVertex() {
		return graph.addVertex();
	}

	public boolean addVertex(V v0) {
		return graph.addVertex(v0);
	}

	public boolean containsEdge(E e) {
		return graph.containsEdge(e) && 
				edges.test(e) &&
				vertices.test(this.getEdgeSource(e)) && 
				vertices.test(this.getEdgeTarget(e));
	}

	public boolean containsEdge(V v0, V v1) {
		return graph.containsEdge(v0,v1) && 
				edges.test(graph.getEdge(v0,v1)) &&
				vertices.test(v0) && 
				vertices.test(v1);
	}

	public boolean containsVertex(V v) {
		return graph.containsVertex(v) &&
				vertices.test(v);
	}

	public int degreeOf(V v) {
		return graph.degreeOf(v);
	}

	public Set<E> edgeSet() {
		return graph.edgeSet().stream().filter(e->containsEdge(e)).collect(Collectors.toSet());
	}

	public Set<E> edgesOf(V v) {
		return graph.edgesOf(v).stream()
				.filter(e->containsEdge(e))
				.collect(Collectors.toSet());
	}

	public Set<E> getAllEdges(V v0, V v1) {
		return graph.getAllEdges(v1, v1).stream().filter(e->edges!=null?edges.test(e):true).collect(Collectors.toSet());
	}

	public E getEdge(V v0, V v1) {
		return graph.getEdge(v0, v1);
	}

	public EdgeFactory<V, E> getEdgeFactory() {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public V getEdgeSource(E e) {
		return graph.getEdgeSource(e);
	}

	public Supplier<E> getEdgeSupplier() {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public V getEdgeTarget(E e) {
		return graph.getEdgeTarget(e);
	}

	public double getEdgeWeight(E e) {
		return graph.getEdgeWeight(e);
	}

	public GraphType getType() {
		return graph.getType();
	}

	public Supplier<V> getVertexSupplier() {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public int inDegreeOf(V v) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public Set<E> incomingEdgesOf(V v) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public int outDegreeOf(V v) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public Set<E> outgoingEdgesOf(V v) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public boolean removeAllEdges(Collection<? extends E> c) {
		return graph.removeAllEdges(c);
	}

	public Set<E> removeAllEdges(V v0, V v1) {
		return graph.removeAllEdges(v0, v1);
	}

	public boolean removeAllVertices(Collection<? extends V> c) {
		return graph.removeAllVertices(c);
	}

	public boolean removeEdge(E e) {
		return graph.removeEdge(e);
	}

	public E removeEdge(V v0, V v1) {
		return graph.removeEdge(v0, v1);
	}

	public boolean removeVertex(V v) {
		return graph.removeVertex(v);
	}

	public void setEdgeWeight(E e, double w) {
		graph.setEdgeWeight(e, w);
	}

	public Set<V> vertexSet() {
		return graph.vertexSet().stream().filter(v->vertices.test(v)).collect(Collectors.toSet());
	}
	
}
