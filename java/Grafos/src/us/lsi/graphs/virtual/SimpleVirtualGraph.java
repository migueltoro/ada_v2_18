package us.lsi.graphs.virtual;

import java.util.*;
import java.util.function.Supplier;

import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.GraphType;

import us.lsi.common.Sets2;
import us.lsi.graphs.SimpleEdge;

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
public class SimpleVirtualGraph<V extends VirtualVertex<E,V>, E extends SimpleEdge<V>>
	implements Graph<V, E> {
			
	private Set<V> vertexSet;
    @SuppressWarnings("unused")
	private boolean weighted;
	
	public SimpleVirtualGraph() {
		this.vertexSet = Sets2.newHashSet();
	    this.weighted = true;
	
	}

	/**
	 * @param vertexSet Conjunto de vértices del grafo que queremos hacer explícitos.
	 */
	@SafeVarargs
	public SimpleVirtualGraph(V... vertexSet){
		super();
		this.vertexSet = new HashSet<>();
		for(V v:vertexSet){
			this.vertexSet.add(v);
		}
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

	@Override
	public double getEdgeWeight(E e) {
		return e.getEdgeWeight();
	}
	
	@Override
	public E getEdge(V v1, V v2) {
		E a = null;
		if (v1.isNeighbor(v2)) {
			a = v1.edgesOf()
					.stream()
					.filter(e->e.otherVertex(v1).equals(v2))
					.findFirst()
					.get();
		}
		return a;
	}
	
	@Override
	public Set<E> getAllEdges(V v1, V v2) {
		Set<E> s = new HashSet<>();
		if (v1.isNeighbor(v2))
			s.add(getEdge(v1, v2));
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
	public int degreeOf(V v) {
		return v.edgesOf().size();
	}

	@Override
	public int inDegreeOf(V v) {
		return v.edgesOf().size();
	}

	@Override
	public Set<E> incomingEdgesOf(V v) {
		return edgesOf(v);
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
	public GraphType getType() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void setEdgeWeight(E arg0, double arg1) {
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

}
