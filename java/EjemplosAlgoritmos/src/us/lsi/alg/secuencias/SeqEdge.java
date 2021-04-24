package us.lsi.alg.secuencias;


import us.lsi.graphs.virtual.ActionSimpleEdge;

public class SeqEdge extends ActionSimpleEdge<SeqVertex,SeqAction>{

	public static SeqEdge of(SeqVertex c1, SeqVertex c2, SeqAction action) {
		Double w = action.weight(c1);
		return new SeqEdge(c1, c2, action, w);
	}

	private SeqEdge(SeqVertex c1, SeqVertex c2, SeqAction action, Double w) {
		super(c1, c2, action, w);
	}

}
