package us.lsi.alg.monedas;


import java.util.function.Predicate;




public class MonedasHeuristica {

	record Sv(Integer valor, Integer peso) {}
	record Sh(Double valor, Double peso) {}
	
	public static Double heuristic_negate(MonedaVertex v1, Predicate<MonedaVertex> goal, MonedaVertex v2) {
		return -heuristic_left(v1.index(), v1.valorRestante().doubleValue(),v2.index()).peso();
	}
	
	public static Double heuristic(MonedaVertex v1, Predicate<MonedaVertex> goal, MonedaVertex v2) {
		return heuristic_left(v1.index(), v1.valorRestante().doubleValue(), v2.index()).peso();
	}
	
	public static Integer voraz(MonedaVertex v1, Predicate<MonedaVertex> goal, MonedaVertex v2) {
		return voraz_left(v1.index(), v1.valorRestante(),v2.index()).peso();
	}
	
	public static Sh heuristic_left(Integer index, Double valorRestante, Integer lastIndex) {
		Double r = 0.;
		Double p = 0.;
//		if(index == MonedaVertex.n-1) return 0.;
		while (valorRestante> 0 && index < lastIndex) {
			Double a = valorRestante / Moneda.valor(index);
			r = r + a * Moneda.valor(index);
			p = p + a * Moneda.peso(index);
			valorRestante = valorRestante - a * Moneda.valor(index);
			index = index + 1;
		}
		return new Sh(r,p);
	}
	
	public static Sv voraz_left(Integer index, Integer valorRestante, Integer lastIndex) {
		Integer r = 0;
		Integer p = 0;
//		if(index == MonedaVertex.n) return 0;
		while (valorRestante > 0 && index < lastIndex) {
			Integer a = valorRestante / Moneda.valor(index);
			r = r + a * Moneda.valor(index);
			p = p + a * Moneda.peso(index);
			valorRestante = valorRestante - a * Moneda.valor(index);
			index = index + 1;
		}
		return new Sv(r,p);
	}
	
	public static Sh heuristic_right(Integer index, Double valorRestante, Integer lastIndex) {
		Double r = 0.;
		Double p = 0.;
//		if(index == MonedaVertex.n) return 0.;
		Integer i = lastIndex-1;
		while (valorRestante> 0 && i >= index) {
			Double a = valorRestante / Moneda.valor(i);
			r = r + a * Moneda.valor(i);
			p = p + a * Moneda.peso(i);
			valorRestante = valorRestante - a * Moneda.valor(i);
			i = i - 1;
		}
		return new Sh(r,p);
	}
	
	public static Sv voraz_right(Integer index, Integer valorRestante, Integer lastIndex) {
		Integer r = 0;	
		Integer p = 0;
//		if(index == MonedaVertex.n) return 0;
		Integer i = lastIndex-1;
		while (valorRestante> 0 && i >= index) {
			Integer a = valorRestante / Moneda.valor(i);
			r = r + a * Moneda.valor(i);
			p = p + a * Moneda.peso(i);
			valorRestante = valorRestante - a * Moneda.valor(i);
			i = i - 1;
		}
		return new Sv(r,p);
	}

}
