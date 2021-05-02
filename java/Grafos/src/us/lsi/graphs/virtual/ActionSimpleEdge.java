package us.lsi.graphs.virtual;

import us.lsi.graphs.SimpleEdge;

public interface ActionSimpleEdge<V, A> extends SimpleEdge<V> {
	
	public static <V, A> ActionSimpleEdge<V, A> of(V c1, V c2, A action, Double weight) {
		return new ActionSimpleEdgeR<V, A>(c1, c2, action, weight);
	}
	
	A action();
	
	public static record ActionSimpleEdgeR<V,A>(V source, V target, A action, Double weight) 
	             implements ActionSimpleEdge<V, A> {}

}
