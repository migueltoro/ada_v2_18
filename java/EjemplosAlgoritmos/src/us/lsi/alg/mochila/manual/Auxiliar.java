package us.lsi.alg.mochila.manual;

import java.util.List;
import java.util.function.Predicate;

import us.lsi.alg.mochila.MochilaEdge;
import us.lsi.alg.mochila.MochilaHeuristic;
import us.lsi.alg.mochila.MochilaVertex;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;

public class Auxiliar {
	
	public static Double heuristic_2(MochilaVertex v1, Predicate<MochilaVertex> goal, MochilaVertex v2) {
		return heuristic_2(v1.index, v1.capacidadRestante, v2.index);
	}

	public static Double heuristic_2(int index, Integer capacidadRestante, int lastIndex) {
		Double r = 0.;
		
		while (capacidadRestante> 0 && index < lastIndex) {
			Integer a = 1+Math.min(capacidadRestante / DatosMochila.getPeso(index), DatosMochila.getNumMaxDeUnidades(index));
			r = r + a * DatosMochila.getValor(index);
			capacidadRestante = Math.max(0,capacidadRestante - a * DatosMochila.getPeso(index));
			index = index + 1;
		}
		return r;
	}	


	public static Double cota(MochilaVertex v,Integer a) {
		MochilaVertex vn = v.neighbor(a);
		return MochilaEdge.of(v,vn, a).weight.intValue()+
				MochilaHeuristic.heuristic(vn,MochilaVertex.goal(),MochilaVertex.lastVertex());
	}
	
	public static Double cota_2(MochilaVertex v,Integer a) {
		MochilaVertex vn = v.neighbor(a);
		return MochilaEdge.of(v,vn, a).weight.intValue()+
				Auxiliar.heuristic_2(vn,MochilaVertex.goal(),MochilaVertex.lastVertex());
	}

	public static Integer voraz(MochilaVertex v1) {
		Integer value = 0;
		while(!MochilaVertex.goal().test(v1)) {
			MochilaVertex old = v1;
			Integer a = v1.greedyAction();
			v1 = v1.neighbor(a);
			value += MochilaEdge.of(old, v1, a).weight.intValue();
		}
		return value;
	}

	public static SolucionMochila solucionVoraz(MochilaVertex v1) {
		SolucionMochila s = SolucionMochila.empty();
		while(!MochilaVertex.goal().test(v1)) {
			MochilaVertex old = v1;
			Integer a = v1.greedyAction();
			v1 = v1.neighbor(a);
			s.add(DatosMochila.getObjeto(old.index), a);
		}
		return s;
	}
		
	public static SolucionMochila solucionFromEdges(List<MochilaEdge> edges) {
		SolucionMochila s = SolucionMochila.empty();
		for(MochilaEdge edge:edges) {
			s.add(DatosMochila.getObjeto(edge.source.index),edge.a);
		}
		return s;
	}
	
	public static Integer pesoArista(MochilaVertex v, Integer a) {
		return MochilaEdge.of(v, v.neighbor(a), a).weight.intValue();
	}
	
}
