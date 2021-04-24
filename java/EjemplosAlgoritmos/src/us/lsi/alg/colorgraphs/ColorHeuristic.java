package us.lsi.alg.colorgraphs;

import java.util.function.Predicate;


import us.lsi.common.Preconditions;


public class ColorHeuristic {
	
	public static Double heuristic(ColorVertex v1, Predicate<?> goal, ColorVertex v2) {
		return (double) v1.nc();
	}
	
	public static Integer gredyPath(ColorVertex v1, Predicate<ColorVertex> p) {
		ColorVertex v = v1;
		while(!p.test(v)) {
			Integer a = v.greedyAction();
			v = v.neighbor(a);
		};	 
		Preconditions.checkState(AuxiliaryColor.check(ColorVertex.graph,v.cav()),"Error en solucion");
		return v.nc();
	}
	

}
