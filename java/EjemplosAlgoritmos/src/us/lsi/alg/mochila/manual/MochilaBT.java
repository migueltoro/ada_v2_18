package us.lsi.alg.mochila.manual;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;

public class MochilaBT {
	
	public static record StateMochila(Mochila vertice, Integer valorAcumulado, List<Integer> acciones) {
		public static StateMochila of(Mochila vertex, Integer valorAcumulado, List<Integer> acciones) {
			return new StateMochila(vertex, valorAcumulado, new ArrayList<>(acciones));
		}

		StateMochila forward(Integer a) {
			List<Integer> ls = new ArrayList<>(this.acciones());
			ls.add(a);
			return StateMochila.of(this.vertice().vecino(a), 
					this.valorAcumulado() + a * DatosMochila.getValor(this.vertice().index()),ls);
		}

		StateMochila back(Integer a) {
			List<Integer> ls = new ArrayList<>(this.acciones());
			ls.remove(ls.size()-1);
			return StateMochila.of(this.vertice().anterior(a), 
					valorAcumulado() - a * DatosMochila.getValor(this.vertice().index()-1),ls);
		}
		
		SolucionMochila solucion() {
			SolucionMochila s = SolucionMochila.empty();
			Mochila v = MochilaBT.start;
			for(Integer a: this.acciones()) {	
				s.add(DatosMochila.getObjeto(v.index()), a);
				v = v.vecino(a);
			}
			return s;
		}
	}
	
	public static Mochila start;
	public static StateMochila estado;
	public static Integer maxValue;
	public static Set<SolucionMochila> soluciones;
	
	public static void btm(Integer capacidadInicial) {
		MochilaBT.start = Mochila.of(0,capacidadInicial);
		MochilaBT.estado = StateMochila.of(start,0,new ArrayList<>());
		MochilaBT.maxValue = Integer.MIN_VALUE;
		MochilaBT.soluciones = new HashSet<>();
		btm();
	}
	
	public static void btm(Integer capacidadInicial, Integer maxValue, SolucionMochila s) {
		MochilaBT.start = Mochila.of(0,capacidadInicial);
		MochilaBT.estado = StateMochila.of(start,0,new ArrayList<>());
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
				if(cota <= MochilaBT.maxValue) continue;
				MochilaBT.estado = MochilaBT.estado.forward(a);
				btm();  
				MochilaBT.estado = MochilaBT.estado.back(a);
			}
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;
		Mochila v1 = Mochila.of(0, DatosMochila.capacidadInicial);
		SolucionMochila s = Heuristica.solucionVoraz(v1);
		MochilaBT.btm(78,s.getValor(),s);	
		System.out.println(MochilaBT.soluciones);

	}

}
