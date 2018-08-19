package us.lsi.graphs;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Miguel Toro
 *
 * @param <A> Tipo de la acción
 * @param <V> Tipo del vértice
 * 
 * 
 * <a> Tipo adecuado para modelar un vértice de un grafo virtual simple cuyas aristas están 
 * definidas por un conjunto finito de acciones. 
 * Cada acción válida identifica de forma única uno de los vécinos del vértice. 
 * Cada vértice conoce sus vecinos y la forma de llegar a ellos mediante una de las acciones válidas disponibles </a>
 */
public abstract class ActionVirtualVertex<V extends VirtualVertex<V,E>, E extends SimpleEdge<V>, A extends Action<V>> 
			implements VirtualVertex<V,E> {

	public ActionVirtualVertex() {
	}
	
	/**
	 * @return Si es un valor válido del tipo
	 */
	public abstract boolean isValid();
	
	/**
	 * Para ser implementado por el subtipo
	 * @return Lista de acciones disponibles
	 */
	protected abstract List<A> acciones();
	protected abstract V getThis();
	protected abstract E getEdge(V v, A a);
	
	private Set<V> neighbors = null;
	private Set<E> edges = null;
	

	@Override
	public Set<V> getNeighborListOf() {
		if (this.neighbors==null) {
			this.neighbors=acciones()
					.stream()
					.filter(x -> x.isApplicable(this.getThis()))
					.map(x -> x.neighbor(this.getThis()))
					.collect(Collectors.toSet());
		}
		return this.neighbors;
	}

	@Override
	public Set<E> edgesOf() {
		if (this.edges==null) {
			this.edges=
					acciones()
					.stream()
					.filter(x -> x.isApplicable(this.getThis()))
					.map(x->this.getEdge(x.neighbor(this.getThis()), x))
					.collect(Collectors.toSet());
		}
		return edges;
	}

	@Override
	public boolean isNeighbor(V e) {
		return this.getNeighborListOf().contains(e);
	}

	
}
