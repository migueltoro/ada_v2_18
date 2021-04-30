package us.lsi.alg.mochila.manual;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import us.lsi.common.List2;


public class MochilaBT {
	
	public static record StateMochila(MochilaProblem vertice, Integer valorAcumulado, 
			List<Integer> acciones, List<MochilaProblem> vertices) {
		public static StateMochila of(MochilaProblem vertex, Integer valorAcumulado, List<Integer> acciones, List<MochilaProblem> vertices) {
			List<Integer> accionesC = List.copyOf(acciones);
			List<MochilaProblem> verticesC = List.copyOf(vertices);
			return new StateMochila(vertex, valorAcumulado, accionesC, verticesC);
		}
		
		public static StateMochila of(MochilaProblem vertex) {
			List<MochilaProblem> vt = List.of(vertex);
			return new StateMochila(vertex,0,new ArrayList<>(),vt);
		}

		StateMochila forward(Integer a) {
			List<Integer> as = List2.addLast(this.acciones(), a);
			MochilaProblem vcn = this.vertice().vecino(a);
			List<MochilaProblem> vt = List2.addLast(this.vertices(), vcn);
			return StateMochila.of(vcn, this.valorAcumulado() + a * DatosMochila.valor(this.vertice().index()), as, vt);
		}

		StateMochila back(Integer a) {
			List<Integer> as = List2.removeLast(this.acciones());
			List<MochilaProblem> vt = List2.removeLast(this.vertices());
			MochilaProblem van = List2.last(vt);
			return StateMochila.of(van, this.valorAcumulado() - a * DatosMochila.valor(van.index()), as, vt);
		}
		
		SolucionMochila solucion() {
			return SolucionMochila.of(MochilaBT.start,this.acciones);
		}
	}
	
	public static MochilaProblem start;
	public static StateMochila estado;
	public static Integer maxValue;
	public static Set<SolucionMochila> soluciones;
	
	public static void btm(Integer capacidadInicial) {
		MochilaBT.start = MochilaProblem.of(0,capacidadInicial);
		MochilaBT.estado = StateMochila.of(start);
		MochilaBT.maxValue = Integer.MIN_VALUE;
		MochilaBT.soluciones = new HashSet<>();
		btm();
	}
	
	public static void btm(Integer capacidadInicial, Integer maxValue, SolucionMochila s) {
		MochilaBT.start = MochilaProblem.of(0,capacidadInicial);
		MochilaBT.estado = StateMochila.of(start);
		MochilaBT.maxValue = maxValue;
		MochilaBT.soluciones = new HashSet<>();
		MochilaBT.soluciones.add(s);
		btm();
	}
	
	public static void btm() {
		if(MochilaBT.estado.vertice().index() == DatosMochila.n) {
			Integer value = estado.valorAcumulado();
			if(value > MochilaBT.maxValue) {
				MochilaBT.maxValue = value;
				MochilaBT.soluciones.add(MochilaBT.estado.solucion());
			}
		} else {
			List<Integer> alternativas = MochilaBT.estado.vertice().acciones();
			for(Integer a:alternativas) {	
				Double cota = MochilaBT.estado.valorAcumulado()+Heuristica.cota(MochilaBT.estado.vertice(),a);
				if(cota < MochilaBT.maxValue) continue;
				MochilaBT.estado = MochilaBT.estado.forward(a);
				btm();  
				MochilaBT.estado = MochilaBT.estado.back(a);
			}
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.datos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;
		MochilaProblem v1 = MochilaProblem.of(0, DatosMochila.capacidadInicial);
		SolucionMochila s = Heuristica.solucionVoraz(v1);
		long startTime = System.nanoTime();
		MochilaBT.btm(78);	
		long endTime = System.nanoTime() - startTime;
		System.out.println("1 = "+endTime);
		System.out.println(MochilaBT.soluciones);
	    startTime = System.nanoTime();
		MochilaBT.btm(78,s.valor(),s);
		long endTime2 = System.nanoTime() - startTime;
		System.out.println("2 = "+1.*endTime2/endTime);
		System.out.println(MochilaBT.soluciones);

	}

}
