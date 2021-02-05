package us.lsi.alg.secuencias;


import us.lsi.graphs.virtual.ActionSimpleEdge;

public class SeqEdge extends ActionSimpleEdge<SeqVertex,SeqAction>{

	public static SeqEdge of(SeqVertex c1, SeqVertex c2, SeqAction action) {
		return new SeqEdge(c1, c2, action);
	}

	private SeqEdge(SeqVertex c1, SeqVertex c2, SeqAction action) {
		super(c1, c2, action);
	}

	@Override
	public Double getEdgeWeight() {
		return action.weight(super.source);
	}

	@Override
	public String toString() {
		return String.format("(%s,%d,%s)", this.action,super.getSource().index,super.getSource().s);
	}

	
}
