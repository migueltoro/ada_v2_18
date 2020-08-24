package us.lsi.ag.mochila;

import us.lsi.mochila.datos.DatosMochila;
import us.lsi.sa.AlgoritmoSA;
import us.lsi.sa.StateSa;

public class TestMochilaSARange2 {
	
	

	public static void main(String[] args) {
		
		AlgoritmoSA.alfa = 0.995;
		AlgoritmoSA.numeroDeIteracionesPorIntento = 2500;
		AlgoritmoSA.numeroDeIntentos = 20;
		AlgoritmoSA.numeroDeIteracionesALaMismaTemperatura = 10;
		AlgoritmoSA.temperaturaInicial = 500.;
		
		DatosMochila.capacidadInicial = 78;
		DatosMochila.iniDatos("ficheros/objetosmochila.txt");
		RangeInteger p = new RangeInteger();
		StateSa c = p.random();
		AlgoritmoSA a = AlgoritmoSA.of(c);
		
		a.ejecuta();		
		StateSa s = a.mejorSolucionEncontrada;
		System.out.println(s.fitness());
		System.out.println(RangeInteger.getSolucion(((RangeInteger)s).values));
	}

}
