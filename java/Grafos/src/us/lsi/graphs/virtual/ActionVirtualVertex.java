package us.lsi.graphs.virtual;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author Miguel Toro
 *
 * @param <A> Tipo de la acci&oacute;n
 * @param <V> Tipo del v&eacute;rtice
 * 
 * 
 * <a> Tipo adecuado para modelar un v&eacute;rtice de un grafo virtual simple cuyas aristas est&aacute;n 
 * definidas por un conjunto de acciones o alternativas. 
 * Cada acci&oacute;n v&aacute;lida identifica de forma única uno de los vecinos del v&eacute;rtice. 
 * Cada v&eacute;rtice conoce sus vecinos y la forma de llegar a ellos mediante una de las acciones v&aacute;lidas disponibles </a>
 */
public abstract class ActionVirtualVertex<V extends VirtualVertex<V,E>, E extends ActionSimpleEdge<V,A>, A> implements VirtualVertex<V,E> {

	public ActionVirtualVertex() {
	}
	
	/**
	 * @return Si es un valor v&aacute;lido del tipo
	 */
	public abstract Boolean isValid();
	
	/**
	 * Para ser implementado por el subtipo
	 * @return Lista de acciones disponibles y adecuadas para alcanzar un v&eacute;rtice v&aacute;lido
	 */
	public abstract List<A> actions();
	
	/**
	 * @param a Una acci&oacute;n
	 * @return El vecino del v&eacute;rtice siguiendo esa acci&oacute;n
	 * @pre La acci&oacute;n a debe ser aplicable
	 * @post El v&eacute;rtice retornada debe ser distinto al original y v&aacute;lido
	 */
	public abstract V neighbor(A a);
	
	/**
	 * @param a Acci&oacute;n
	 * @return La arista que lleva al vecino siguiendo esta acci&oacute;n
	 */
	
	public abstract E edge(A a); 
	
	private Set<V> neighbors = null;
	private Set<E> edges = null;
	private List<E> edgesList = null;
	
	/**
	 * @param v Otro v&eacute;rtice
	 * @return La arista desde this a v2
	 */
	@Override
	public E getEdge(V v) {
		E edge = null;
		if (this.isNeighbor(v)) {
			edge = this.edgesOf()
					.stream()
					.filter(e->e.getSource().equals(v) || e.getTarget().equals(v))
					.findFirst()
					.get();
		}
		return edge;
	}
	
	/**
	 * Este m&eacute;todo podr&iacute;a ser sobrescrito en la clase que refine al tipo
	 * @return El conjunto de los vecinos
	 */
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
	/**
	 * Este m&eacute;todo podr&iacute;a ser sobrescrito en la clase que refine al tipo
	 * @return El conjunto de las aristas hacia los vecinos
	 */
	@Override
	public Set<E> edgesOf() {
		if (this.edges==null) {
			this.edges= actions()
					.stream()
					.map(a->this.edge(a))
					.collect(Collectors.toSet());				
		}
		return edges;
	}
	/**
	 * Este m&eacute;todo podr&iacute;a ser sobrescrito en la clase que refine al tipo
	 * @return El conjunto de las aristas hacia los vecinos
	 */
	@Override
	public List<E> edgesListOf() {
		if (this.edgesList==null) {
			this.edgesList= actions()
					.stream()
					.map(a->this.edge(a))
					.collect(Collectors.toList());				
		}
		return edgesList;
	}
	/**
	 * Este m&eacute;todo podr&iacute;a ser sobrescrito en la clase que refine al tipo
	 * @param v Otro v&eacute;rtice
	 * @return Si v es vecino
	 */
	@Override
	public Boolean isNeighbor(V v) {
		return this.getNeighborListOf().contains(v);
	}
	
}
