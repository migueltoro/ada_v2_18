package us.lsi.graphs.views;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.Graph;
import org.jgrapht.GraphType;


public class IntegerMappingGraphView<V,E> implements Graph<Integer,E>{
	
	public static <V, E> IntegerMappingGraphView<V, E> of(Graph<V, E> graph) {
		return new IntegerMappingGraphView<V, E>(graph);
	}

	private Map<V,Integer> map;
	private List<V> vertices;
	private Graph<V,E> graph;
	
	private IntegerMappingGraphView(Graph<V, E> graph) {
		super();
		this.graph = graph;
		this.vertices = graph.vertexSet().stream().collect(Collectors.toList());
		this.map = IntStream.range(0,this.vertices.size()).boxed()
				.collect(Collectors.toMap(x->this.vertices.get(x),x->x));
	}

	@Override
	public E addEdge(Integer arg0, Integer arg1) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	@Override
	public boolean addEdge(Integer arg0, Integer arg1, E arg2) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	@Override
	public Integer addVertex() {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	@Override
	public boolean addVertex(Integer arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	@Override
	public boolean containsEdge(E e) {
		return graph.containsEdge(e);
	}

	@Override
	public boolean containsEdge(Integer v1, Integer v2) {
		return graph.containsEdge(vertices.get(v1), vertices.get(v2));
	}

	@Override
	public boolean containsVertex(Integer v) {
		return v>=0 && v<vertices.size();
	}

	@Override
	public int degreeOf(Integer v) {
		return graph.degreeOf(vertices.get(v));
	}

	@Override
	public Set<E> edgeSet() {
		return graph.edgeSet();
	}

	@Override
	public Set<E> edgesOf(Integer v) {
		return graph.edgesOf(vertices.get(v));
	}

	@Override
	public Set<E> getAllEdges(Integer v1, Integer v2) {
		return graph.getAllEdges(vertices.get(v1), vertices.get(v2));
	}

	@Override
	public E getEdge(Integer v1, Integer v2) {		
		return graph.getEdge(vertices.get(v1), vertices.get(v2));
	}

	@Override
	public Integer getEdgeSource(E e) {
		return this.map.get(graph.getEdgeSource(e));
	}

	@Override
	public Supplier<E> getEdgeSupplier() {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	@Override
	public Integer getEdgeTarget(E e) {
		return this.map.get(graph.getEdgeTarget(e));
	}

	@Override
	public double getEdgeWeight(E e) {
		return graph.getEdgeWeight(e);
	}
	
	public double getEdgeWeight(int i, int j) {
		E e = getEdge(i,j);
		return graph.getEdgeWeight(e);
	}

	public V getVertex(int v) {
		return vertices.get(v);
	}
	public Integer getIndex(V v) {
		return map.get(v);
	}
	
	@Override
	public GraphType getType() {
		return graph.getType();
	}

	@Override
	public Supplier<Integer> getVertexSupplier() {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	@Override
	public int inDegreeOf(Integer v) {
		return graph.inDegreeOf(vertices.get(v));
	}

	@Override
	public Set<E> incomingEdgesOf(Integer v) {
		return graph.incomingEdgesOf(vertices.get(v));
	}

	@Override
	public int outDegreeOf(Integer v) {
		return graph.outDegreeOf(vertices.get(v));
	}

	@Override
	public Set<E> outgoingEdgesOf(Integer v) {
		return graph.outgoingEdgesOf(vertices.get(v));
	}

	@Override
	public boolean removeAllEdges(Collection<? extends E> arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	@Override
	public Set<E> removeAllEdges(Integer arg0, Integer arg1) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	@Override
	public boolean removeAllVertices(Collection<? extends Integer> arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	@Override
	public boolean removeEdge(E arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	@Override
	public E removeEdge(Integer arg0, Integer arg1) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	@Override
	public boolean removeVertex(Integer arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	@Override
	public void setEdgeWeight(E e, double w) {
		graph.setEdgeWeight(e, w);
	}

	@Override
	public Set<Integer> vertexSet() {
		return graph.vertexSet().stream().map(x->map.get(x)).collect(Collectors.toSet());
	}	
	
	@Override
	public String toString() {
		return String.format("%s === %s",this.vertexSet(),this.edgeSet());
	}
	
	public Integer index(V v) {
		return this.index(v);
	}
	
	public V vertex(Integer i) {
		return this.vertices.get(i);
	}

}
