package us.lsi.graphs.virtual;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

import org.jgrapht.EdgeFactory;
import org.jgrapht.GraphType;

import us.lsi.common.Sets2;
import us.lsi.common.TriFunction;
import us.lsi.graphs.SimpleEdge;
import us.lsi.path.EGraphPath;
import us.lsi.path.EGraphPath.PathType;

/**
 * <p> Implementación de un grafo virtual simple 
 * Asumimos que los vértices son subtipo de VirtualVertex &lt; V,E &gt;
 * Asumimos que las aristas son subtipos de SimpleEdge &lt; V &gt; 
 * </p>
 * 
 * <p> El grafo es inmutable por lo que no están permitadas las operación de modificación. Tampoco
 * están permitidas las operaciones de consulta de todos los vértices o todas las aristas.
 *  Si se invocan alguna de ellas se disparará 
 * la excepción UnsupportedOperationException </p>
 * 
 * @see us.lsi.graphs.virtual.VirtualVertex
 * 
 * 
 * 
 * @author Miguel Toro
 *
 * @param <V> El tipo de los vértices
 * @param <E> El tipo de las aristas
 * 
 */
@SuppressWarnings("deprecation")
public class SimpleVirtualGraph<V extends VirtualVertex<V,E>, E extends SimpleEdge<V>>
	implements EGraph<V, E> {
	
	public static <V extends VirtualVertex<V, E>, E extends SimpleEdge<V>> SimpleVirtualGraph<V, E> of(V startVertex) {
		return new SimpleVirtualGraph<V, E>(startVertex,PathType.Sum,null, null, null);
	}
	
	public static <V extends VirtualVertex<V, E>, E extends SimpleEdge<V>> SimpleVirtualGraph<V, E> sum(
			V startVertex,
			Function<E, Double> edgeWeight) {
		return new SimpleVirtualGraph<V, E>(startVertex,PathType.Sum,edgeWeight,null,null);
	}
	
//	public static <V extends VirtualVertex<V, E>, E extends SimpleEdge<V>> SimpleVirtualGraph<V, E> of(
//			V startVertex,
//			PathType type,
//			Function<E, Double> edgeWeight) {
//		return new SimpleVirtualGraph<V, E>(startVertex,type,edgeWeight,null,null);
//	}
	
	public static <V extends VirtualVertex<V, E>, E extends SimpleEdge<V>> SimpleVirtualGraph<V, E> last(
			V startVertex,
			Function<V, Double> vertexWeight) {
		return new SimpleVirtualGraph<V, E>(startVertex,PathType.Last,null,vertexWeight,null);
	}
	
	public static <V extends VirtualVertex<V, E>, E extends SimpleEdge<V>> SimpleVirtualGraph<V, E> of(
			V startVertex,
			Function<E, Double> edgeWeight, 
			Function<V, Double> vertexWeight,
			TriFunction<V, E, E, Double> vertexPassWeight,
			PathType type) {
		return new SimpleVirtualGraph<V, E>(startVertex,type,edgeWeight,vertexWeight,vertexPassWeight);
	}

	private Set<V> vertexSet;
	private Function<E,Double> edgeWeight = null;
	private Function<V,Double> vertexWeight = null;
	private TriFunction<V,E,E,Double> vertexPassWeight= null;
	private EGraphPath<V,E> path;
	private V startVertex;
	private PathType type;
	
	
	protected SimpleVirtualGraph(V startVertex, 
			PathType type,			
			Function<E, Double> edgeWeight, 
			Function<V, Double> vertexWeight,
			TriFunction<V, E, E, Double> vertexPassWeight) {
		super();
		this.vertexSet = Sets2.empty();;
		this.edgeWeight = edgeWeight;
		this.vertexWeight = vertexWeight;
		this.vertexPassWeight = vertexPassWeight;
		this.type = PathType.Sum;
		this.startVertex = startVertex;
		this.vertexSet = new HashSet<V>();
		this.vertexSet.add(this.startVertex);
		this.type = type;
		this.path = EGraphPath.ofVertex(this,this.startVertex,this.type);
	}

	@Override
	public boolean containsEdge(E e) {
		return e.getSource().isNeighbor(e.getTarget());
	}

	@Override
	public boolean containsEdge(V v1, V v2) {
		return v1.isNeighbor(v2);
	}
	@Override
	public boolean containsVertex(V v) {
		return v.isValid();
	}
	@Override
	public V getEdgeSource(E e) {
		return e.getSource();
	}

	@Override
	public V getEdgeTarget(E e) {
		return e.getTarget();
	}
	
	/**
	 * @param edge es una arista
	 * @return El peso de edge
	 */
	@Override
	public double getEdgeWeight(E edge) {
		Double r;
		if(edgeWeight != null) r = edgeWeight.apply(edge);
		else r = edge.getEdgeWeight();
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
	public E getEdge(V v1, V v2) {
		return v1.getEdge(v2);
	}	
	@Override
	public Set<E> getAllEdges(V v1, V v2) {
		Set<E> s = new HashSet<>();
		if (v1.isNeighbor(v2)) s = Set.of(this.getEdge(v1, v2));
		return s;
	}	
	
	/** 
	 * @return Conjunto de vértices del grafo que se han hecho explícitos en el constructor.
	 */
	@Override
	public Set<V> vertexSet(){
		return this.vertexSet;
	}
	
	@Override
	public Set<E> edgesOf(V v) {
		return v.edgesOf();
	}
	@Override
	public List<E> edgesListOf(V v) {
		return v.edgesListOf();
	}
	
	@Override
	public int degreeOf(V v) {
		return v.edgesOf().size();
	}
	
	@Override
	public int outDegreeOf(V v) {
		return v.edgesOf().size();
	}

	@Override
	public Set<E> outgoingEdgesOf(V v) {
		return edgesOf(v);
	}
	
	@Override
	public void setEdgeWeight(E edge, double weight) {
		edge.setWeight(weight);		
	}	

	/**
	 * @throw UnsupportedOperationException
	 */
	@Override
	public int inDegreeOf(V v) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @throw UnsupportedOperationException
	 */
	@Override
	public Set<E> incomingEdgesOf(V v) {
		throw new UnsupportedOperationException();
	}

	
	/**
	 * @throw UnsupportedOperationException
	 */
	@Override
	public GraphType getType() {
		throw new UnsupportedOperationException();
	}	
	
	/**
	 * @throw UnsupportedOperationException
	 */
	@Override
	public Set<E> edgeSet() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @throw UnsupportedOperationException
	 */
	@Override
	public E addEdge(V arg0, V arg1) {
		throw new UnsupportedOperationException();
	}

	
	/**
	 * @throw UnsupportedOperationException
	 */
	@Override
	public boolean addEdge(V arg0, V arg1, E arg2) {
		throw new UnsupportedOperationException();
	}


	/**
	 * @throw UnsupportedOperationException
	 */
	@Override
	public boolean addVertex(V arg0) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @throw UnsupportedOperationException
	 */
	@Override
	public boolean removeAllEdges(Collection<? extends E> arg0) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @throw UnsupportedOperationException
	 */
	@Override
	public Set<E> removeAllEdges(V arg0, V arg1) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @throw UnsupportedOperationException
	 */
	@Override
	public boolean removeAllVertices(Collection<? extends V> arg0) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @throw UnsupportedOperationException
	 */
	@Override
	public boolean removeEdge(E arg0) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @throw UnsupportedOperationException
	 */
	@Override
	public E removeEdge(V arg0, V arg1) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @throw UnsupportedOperationException
	 */
	@Override
	public boolean removeVertex(V arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public V addVertex() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Supplier<E> getEdgeSupplier() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Supplier<V> getVertexSupplier() {
		throw new UnsupportedOperationException();
	}

	@Override
	public EdgeFactory<V, E> getEdgeFactory() {
		throw new UnsupportedOperationException();
	}

	@Override
	public EGraphPath<V, E> initialPath() {
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
