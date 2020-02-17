package us.lsi.bt.mochila;

import java.util.Comparator;
import java.util.List;

import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;


public class VorazMochila {

	public static SolucionMochila getSolucionVoraz(EstadoMochila e) {
		SolucionMochila r = SolucionMochila.empty();
		while (!e.esCasoBase()) {
			List<Integer> alternativas = e.getAlternativas();
			Integer a = alternativas.stream().max(Comparator.naturalOrder()).get();
			r.add(DatosMochila.getObjeto(e.getIndex()), a);
			e = e.avanza(a);
		}
		return r;
	}
	
	public static void main(String[] args) {
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;	
		EstadoMochila  p = EstadoMochila.createInitial();
		SolucionMochila s = getSolucionVoraz(p);
		System.out.println(s);
		Integer r = DatosMochila.getCotaSuperior(0, 78);
		System.out.println("Cota Superior = "+ r);
	}

}
