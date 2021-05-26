package us.lsi.alg.reinas.manual;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import us.lsi.common.List2;

public class ReinasBTRandom2 {
	
	public static class StateReinas {
		ReinasProblem vertice;
		Map<ReinasProblem,ReinasProblem> verticeAnterior;
		
		public StateReinas(ReinasProblem vertice, Map<ReinasProblem, ReinasProblem> verticeAnterior) {
			super();
			this.vertice = vertice;
			this.verticeAnterior = verticeAnterior;
		}

		void forward(Integer a) {
			ReinasProblem nv = vertice.vecino(a);
			this.verticeAnterior.put(nv,this.vertice);
			this.vertice = nv;
		}
		
		void back(Integer a) {
			ReinasProblem old = this.vertice;
			this.vertice = this.verticeAnterior.get(old);
			this.verticeAnterior.remove(old);		
		}
		
		SolucionReinas solucion() {
			return SolucionReinas.of(this.vertice);
		}
		
	}
	
	public static ReinasProblem start;
	public static StateReinas estado;
	public static Set<SolucionReinas> soluciones;
	public static Integer threshold;
	public static Integer iteraciones;
	
	public static void btm(Integer nr) {
		ReinasProblem.n = nr;
		ReinasBTRandom2.iteraciones = 0;
		do {
			ReinasBTRandom2.start = ReinasProblem.first();
			Map<ReinasProblem,ReinasProblem> va = new HashMap<>();
			va.put(ReinasBTRandom2.start,null);
			ReinasBTRandom2.estado = new StateReinas(ReinasBTRandom2.start,va);
			ReinasBTRandom2.soluciones = new HashSet<>();
			ReinasBTRandom2.btm();
			ReinasBTRandom2.iteraciones++;
		}while(ReinasBTRandom2.soluciones.size()==0);
	}
	
	public static void btm() {
		if(ReinasBTRandom2.estado.vertice.index() == ReinasProblem.n) {
			SolucionReinas s = ReinasBTRandom2.estado.solucion();
			if(s != null) ReinasBTRandom2.soluciones.add(s);
		} else {
			List<Integer> alternativas = ReinasBTRandom2.estado.vertice.acciones();
			if(ReinasBTRandom2.estado.vertice.size() > ReinasBTRandom2.threshold) {
				alternativas = List2.randomUnitary(alternativas);
			}
			for(Integer a:alternativas) {	
				ReinasBTRandom2.estado.forward(a);
				ReinasBTRandom2.btm();  
				ReinasBTRandom2.estado.back(a);
			}
		}
	}
	
	public static SolucionReinas solucion() {
		return ReinasBTRandom2.soluciones.stream().findFirst().get();
	}

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		Integer n = 110;
		ReinasBTRandom2.threshold = 15;
		long startTime = System.nanoTime();
		ReinasBTRandom2.btm(n);	
		long endTime = System.nanoTime() - startTime;
		System.out.println("1 = "+endTime);
		System.out.println("Iteraciones = "+ReinasBTRandom2.iteraciones);
		System.out.println(ReinasBTRandom2.solucion());
	}

}
