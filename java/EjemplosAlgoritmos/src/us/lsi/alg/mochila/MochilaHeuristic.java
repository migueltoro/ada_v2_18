package us.lsi.alg.mochila;

import java.util.function.Predicate;

import us.lsi.mochila.datos.DatosMochila;

public class MochilaHeuristic {
	
	
	public static Double heuristic_negate(MochilaVertex v1, Predicate<MochilaVertex> goal, MochilaVertex v2) {
		return -heuristic(v1.index, v1.capacidadRestante.doubleValue(), v2.index);
	}
	
	public static Double heuristic(MochilaVertex v1, Predicate<MochilaVertex> goal, MochilaVertex v2) {
		return heuristic(v1.index, v1.capacidadRestante.doubleValue(), v2.index);
	}

	public static Double heuristic(int index, Double capacidadRestante, int lastIndex) {
		Double r = 0.;		
		while (capacidadRestante> 0 && index < lastIndex) {
			Double a = Math.min(capacidadRestante / DatosMochila.getPeso(index), DatosMochila.getNumMaxDeUnidades(index));
			r = r + a * DatosMochila.getValor(index);
			capacidadRestante = capacidadRestante - a * DatosMochila.getPeso(index);
			index = index + 1;
		}
		return r;
	}	

}
