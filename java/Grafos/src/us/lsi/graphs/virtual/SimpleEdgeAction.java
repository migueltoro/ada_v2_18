package us.lsi.graphs.virtual;

import us.lsi.graphs.SimpleEdge;

public class SimpleEdgeAction<V, A> extends SimpleEdge<V> {
	
	public static <V, A> SimpleEdgeAction<V, A> of(V c1, V c2, A action) {
		return new SimpleEdgeAction<V, A>(c1, c2, action);
	}

	public static <V, A> SimpleEdgeAction<V, A> ofVertex(V c1, V c2) {
		return new SimpleEdgeAction<V, A>(c1, c2);
	}

	public static <V, A> SimpleEdgeAction<V, A> ofWeight(V c1, V c2, double weight) {
		return new SimpleEdgeAction<V, A>(c1, c2, weight);
	}

	public static <V, A> SimpleEdgeAction<V, A> empty() {
		return new SimpleEdgeAction<V, A>();
	}

	public static <V, A> SimpleEdgeAction<V, A> of(V c1, V c2, Double w, A action) {
		return new SimpleEdgeAction<V, A>(c1, c2, w, action);
	}

	public A action = null;

	private SimpleEdgeAction() {
		super();
	}

	protected SimpleEdgeAction(V c1, V c2, double weight) {
		super(c1, c2, weight);
	}

	protected SimpleEdgeAction(V c1, V c2) {
		super(c1, c2);
	}

	protected SimpleEdgeAction(V c1, V c2, A action) {
		super(c1, c2);
		this.action = action;
	}
	
	private SimpleEdgeAction(V c1, V c2, Double w, A action) {
		super(c1, c2, w);
		this.action = action;
	}

}
