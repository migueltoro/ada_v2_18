package us.lsi.ag.mochila;



import java.util.List;

import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.sa.AlgoritmoSA;
import us.lsi.sa.StateSaChromosome;

public class TestMochilaSARange {
	

	public static void main(String[] args) {
		
		AlgoritmoSA.alfa = 0.995;
		AlgoritmoSA.numeroDeIteracionesPorIntento = 2500;
		AlgoritmoSA.numeroDeIntentos = 20;
		AlgoritmoSA.numeroDeIteracionesALaMismaTemperatura = 10;
		AlgoritmoSA.temperaturaInicial = 500.;
		
		DatosMochila.capacidadInicial = 78;
		DatosMochilaAGRange p = new DatosMochilaAGRange("ficheros/objetosmochila.txt");
		StateSaChromosome c = StateSaChromosome.random(p,ChromosomeType.Range);
		AlgoritmoSA a = AlgoritmoSA.of(c);
		
		a.ejecuta();		
		StateSaChromosome s = (StateSaChromosome) a.mejorSolucionEncontrada;
		@SuppressWarnings("unchecked")
		List<Integer> d = (List<Integer>) s.decode();
		System.out.println(p.solucion(d));
	}

}
