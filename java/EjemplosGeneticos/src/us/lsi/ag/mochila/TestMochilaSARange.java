package us.lsi.ag.mochila;


import us.lsi.ag.ValuesInRangeProblemAG;
import us.lsi.ag.agchromosomes.ValuesInRangeChromosome;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;
import us.lsi.sa.AlgoritmoSA;
import us.lsi.sa.StateSa;
import us.lsi.sa.StateSaChromosome;

public class TestMochilaSARange {
	

	public static void main(String[] args) {
		
		AlgoritmoSA.alfa = 0.995;
		AlgoritmoSA.numeroDeIteracionesPorIntento = 2500;
		AlgoritmoSA.numeroDeIntentos = 20;
		AlgoritmoSA.numeroDeIteracionesALaMismaTemperatura = 10;
		AlgoritmoSA.temperaturaInicial = 500.;
		
		DatosMochila.capacidadInicial = 78;
		ValuesInRangeProblemAG<Integer,SolucionMochila> p = new ProblemaMochilaAGRange("ficheros/objetosmochila.txt");
		StateSa c = StateSaChromosome.random(p,ChromosomeType.Range);
		AlgoritmoSA a = AlgoritmoSA.of(c);
		
		a.ejecuta();		
		var s = a.mejorSolucionEncontrada;
		System.out.println(s.fitness());
		System.out.println(p.getSolucion((ValuesInRangeChromosome<Integer>) ((StateSaChromosome) s).chromosome));
	}

}
