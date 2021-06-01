package us.lsi.alg.typ.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import us.lsi.common.List2;

public class TyPBT2 {
	
	public static class StateTyP {
		
		TyPProblem vertice; 
		Integer valorAcumulado; 
		List<Integer> acciones; 
		List<TyPProblem> vertices;
		
		public StateTyP(TyPProblem vertice, Integer valorAcumulado, List<Integer> acciones, List<TyPProblem> vertices) {
			super();
			this.vertice = vertice;
			this.valorAcumulado = valorAcumulado;
			this.acciones = acciones;
			this.vertices = vertices;
		}
		
		public static StateTyP of(TyPProblem vertex) {
			List<TyPProblem> vt = List2.of(vertex);
			return new StateTyP(vertex,0,new ArrayList<>(),vt);
		}

		void forward(Integer a) {
			this.acciones.add(a);
			TyPProblem vcn = this.vertice.vecino(a);		
			this.vertices.add(vcn);
			this.valorAcumulado =  vcn.maxCarga(); 
			this.vertice = vcn;
		}

		void back(Integer a) {
			this.acciones.remove(this.acciones.size()-1);
			this.vertices.remove(this.vertices.size()-1);
			this.vertice = this.vertices.get(this.vertices.size()-1);
			this.valorAcumulado = this.vertice.maxCarga(); 
		}
		
		SolucionTyP solucion() {
			return SolucionTyP.of(start,this.vertice.acciones());
		}
	}
	
	public static TyPProblem start;
	public static StateTyP estado;
	public static Integer minValue;
	public static Set<SolucionTyP> soluciones;
	
	public static void btm(Integer minValue) {
		TyPBT2.start = TyPProblem.first();
		TyPBT2.estado = StateTyP.of(start);
		TyPBT2.minValue = minValue;
		TyPBT2.soluciones = new HashSet<>();
		btm();
	}
	
	public static void btm(Integer minValue, SolucionTyP s) {
		TyPBT2.start = TyPProblem.first();
		TyPBT2.estado = StateTyP.of(start);
		TyPBT2.minValue = minValue;
		TyPBT2.soluciones = new HashSet<>();
		TyPBT2.soluciones.add(s);
		btm();
	}
	
	public static void btm() {
		if(TyPBT2.estado.vertice.index() == DatosTyP.n) {
			Integer value = estado.valorAcumulado;
			if(value < TyPBT2.minValue) {
				TyPBT2.minValue = value;
				TyPBT2.soluciones.add(TyPBT2.estado.solucion());
			}
		} else {
			List<Integer> alternativas = TyPBT2.estado.vertice.acciones();
			for(Integer a:alternativas) {	
				Integer cota = Heuristica.cota(TyPBT2.estado.vertice,a);
				if(cota >= TyPBT2.minValue) continue;
				TyPBT2.estado.forward(a);
				btm();  
				TyPBT2.estado.back(a);
			}
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosTyP.datos("ficheros/tareas.txt",5);
		TyPProblem v1 = TyPProblem.first();
		SolucionTyP s = Heuristica.solucionVoraz(v1);
		System.out.println(s);
		long startTime = System.nanoTime();
		TyPBT2.btm(Integer.MAX_VALUE);
		long endTime = System.nanoTime() - startTime;
		System.out.println("1 = "+endTime);
		System.out.println(TyPBT2.soluciones.stream().min(Comparator.comparing(x->x.maxCarga())).get());
		startTime = System.nanoTime();
		TyPBT2.btm(s.maxCarga(),s);
		long endTime2 = System.nanoTime() - startTime;
		System.out.println("2 = "+1.*endTime2/endTime);
		System.out.println(TyPBT2.soluciones.stream().min(Comparator.comparing(x->x.maxCarga())).get());
	}

}
