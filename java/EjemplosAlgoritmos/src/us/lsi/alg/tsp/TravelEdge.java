package us.lsi.alg.tsp;

import us.lsi.common.IntPair;
import us.lsi.graphs.virtual.ActionSimpleEdge;


public class TravelEdge extends ActionSimpleEdge<TravelVertex,IntPair> {

	public static TravelEdge of(TravelVertex c1, TravelVertex c2, IntPair action) {
		Double w = c2.weight-c1.weight;
		return new TravelEdge(c1, c2, action, w);
	}

	TravelEdge(TravelVertex c1, TravelVertex c2, IntPair action, Double w) {
		super(c1, c2, action, w);
	}

}
