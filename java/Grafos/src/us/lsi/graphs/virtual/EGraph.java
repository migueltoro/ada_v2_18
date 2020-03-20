package us.lsi.graphs.virtual;

import java.util.List;

import org.jgrapht.Graph;

public interface EGraph<V, E> extends Graph<V,E> {
	List<E> edgesListOf(V v);
}
