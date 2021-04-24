package us.lsi.graphs.virtual;

import java.util.List;
import java.util.Set;

/**
 * <a> El tipo representa un v&eacute;rtice de un grafo virtual no dirigido </a>
 * 
 * @author Miguel Toro
 *
 * @param <V> Tipo de los v&eacute;rtices
 * @param <E> Tipo de las aristas
 */
public interface VirtualVertex<V extends VirtualVertex<V,E>, E> {	
	/**
	 * @return Si es un valor v&aacute;lido del tipo
	 */
	Boolean isValid();
	/**
	 * @return Conjunto de los v&eacute;rtices vecinos
	 */
	Set<V> getNeighborListOf();
	/**
	 * @return Conjunto de las aristas hacia los v&eacute;rtices vecinos
	 */
	Set<E> edgesOf(); 
	/**
	 * @return Lista de las aristas hacia los v&eacute;rtices vecinos
	 */
	List<E> edgesListOf();
	
	/**
	 * @param v V&eacute;rtice que se pregunta si es vecino
	 * @return Si el v&eacute;rtice es vecino
	 */		
	
	public default Boolean isNeighbor(V v) {
		return this.getNeighborListOf().contains(v);
	}

	/**
	 * 
	 * @param v2 Otro v&eacute;rtice
	 * @return La arista desde this a v2
	 */	
	E getEdgeToVertex(V v2);
	
	
}
