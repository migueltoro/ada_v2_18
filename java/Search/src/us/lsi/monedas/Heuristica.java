package us.lsi.monedas;

public class Heuristica {
	
	public static Double heuristic(MonedasVertex v1, MonedasVertex v2) {
		Integer index = v1.index;
		Double valorRestante = (double ) v1.valorRestante;
		Double w =0.;
		while(index < MonedasVertex.n) {
			Double a = valorRestante/Moneda.monedas.get(index).valor;
			w = w + a*Moneda.monedas.get(index).peso;
			index = index +1;
			valorRestante = valorRestante-a*Moneda.monedas.get(index).valor;
		}
		return w;
	}
	
	public static Double caminoVoraz(MonedasVertex v1, MonedasVertex v2) {
		MonedasVertex v = v1;
		Double w =0.;
		while(!v.equals(v2)) {
			MonedasEdge e = v.edge(v.accionVoraz());
			w = w + e.getEdgeWeight();
			v = e.getTarget();
		}
		return w;
	}

}
