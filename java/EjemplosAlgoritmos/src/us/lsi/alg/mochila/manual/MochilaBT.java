package us.lsi.alg.mochila.manual;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.alg.mochila.MochilaEdge;
import us.lsi.alg.mochila.MochilaHeuristic;
import us.lsi.alg.mochila.MochilaVertex;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;

public class MochilaBT {
	
	public static EstadoMochila estado;
	public static Integer maxValue;
	public static Set<SolucionMochila> soluciones = new HashSet<>();
	
	public static Double cota(Integer a) {
		MochilaVertex v = estado.vertex;
		MochilaVertex vn = v.neighbor(a);
		return estado.valorAcumulado+
				MochilaEdge.of(v,vn, a).weight.intValue()+
				MochilaHeuristic.heuristic(vn,MochilaVertex.goal(),MochilaVertex.lastVertex());
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
	
	public static void btm(Integer capacidadInicial, Integer maxValue, SolucionMochila s) {
		estado = EstadoMochila.initial(capacidadInicial);
		soluciones = new HashSet<>();
		MochilaBT.maxValue = maxValue;
		soluciones.add(s);
		btm();
	}
	
	public static void btm() {
		if(estado.vertex.index == MochilaVertex.n) {
			Integer value = estado.valorAcumulado;
			if(maxValue==null || value > maxValue) {
				maxValue = value;
				soluciones.add(estado.solucion());
			}
		} else {
			List<Integer> alternativas = estado.vertex.actions();
			for(Integer a:alternativas) {	
				Double cota = cota(a);
				if(cota <= maxValue) continue;
				estado.forward(a);
				btm();  
				estado.back(a);
			}
		}
	}
	
	
}
