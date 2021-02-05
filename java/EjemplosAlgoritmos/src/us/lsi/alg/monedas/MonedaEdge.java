package us.lsi.alg.monedas;


import us.lsi.graphs.virtual.ActionSimpleEdge;

public class MonedaEdge extends ActionSimpleEdge<MonedaVertex,Integer> {

	public static MonedaEdge of(MonedaVertex c1, MonedaVertex c2, Integer action) {
		return new MonedaEdge(c1, c2, action);
	}

	private MonedaEdge(MonedaVertex c1, MonedaVertex c2, Integer action) {
		super(c1, c2, action);
		super.weight = (double) (action*Moneda.peso(this.source.index));
	}
	
	@Override
	public String toString() {
		return String.format("(%s,%s)",super.source,super.action.toString());
	}

}
