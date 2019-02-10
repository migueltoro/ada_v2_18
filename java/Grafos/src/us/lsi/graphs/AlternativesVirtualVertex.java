package us.lsi.graphs;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AlternativesVirtualVertex<V extends VirtualVertex<V,E>, E extends SimpleEdge<V>, A> implements VirtualVertex<V, E> {

	public abstract List<A> alternatives();
	protected abstract V getThis();
	protected abstract V neighbor(A a);
	public abstract boolean isValid(); 
	protected abstract E getEdge(V v, A a);
	
	private Set<V> neighbors = null;
	private Set<E> edges = null;

	@Override
	public Set<V> getNeighborListOf() {
		if (this.neighbors==null) {
			this.neighbors=alternatives()
					.stream()
					.map(x -> neighbor(x))
					.collect(Collectors.toSet());
		}
		return this.neighbors;
	}

	@Override
	public Set<E> edgesOf() {
		if (this.edges==null) {
			this.edges=alternatives()
					.stream()
					.map(x->getEdge(neighbor(x), x))
					.collect(Collectors.toSet());
		}
		return edges;
	}

}
