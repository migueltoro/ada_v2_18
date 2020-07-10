package us.lsi.search.tsp;

import us.lsi.common.IntPair;
import us.lsi.graphs.virtual.ActionSimpleEdge;

public class TravelEdgeInteger extends ActionSimpleEdge<TravelVertexInteger,IntPair> {

	public static TravelEdgeInteger of(TravelVertexInteger c1, TravelVertexInteger c2, IntPair action) {
		return new TravelEdgeInteger(c1, c2, action);
	}

	TravelEdgeInteger(TravelVertexInteger c1, TravelVertexInteger c2, IntPair action) {
		super(c1, c2, action);
	}
	@Override
	public Double getEdgeWeight() {
		return super.target.getWeight()-super.source.getWeight();
	}

	@Override
	public String toString() {
		return String.format("(%s,%s)=%.2f", source,target,weight);
	}

}
