package us.lsi.alg.monedas;


import org.jgrapht.GraphPath;

import us.lsi.common.Multiset;
public class SolucionMonedas {

	public final Multiset<Moneda> monedas;
	public final Integer peso;
	public final Integer valor;
	
	public static SolucionMonedas of(GraphPath<MonedaVertex,MonedaEdge> path) {	
		return new SolucionMonedas(path);
	}

	public SolucionMonedas(GraphPath<MonedaVertex, MonedaEdge> path) {
		super();
		Multiset<Moneda> monedas = Multiset.empty();
		Integer peso = 0;
		Integer valor = 0;
		for (MonedaEdge e : path.getEdgeList()) {
			Integer i = e.source.index;
			Moneda m = Moneda.get(i);
			Integer p = Moneda.peso(i);
			Integer v = Moneda.valor(i);
			monedas.add(m, e.action);
			peso = peso + e.action * p;
			valor = valor + e.action * v;
		}
		this.monedas = Multiset.copy(monedas);
		this.peso = peso;
		this.valor = valor;
	}

	@Override
	public String toString() {
		return String.format("valor = %d, peso = %d\n%s",
				this.valor,
				this.peso,
				this.monedas);
	}

}
