package us.lsi.graphs.hypergraphs;

import java.util.List;
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
public abstract class VirtualHyperVertex<V extends VirtualHyperVertex<V,E,A>,E extends SimpleHyperEdge<V,A>, A> {

	public VirtualHyperVertex() {
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
	
	public abstract V getThis();
	
	public abstract Boolean isBaseCase();
	
	public abstract Double baseCaseSolution();
	
	/**
	 * @param a Una acci&oacute;n
	 * @return El vecino del v&eacute;rtice siguiendo esa acci&oacute;n
	 * @pre La acci&oacute;n a debe ser aplicable
	 * @post El v&eacute;rtice retornada debe ser distinto al original y v&aacute;lido
	 */
	public abstract List<V> neighbors(A a);
	
	/**
	 * Este m&eacute;todo debe ser sobrescrito en la clase que refine el tipo
	 * @param a Acci&oacute;n
	 * @return La arista que lleva al vecino siguiendo esta acci&oacute;n
	 */
	@SuppressWarnings("unchecked")
	public E getEdgeFromAction(A a) {
		List<V> v = this.neighbors(a);
		return (E) SimpleHyperEdge.ofAction(this.getThis(),v,a);
	}
	
	private List<List<V>> neighbors = null;
	private List<E> edges = null;
	
	/**
	 * Este m&eacute;todo podr&iacute;a ser sobrescrito en la clase que refine al tipo
	 * @param v Otro v&eacute;rtice
	 * @return La arista desde this a v2
	 */
//	@Override
//	public E getEdge(V v) {
//		E edge = null;
//		if (this.isNeighbor(v)) {
//			edge = this.edgesOf()
//					.stream()
//					.filter(e->e.getSource().equals(v) || e.getTarget().equals(v))
//					.findFirst()
//					.get();
//		}
//		return edge;
//	}
//	
	/**
	 * Este m&eacute;todo podr&iacute;a ser sobrescrito en la clase que refine al tipo
	 * @return El conjunto de los vecinos
	 */
	public List<List<V>> getNeighborListOf() {
		if (this.neighbors==null) {
			this.neighbors=actions()
					.stream()
					.map(a->this.neighbors(a))
					.collect(Collectors.toList());
		}
		return this.neighbors;
	}
	/**
	 * Este m&eacute;todo podr&iacute;a ser sobrescrito en la clase que refine al tipo
	 * @return El conjunto de las aristas hacia los vecinos
	 */
	public List<E> edgesOf() {
		if (this.edges==null) {
			this.edges= actions()
					.stream()
					.map(a->this.getEdgeFromAction(a))
					.collect(Collectors.toList());				
		}
		return edges;
	}
	/**
	 * Este m&eacute;todo podr&iacute;a ser sobrescrito en la clase que refine al tipo
	 * @param v Otro v&eacute;rtice
	 * @return Si v es vecino
	 */
//	@Override
//	public Boolean isNeighbor(V v) {
//		return this.getNeighborListOf().contains(v);
//	}
	
}
