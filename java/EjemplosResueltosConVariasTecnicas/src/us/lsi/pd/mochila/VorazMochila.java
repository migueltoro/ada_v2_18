package us.lsi.pd.mochila;

import java.util.Comparator;
import java.util.List;

import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;

public class VorazMochila {

	public static SolucionMochila getSolucionVoraz(ProblemaMochilaPD p) {
		SolucionMochila r = SolucionMochila.empty();
		while (!p.esCasoBase()) {
			List<Integer> alternativas = p.getAlternativas();
			Integer a = alternativas.stream().max(Comparator.naturalOrder()).get();
			r.add(DatosMochila.getObjeto(p.getIndex()), a);
			p = p.getSubProblema(a);
		}
		return r;
	}
	
	public static void main(String[] args) {
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;	
		ProblemaMochilaPD  p = ProblemaMochilaPD.createInitial();
		SolucionMochila s = getSolucionVoraz(p);
		System.out.println(s);
		Integer r = DatosMochila.getCotaSuperior(0, 78);
		System.out.println(r);
	}

}
