package us.lsi.graphs.virtual;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.GraphType;

import us.lsi.common.TriFunction;
import us.lsi.path.EGraphPath;
import us.lsi.path.EGraphPath.PathType;

@SuppressWarnings("deprecation")
public class ToEGraph<V,E, G extends Graph<V,E>> implements EGraph<V, E> {
	
	public static <V, E, G extends Graph<V, E>> ToEGraph<V, E, G> of(G graph, V startVertex, PathType type, Function<E, Double> edgeWeight,
			Function<V, Double> vertexWeight, TriFunction<V, E, E, Double> vertexPassWeight) {
		return new ToEGraph<V, E, G>(graph, startVertex, type,edgeWeight, vertexWeight, vertexPassWeight);
	}
	
	public static <V, E, G extends Graph<V, E>> ToEGraph<V, E, G> of(G graph, V startVertex, PathType type, Function<E, Double> edgeWeight, 
			Function<V, Boolean> isBaseCase) {
		return new ToEGraph<V, E, G>(graph, startVertex, type, edgeWeight, null, null);
	}
	
	public static <V, E, G extends Graph<V, E>> ToEGraph<V, E, G> of(G graph,V startVertex,PathType type) {
		return new ToEGraph<V, E, G>(graph, startVertex, type, null, null, null);
	}

	private G graph;
	private Function<E,Double> edgeWeight = null;
	private Function<V,Double> vertexWeight = null;
	private TriFunction<V,E,E,Double> vertexPassWeight= null;
	private EGraphPath<V,E> path;
	private V startVertex;
	private PathType type;
	
	private ToEGraph(G graph, V startVertex, PathType type, Function<E, Double> edgeWeight, Function<V, Double> vertexWeight,
			TriFunction<V, E, E, Double> vertexPassWeight) {
		super();
		this.graph = graph;
		this.edgeWeight = edgeWeight;
		this.vertexWeight = vertexWeight;
		this.vertexPassWeight = vertexPassWeight;
		this.type = PathType.Sum;
		this.startVertex = startVertex;
		this.type = type;
	}

	public boolean addEdge(V arg0, V arg1, E arg2) {
		return graph.addEdge(arg0, arg1, arg2);
	}

	public E addEdge(V arg0, V arg1) {
		return graph.addEdge(arg0, arg1);
	}

	public V addVertex() {
		return graph.addVertex();
	}

	public boolean addVertex(V arg0) {
		return graph.addVertex(arg0);
	}

	public boolean containsEdge(E arg0) {
		return graph.containsEdge(arg0);
	}

	public boolean containsEdge(V arg0, V arg1) {
		return graph.containsEdge(arg0, arg1);
	}

	public boolean containsVertex(V arg0) {
		return graph.containsVertex(arg0);
	}

	public int degreeOf(V arg0) {
		return graph.degreeOf(arg0);
	}

	public Set<E> edgeSet() {
		return graph.edgeSet();
	}

	public Set<E> edgesOf(V v) {
		return graph.edgesOf(v);
	}

	public Set<E> getAllEdges(V arg0, V arg1) {
		return graph.getAllEdges(arg0, arg1);
	}

	public E getEdge(V arg0, V arg1) {
		return graph.getEdge(arg0, arg1);
	}

	public EdgeFactory<V, E> getEdgeFactory() {
		return graph.getEdgeFactory();
	}

	public V getEdgeSource(E arg0) {
		return graph.getEdgeSource(arg0);
	}

	public Supplier<E> getEdgeSupplier() {
		return graph.getEdgeSupplier();
	}

	public V getEdgeTarget(E arg0) {
		return graph.getEdgeTarget(arg0);
	}

	public GraphType getType() {
		return graph.getType();
	}

	public Supplier<V> getVertexSupplier() {
		return graph.getVertexSupplier();
	}

	public int inDegreeOf(V arg0) {
		return graph.inDegreeOf(arg0);
	}

	public Set<E> incomingEdgesOf(V arg0) {
		return graph.incomingEdgesOf(arg0);
	}

	public int outDegreeOf(V arg0) {
		return graph.outDegreeOf(arg0);
	}

	public Set<E> outgoingEdgesOf(V arg0) {
		return graph.outgoingEdgesOf(arg0);
	}

	public boolean removeAllEdges(Collection<? extends E> arg0) {
		return graph.removeAllEdges(arg0);
	}

	public Set<E> removeAllEdges(V arg0, V arg1) {
		return graph.removeAllEdges(arg0, arg1);
	}

	public boolean removeAllVertices(Collection<? extends V> arg0) {
		return graph.removeAllVertices(arg0);
	}

	public boolean removeEdge(E arg0) {
		return graph.removeEdge(arg0);
	}

	public E removeEdge(V arg0, V arg1) {
		return graph.removeEdge(arg0, arg1);
	}

	public boolean removeVertex(V arg0) {
		return graph.removeVertex(arg0);
	}

	public void setEdgeWeight(E arg0, double arg1) {
		graph.setEdgeWeight(arg0, arg1);
	}

	public Set<V> vertexSet() {
		return graph.vertexSet();
	}

	
	@Override
	public double getEdgeWeight(E edge) {
		Double r;
		if(edgeWeight != null) r = edgeWeight.apply(edge);
		else r = graph.getEdgeWeight(edge);
		return r;
	}
	
	/**
	 * @param vertex es el vértice actual
	 * @return El peso de vertex
	 */
	public double getVertexWeight(V vertex) {
		Double r = 0.;
		if(vertexWeight != null) r = vertexWeight.apply(vertex);
		return r;
	}
	/**
	 * @param vertex El vértice actual
	 * @param edgeIn Una arista entrante o incidente en el vértice actual. Es null en el vértice inicial.
	 * @param edgeOut Una arista saliente o incidente en el vértice actual. Es null en el vértice final.
	 * @return El peso asociado al vértice suponiendo las dos aristas dadas. 
	 */
	public double getVertexPassWeight(V vertex, E edgeIn, E edgeOut) {
		Double r = 0.;
		if(vertexPassWeight != null) r = vertexPassWeight.apply(vertex,edgeIn,edgeOut);
		return r;
	}
	
	@Override
	public List<E> edgesListOf(V v) {
		return graph.edgesOf(v).stream().collect(Collectors.toList());
	}
	
	@Override
	public EGraphPath<V, E> initialPath() {
		if(this.path == null) {
			this.path = EGraphPath.ofVertex(this,this.startVertex,this.type);
		}
		return this.path.copy();
	}
	@Override
	public V startVertex() {
		return startVertex;
	}
	@Override
	public PathType pathType() {
		return type;
	}
	
	
}
