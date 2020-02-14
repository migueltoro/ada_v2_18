package us.lsi.graphs.virtual;

import java.util.Set;
/**
 * <a> El tipo representa un vértice de un grafo virtual no dirigido </a>
 * 
 * @author Miguel Toro
 *
 * @param <V> Tipo de los vértices
 * @param <E> Tipo de las aristas
 */
public interface VirtualVertex<V extends VirtualVertex<V,E>, E> {	
	/**
	 * @return Si es un valor válido del tipo
	 */
	boolean isValid();
	/**
	 * @return Conjunto de los vértices vecinos
	 */
	Set<V> getNeighborListOf();
	/**
	 * @return Conjunto de las aristas hacia los vértices vecinos
	 */
	Set<E> edgesOf(); 
	
	/**
	 * @param v Vértice que se pregunta si es vecino
	 * @return Si el vértice es vecino
	 */
	default Boolean isNeighbor(V v) {
		return null;
	}
	
	/**
	 * @param v2 Otro vértice
	 * @return La arista desde this a v2
	 */
	default E getEdge(V v2) {
		return null;
	}

}
