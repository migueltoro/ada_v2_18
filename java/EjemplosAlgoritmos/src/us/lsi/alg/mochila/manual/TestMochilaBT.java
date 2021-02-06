package us.lsi.alg.mochila.manual;

import java.util.Locale;

import us.lsi.alg.mochila.MochilaVertex;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;

public class TestMochilaBT {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		MochilaVertex.capacidadInicial = 78;
		MochilaVertex v1 = MochilaVertex.initialVertex();
		Integer maxValue = MochilaBT.voraz(v1);
		SolucionMochila s = MochilaBT.solucionVoraz(v1);
		MochilaBT.btm(78,maxValue,s);	
		System.out.println(MochilaBT.soluciones);
	}

}
