package us.lsi.graphs.virtual;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface ActionVirtualVertex<V extends VirtualVertex<V,E>, E extends ActionSimpleEdge<V,A>, A> 
     extends VirtualVertex<V,E>{
	
	public List<A> actions();
	public V neighbor(A a);
	public E edge(A a);
	
	/**
	 * @param v Otro v&eacute;rtice
	 * @return La arista desde this a v2
	 */
	@Override
	public default E getEdgeToVertex(V v) {
		return this.edgesOf().stream()
				.filter(e->e.getSource().equals(v) || e.getTarget().equals(v))
				.findFirst()
				.get();
	}
	
	/**
	 * Este m&eacute;todo podr&iacute;a ser sobrescrito en la clase que refine al tipo
	 * @return El conjunto de los vecinos
	 */
	@Override
	public default Set<V> getNeighborListOf() {
		return actions().stream()
				.map(a->this.neighbor(a))
				.collect(Collectors.toSet());
		}
	/**
	 * Este m&eacute;todo podr&iacute;a ser sobrescrito en la clase que refine al tipo
	 * @return El conjunto de las aristas hacia los vecinos
	 */
	@Override
	public default Set<E> edgesOf() {
		return actions().stream()
				.map(a -> this.edge(a))
				.collect(Collectors.toSet());
	}
	/**
	 * Este m&eacute;todo podr&iacute;a ser sobrescrito en la clase que refine al tipo
	 * @return El conjunto de las aristas hacia los vecinos
	 */
	@Override
	public default List<E> edgesListOf() {
		return actions().stream()
			.map(a->this.edge(a))
			.collect(Collectors.toList());				
	}

}
