package us.lsi.graphs;

public interface Action<V> {
	/**
	 * @pre isApplicable(a)
	 * @param v Un vértice
	 * @return El vecino tras tomar esa acción
	 */
	public abstract V neighbor(V v);
	/**
	 * @param v Un vértice
	 * @return Si la acción es aplicable en este vértice
	 */
	public abstract boolean isApplicable(V v);
}
