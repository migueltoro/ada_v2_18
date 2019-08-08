package us.lsi.graphs.virtual;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.graphs.SimpleEdge;

/**
 * @author Miguel Toro
 *
 * @param <A> Tipo de la acción
 * @param <V> Tipo del vértice
 * 
 * 
 * <a> Tipo adecuado para modelar un vértice de un grafo virtual simple cuyas aristas están 
 * definidas por un conjunto de acciones o alternativas. 
 * Cada acción válida identifica de forma única uno de los vecinos del vértice. 
 * Cada vértice conoce sus vecinos y la forma de llegar a ellos mediante una de las acciones válidas disponibles </a>
 */
public abstract class ActionVirtualVertex<V extends VirtualVertex<V,E>, E extends SimpleEdge<V>, A> 
			implements VirtualVertex<V,E> {

	public ActionVirtualVertex() {
	}
	
	/**
	 * @return Si es un valor válido del tipo
	 */
	public abstract boolean isValid();
	
	/**
	 * Para ser implementado por el subtipo
	 * @return Lista de acciones disponibles y adecuadas para alcanzar un vértice válido
	 */
	protected abstract List<A> actions();
	
	protected abstract V getThis();
	
	protected abstract V neighbor(A a);
	
	/**
	 * @param a Acci&oacute;n
	 * @return La arista que lleva al vecino siguiendo esta acci&oacute;n
	 */
	protected abstract E getEdge(A a);
	
	private Set<V> neighbors = null;
	private Set<E> edges = null;
	

	@Override
	public Set<V> getNeighborListOf() {
		if (this.neighbors==null) {
			this.neighbors=actions()
					.stream()
					.map(a->this.neighbor(a))
					.collect(Collectors.toSet());
		}
		return this.neighbors;
	}

	@Override
	public Set<E> edgesOf() {
		if (this.edges==null) {
			this.edges= actions()
					.stream()
					.map(a->this.getEdge(a))
					.collect(Collectors.toSet());				
		}
		return edges;
	}

	@Override
	public boolean isNeighbor(V e) {
		return this.getNeighborListOf().contains(e);
	}

	
}
