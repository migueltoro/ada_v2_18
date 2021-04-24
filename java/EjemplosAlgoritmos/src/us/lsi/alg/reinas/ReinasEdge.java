package us.lsi.alg.reinas;

import us.lsi.graphs.virtual.ActionSimpleEdge;

public class ReinasEdge extends ActionSimpleEdge<ReinasVertex,Integer> {

	public static ReinasEdge of(ReinasVertex c1, ReinasVertex c2, Integer action) {
		return new ReinasEdge(c1, c2, action);
	}

	private ReinasEdge(ReinasVertex c1, ReinasVertex c2, Integer action) {
		super(c1, c2, action, 1.);
	}
}
