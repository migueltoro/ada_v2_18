package us.lsi.alg.monedas;

import java.util.function.Predicate;

public class Heuristica {
	
	public static Double heuristic(MonedasVertex v1, Predicate<MonedasVertex> goal, MonedasVertex v2) {
		Integer index = v1.index;
		Double valorRestante = (double ) v1.valorRestante;
		Double w =0.;
		while(index < MonedasVertex.n) {
			Double a = valorRestante/Moneda.monedas.get(index).valor;
			w = w + a*Moneda.monedas.get(index).peso;
			valorRestante = valorRestante-a*Moneda.monedas.get(index).valor;
			index = index +1;			
		}
		return w;
	}
	
	public static Double caminoVoraz(MonedasVertex v1, Predicate<MonedasVertex> goal, MonedasVertex v2) {
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
