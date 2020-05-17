package us.lsi.graphs.virtual;

import us.lsi.graphs.SimpleEdge;

public class ActionSimpleEdge<V, A> extends SimpleEdge<V> {
	
	public static <V, A> ActionSimpleEdge<V, A> of(V c1, V c2, A action) {
		return new ActionSimpleEdge<V, A>(c1, c2, action);
	}

	public static <V, A> ActionSimpleEdge<V, A> ofVertex(V c1, V c2) {
		return new ActionSimpleEdge<V, A>(c1, c2);
	}

	public static <V, A> ActionSimpleEdge<V, A> empty() {
		return new ActionSimpleEdge<V, A>();
	}

	public static <V, A> ActionSimpleEdge<V, A> of(V c1, V c2, Double w, A action) {
		return new ActionSimpleEdge<V, A>(c1, c2, w, action);
	}

	public A action = null;

	private ActionSimpleEdge() {
		super();
	}

	protected ActionSimpleEdge(V c1, V c2) {
		super(c1, c2);
	}

	protected ActionSimpleEdge(V c1, V c2, A action) {
		super(c1, c2);
		this.action = action;
	}
	
	private ActionSimpleEdge(V c1, V c2, Double w, A action) {
		super(c1, c2, w);
		this.action = action;
	}

	@Override
	public String toString() {
		return String.format("(%s,%.2f,%s,%s)",action,weight,source,target);
	}
	
	

}
