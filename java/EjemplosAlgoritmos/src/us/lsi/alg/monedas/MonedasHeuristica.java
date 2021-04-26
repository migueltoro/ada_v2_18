package us.lsi.alg.monedas;


import java.util.function.Predicate;




public class MonedasHeuristica {

	
	public static Double heuristic_negate(MonedaVertex v1, Predicate<MonedaVertex> goal, MonedaVertex v2) {
		return -heuristic(v1.index(), v1.valorRestante().doubleValue(),v2.index());
	}
	
	public static Double heuristic(MonedaVertex v1, Predicate<MonedaVertex> goal, MonedaVertex v2) {
		return heuristic(v1.index(), v1.valorRestante().doubleValue(), v2.index());
	}


	public static Double heuristic(int index, Double valorRestante, Integer lastIndex) {
		Double r = 0.;	
		if(index == MonedaVertex.n-1) return 0.;
		while (valorRestante> 0 && index < lastIndex) {
			Double a = valorRestante / Moneda.valor(index);
			r = r + a * Moneda.valor(index);
			valorRestante = valorRestante - a * Moneda.valor(index);
			index = index + 1;
		}
		return r;
	}	

}
