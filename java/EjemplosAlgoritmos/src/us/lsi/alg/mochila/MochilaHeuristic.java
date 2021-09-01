package us.lsi.alg.mochila;

import java.util.function.Predicate;

import us.lsi.mochila.datos.DatosMochila;

public class MochilaHeuristic {
	
	
	public static Double heuristic_negate(MochilaVertex v1, Predicate<MochilaVertex> goal, MochilaVertex v2) {
		return -heuristic(v1.index(), v1.capacidadRestante().doubleValue(),v2.index());
	}
	
	public static Double heuristic(MochilaVertex v1, Predicate<MochilaVertex> goal, MochilaVertex v2) {
		return heuristic(v1.index(), v1.capacidadRestante().doubleValue(), v2.index());
	}
	
	public static Double heuristic2_negate(MochilaVertex v1, Predicate<MochilaVertex> goal, MochilaVertex v2) {
		if(goal.test(v1)) return 0.;
		return -heuristic2(v1, goal, v2);
	}
	
	public static Double heuristic2(MochilaVertex v1, Predicate<MochilaVertex> goal, MochilaVertex v2) {
		Integer index = v1.index();
		Integer capacidadRestante = v1.capacidadRestante();
		Integer a = 1+ Math.min(capacidadRestante / DatosMochila.getPeso(index), DatosMochila.getNumMaxDeUnidades(index));
		Integer valor = a * DatosMochila.getValor(index);
		return valor.doubleValue();
	}

	public static Double heuristic(int index, Double capacidadRestante, Integer lastIndex) {
		Double r = 0.;		
		while (capacidadRestante> 0 && index < lastIndex) {
			Double a = Math.min(capacidadRestante / DatosMochila.getPeso(index), DatosMochila.getNumMaxDeUnidades(index));
			r = r + a * DatosMochila.getValor(index);
			capacidadRestante = capacidadRestante - a * DatosMochila.getPeso(index);
			index = index + 1;
		}
		return r;
	}	
	
	public static Double voraz(MochilaVertex v, MochilaVertex lastVertex) {
		Integer lastIndex = lastVertex.index();
		Double r = 0.;		
		while (v.capacidadRestante()> 0 && v.index() < lastIndex) {
			Integer a = v.greedyEdge().action();
			r = r + a * DatosMochila.getValor(v.index());
			v = v.neighbor(a);
		}
		return r;
	}

}
