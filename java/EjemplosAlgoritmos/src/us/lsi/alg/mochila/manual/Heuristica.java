package us.lsi.alg.mochila.manual;

import java.util.Locale;

import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;

public class Heuristica {
	
	
	private static record HMochila(Integer index, Double capacidadRestante) {		
		public static HMochila of(Integer index, Double capacidadRestante) {
			return new HMochila(index, capacidadRestante);
		}
		
		public Double heuristicAction() {
			return Math.min(this.capacidadRestante()/DatosMochila.getPeso(this.index()),DatosMochila.getNumMaxDeUnidades(index));
		}
		
	}

	public static Integer voraz(Mochila v1) {
		Mochila v = v1;
		Integer r = 0;
		while (v.capacidadRestante() > 0 && v.index() < DatosMochila.n) {
			Integer a = v.greedyAction();
			r = r + a * DatosMochila.getValor(v.index());
			v = Mochila.of(v.index() + 1, v.capacidadRestante() - a * DatosMochila.getPeso(v.index()));
		}
		return r;
	}
	
	public static SolucionMochila solucionVoraz(Mochila v1) {
		SolucionMochila r = SolucionMochila.empty();
		Mochila v = v1;
		while (v.capacidadRestante() > 0 && v.index() < DatosMochila.n) {
			Integer a = v.greedyAction();
			r.add(DatosMochila.getObjeto(v.index()), a);
			v = Mochila.of(v.index() + 1, v.capacidadRestante() - a * DatosMochila.getPeso(v.index()));
		}
		return r;
	}
	
	
	public static Double heuristica(Mochila v1) {
		HMochila v = HMochila.of(v1.index(),v1.capacidadRestante().doubleValue());
		Double r = 0.;
		while (v.capacidadRestante() > 0 && v.index() < DatosMochila.n) {
			Double a = v.heuristicAction();
			r = r + a * DatosMochila.getValor(v.index());
			v = HMochila.of(v.index() + 1, v.capacidadRestante() - a * DatosMochila.getPeso(v.index()));
		}
		return r;
	}
	
	public static Integer heuristica2(Mochila v1) {
		Mochila v = v1;
		Integer r = 0;
		while (v.capacidadRestante() > 0 && v.index() < DatosMochila.n) {
			Integer a = v.greedyAction()+1;
			r = r + a * DatosMochila.getValor(v.index());
			Integer cr = v.capacidadRestante() - a * DatosMochila.getPeso(v.index());
			v = Mochila.of(v.index() + 1, cr>=0?cr:0);
		}
		return r;
	}
	
	
	public static Double cota(Mochila v1, Integer a) {
		Mochila v2 = v1.vecino(a);
		return a*DatosMochila.getValor(v1.index()).doubleValue()+heuristica(v2);
	}
	
	public static Double cota2(Mochila v1, Integer a) {
		Mochila v2 = v1.vecino(a);
		return a*DatosMochila.getValor(v1.index()).doubleValue()+heuristica2(v2);
	}
	
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;
		Mochila v1 = Mochila.of(0, DatosMochila.capacidadInicial);
		Integer r = Heuristica.voraz(v1);
		Double s1 = Heuristica.heuristica(v1);
		Integer s2 = Heuristica.heuristica2(v1);
		System.out.printf("%d,%.2f,%d",r,s1,s2);
	}

}
