package us.lsi.alg.tsp;

import us.lsi.common.IntPair;
import us.lsi.graphs.virtual.ActionSimpleEdge;

public class TravelEdgeInteger extends ActionSimpleEdge<TravelVertexInteger,IntPair> {

	public static TravelEdgeInteger of(TravelVertexInteger c1, TravelVertexInteger c2, IntPair action) {
		Double w = c2.getWeight()-c1.getWeight();
		return new TravelEdgeInteger(c1, c2, action, w);
	}

	TravelEdgeInteger(TravelVertexInteger c1, TravelVertexInteger c2, IntPair action, Double w) {
		super(c1, c2, action, w);
	}

}
