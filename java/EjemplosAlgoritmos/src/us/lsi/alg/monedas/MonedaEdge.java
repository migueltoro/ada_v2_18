package us.lsi.alg.monedas;


import us.lsi.graphs.virtual.ActionSimpleEdge;

public record MonedaEdge(MonedaVertex source, MonedaVertex target, Integer action, Double weight) 
           implements ActionSimpleEdge<MonedaVertex,Integer> {

	public static MonedaEdge of(MonedaVertex c1, MonedaVertex c2, Integer action) {
		Double w = (double) (action*Moneda.peso(c1.index()));
		return new MonedaEdge(c1, c2, action, w);
	}

}
