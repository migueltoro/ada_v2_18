package us.lsi.alg.secuencias;


import us.lsi.graphs.virtual.ActionSimpleEdge;

public record SeqEdge(SeqVertex source, SeqVertex target, SeqAction action, Double weight) 
          implements ActionSimpleEdge<SeqVertex,SeqAction>{

	public static SeqEdge of(SeqVertex c1, SeqVertex c2, SeqAction action) {
		Double w = action.weight(c1);
		return new SeqEdge(c1, c2, action, w);
	}

}
