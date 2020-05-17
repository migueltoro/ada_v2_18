package us.lsi.search.tsp;

import us.lsi.common.IntPair;
import us.lsi.graphs.virtual.ActionSimpleEdge;


public class TravelEdge extends ActionSimpleEdge<TravelVertex,IntPair> {

	public static TravelEdge of(TravelVertex c1, TravelVertex c2, IntPair action) {
		return new TravelEdge(c1, c2, action);
	}

	TravelEdge(TravelVertex c1, TravelVertex c2, IntPair action) {
		super(c1, c2, action);
	}
	@Override
	public double getEdgeWeight() {
		return super.target.weight-super.source.weight;
	}

}
