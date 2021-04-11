package us.lsi.alg.mochila.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;

public class MochilaPD {
	
	public static record Spm(Integer a,Integer weight) implements Comparable<Spm> {
		
		public static Spm of(Integer a, Integer weight) {
			return new Spm(a, weight);
		}
		
		@Override
		public int compareTo(Spm sp) {
			return this.weight.compareTo(sp.weight);
		}
	}
	
	public static Integer maxValue = Integer.MIN_VALUE;
	public static Mochila start;
	public static Map<Mochila,Spm> memory;
	
	public static SolucionMochila pd(Integer initialCapacity) {
		MochilaPD.maxValue = Integer.MIN_VALUE;
		MochilaPD.start = Mochila.of(0,initialCapacity);
		MochilaPD.memory = new HashMap<>();
		pd(start,0,memory);
		return MochilaPD.solucion();
	}
	
	public static SolucionMochila pd(Integer initialCapacity, Integer maxValue, SolucionMochila s) {
		MochilaPD.maxValue = maxValue;
		MochilaPD.start = Mochila.of(0,initialCapacity);
		MochilaPD.memory = new HashMap<>();
		pd(start,0,memory);
		if(MochilaPD.memory.get(start) == null) return s;
		else return MochilaPD.solucion();
	}
	
	public static Spm pd(Mochila vertex,Integer accumulateValue, Map<Mochila,Spm> memory) {
		Spm r;
		if(memory.containsKey(vertex)) {
			r = memory.get(vertex);
		} else if(vertex.index() == DatosMochila.n) {
			r = Spm.of(null,0);
			memory.put(vertex,r);
			if(accumulateValue > MochilaPD.maxValue) MochilaPD.maxValue = accumulateValue;
		} else {
			List<Spm> soluciones = new ArrayList<>();
			for(Integer a:vertex.acciones()) {	
				Double cota = accumulateValue + Heuristica.cota(vertex,a);
				if(cota < MochilaPD.maxValue) continue;				
				Spm s = pd(vertex.vecino(a),accumulateValue+a*DatosMochila.getValor(vertex.index()),memory);
				if(s!=null) {
					Spm sp = Spm.of(a,s.weight()+a*DatosMochila.getValor(vertex.index()));
					soluciones.add(sp);
				}
			}
			r = soluciones.stream().max(Comparator.naturalOrder()).orElse(null);
			if(r!=null) memory.put(vertex,r);
		}
		return r;
	}
	
	public static SolucionMochila solucion(){
		SolucionMochila r = SolucionMochila.empty();
		Mochila v = MochilaPD.start;
		Spm s = MochilaPD.memory.get(v);
		while(s.a() != null) {
			r.add(DatosMochila.getObjeto(v.index()), s.a());
			v = v.vecino(s.a());	
			s = MochilaPD.memory.get(v);
		}
		return r;
	}
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;
		Mochila v1 = Mochila.of(0, DatosMochila.capacidadInicial);
		SolucionMochila s = Heuristica.solucionVoraz(v1);	
		MochilaPD.pd(78,s.getValor(),s);	
		System.out.println(MochilaPD.solucion());		
	}


}
