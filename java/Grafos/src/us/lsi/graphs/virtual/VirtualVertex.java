package us.lsi.graphs.virtual;

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
	 * @param v V&eacute;rtice que se pregunta si es vecino
	 * @return Si el v&eacute;rtice es vecino
	 */	
	Boolean isNeighbor(V v);	

	/**
	 * 
	 * @param v2 Otro v&eacute;rtice
	 * @return La arista desde this a v2
	 */	
	E getEdge(V v2);

}
