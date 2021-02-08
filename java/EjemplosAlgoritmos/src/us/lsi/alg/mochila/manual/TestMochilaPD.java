package us.lsi.alg.mochila.manual;

import java.util.List;
import java.util.Locale;

import us.lsi.alg.mochila.MochilaEdge;
import us.lsi.alg.mochila.MochilaVertex;
import us.lsi.mochila.datos.DatosMochila;

public class TestMochilaPD {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		MochilaVertex.capacidadInicial = 78;
		MochilaVertex v1 = MochilaVertex.initialVertex();
		Integer maxValue = Auxiliar.voraz(v1);
		List<MochilaEdge> edges = MochilaPD.pd(78,maxValue);	
		System.out.println(Auxiliar.solucionFromEdges(edges));
		
	}

}
