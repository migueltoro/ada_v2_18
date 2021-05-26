package us.lsi.alg.reinas.manual;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import us.lsi.common.List2;

public class ReinasBTRandom {
	
	public static record StateReinas(ReinasProblem vertice, List<Integer> acciones, List<ReinasProblem> vertices) {
		public static StateReinas of(ReinasProblem vertex, List<Integer> acciones, List<ReinasProblem> vertices) {
			List<Integer> accionesC = List.copyOf(acciones);
			List<ReinasProblem> verticesC = List.copyOf(vertices);
			return new StateReinas(vertex,accionesC,verticesC);
		}
		
		public static StateReinas of(ReinasProblem vertex) {
			List<ReinasProblem> vt = List.of(vertex);
			return new StateReinas(vertex,List.of(),vt);
		}

		StateReinas forward(Integer a) {
			List<Integer> as = List2.addLast(this.acciones(), a);
			ReinasProblem vcn = this.vertice().vecino(a);
			List<ReinasProblem> vt = List2.addLast(this.vertices(), vcn);
			return StateReinas.of(vcn, as, vt);
		}

		StateReinas back(Integer a) {
			List<Integer> as = List2.removeLast(this.acciones());
			List<ReinasProblem> vt = List2.removeLast(this.vertices());
			ReinasProblem van = List2.last(vt);
			return StateReinas.of(van, as, vt);
		}
		
		SolucionReinas solucion() {
			return SolucionReinas.of(this.vertice());
		}
	}
	
	public static ReinasProblem start;
	public static StateReinas estado;
	public static Set<SolucionReinas> soluciones;
	public static Integer threshold;
	public static Integer iteraciones;
	
	public static void btm(Integer nr) {
		ReinasProblem.n = nr;
		ReinasBTRandom.iteraciones = 0;
		do {
			ReinasBTRandom.start = ReinasProblem.first();
			ReinasBTRandom.estado = StateReinas.of(start);
			ReinasBTRandom.soluciones = new HashSet<>();
			btm();
			ReinasBTRandom.iteraciones++;
		}while(ReinasBTRandom.soluciones.size()==0);
	}
	
	public static void btm() {
		if(ReinasBTRandom.estado.vertice().index() == ReinasProblem.n) {
			SolucionReinas s = ReinasBTRandom.estado.solucion();
			if(s != null) ReinasBTRandom.soluciones.add(s);
		} else {
			List<Integer> alternativas = ReinasBTRandom.estado.vertice().acciones();
			if(ReinasBTRandom.estado.vertice().size() > ReinasBTRandom.threshold)
				alternativas = List2.randomUnitary(alternativas);
			for(Integer a:alternativas) {	
				ReinasBTRandom.estado = ReinasBTRandom.estado.forward(a);
				btm();  
				ReinasBTRandom.estado = ReinasBTRandom.estado.back(a);
			}
		}
	}
	
	public static SolucionReinas solucion() {
		return ReinasBTRandom.soluciones.stream().findFirst().get();
	}
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		Integer n = 100;
		ReinasBTRandom.threshold = 15;
		long startTime = System.nanoTime();
		ReinasBTRandom.btm(n);	
		long endTime = System.nanoTime() - startTime;
		System.out.println("1 = "+endTime);
		System.out.println("Iteraciones = "+ReinasBTRandom.iteraciones);
		System.out.println(ReinasBTRandom.solucion());
//		ReinasBTRandom.threshold = n;
//	    startTime = System.nanoTime();
//	    ReinasBTRandom.btm(n);
//		long endTime2 = System.nanoTime() - startTime;
//		System.out.println(String.format("2 = %.2f",(1.*endTime)/endTime2));
//		System.out.println("Iteraciones = "+ReinasBTRandom.iteraciones);
//		System.out.println(ReinasBTRandom.solucion());
	}

}
