package us.lsi.iterativorecursivos;

import java.util.List;

public class Ejemplos {
	
	public static Double polinomioEvalLeft(List<Double> ls, Double v) {
		Double b = 0.;
		Integer i = 0;
		Double r = 1.;
		Integer n = ls.size();
		while (n - i > 0) {
			Double e = ls.get(i);
			b = b +  e* r;
			i = i + 1;
			r = r * v;
		}
		return b;
	}
	
	public static Double polinomioEvalRight(List<Double> ls, Double v) {
		Double b = polinomioEvalRightP(ls,v,0);
		return b;
	}

	public static Double polinomioEvalRightP(List<Double> ls, Double v, Integer i) {
		Double r = 0.;
		Integer n = ls.size();
		if (n - i > 0) {
			Double e = ls.get(i);
			Double b = polinomioEvalRightP(ls, v, i + 1);
			r = b * v + e;
		} 
		return r;
	}
	

}
