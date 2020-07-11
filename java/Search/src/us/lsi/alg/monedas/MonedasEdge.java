package us.lsi.alg.monedas;


import us.lsi.graphs.virtual.ActionSimpleEdge;

public class MonedasEdge extends ActionSimpleEdge<MonedasVertex,Integer> {

	public static MonedasEdge of(MonedasVertex c1, MonedasVertex c2, Integer action) {
		return new MonedasEdge(c1, c2, action);
	}

	private MonedasEdge(MonedasVertex c1, MonedasVertex c2, Integer action) {
		super(c1, c2, action);
	}
	
	@Override
	public Double getEdgeWeight() {
		return (double) (action*Moneda.monedas.get(super.source.index).peso);
	}
	
	@Override
	public String toString() {
		return String.format("(%s,%d)",super.source,super.action);
	}

}
