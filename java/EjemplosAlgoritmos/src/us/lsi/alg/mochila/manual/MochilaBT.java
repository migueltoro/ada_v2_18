package us.lsi.alg.mochila.manual;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.alg.mochila.MochilaVertex;
import us.lsi.mochila.datos.SolucionMochila;

public class MochilaBT {
	
	public static EstadoMochila estado;
	public static Integer maxValue;
	public static Set<SolucionMochila> soluciones = new HashSet<>();
	
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
				Double cota = MochilaBT.estado.valorAcumulado+Auxiliar.cota_2(estado.vertex,a);
				if(cota <= maxValue) continue;
				estado.forward(a);
				btm();  
				estado.back(a);
			}
		}
	}
	
	
}
