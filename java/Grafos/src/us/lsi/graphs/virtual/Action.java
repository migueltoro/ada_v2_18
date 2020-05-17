package us.lsi.graphs.virtual;

public interface Action<V> {

	/**
	 * @pre isApplicable(a)
	 * @param v Un vértice
	 * @return El vecino tras tomar esa acción
	 */
	V neighbor(V v);

	/**
	 * @param v Un vértice
	 * @return Si la acción es aplicable en este vértice
	 * @post El vértice retornada debe ser distinto a v y válido
	 */
	boolean isApplicable(V v);

	/**
	 * @param v Un vertice 
	 * @return El peso de la arista asociada a esta accion que parte de v 
	 */
	Double weight(V v);

}