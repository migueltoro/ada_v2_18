package us.lsi.ag.mochila;


import us.lsi.ag.ValuesInRangeProblemAG;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.RangeChromosome;
import us.lsi.ag.agchromosomes.ValuesInRangeChromosome;
import us.lsi.ag.agstopping.SolutionsNumber;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;


public class TestMochilaAGRange {

	public static void main(String[] args) {
		
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 50;
		
		StoppingConditionFactory.NUM_GENERATIONS = 60000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 623;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.SolutionsNumber;
		
		DatosMochila.capacidadInicial = 78;
		ValuesInRangeProblemAG<Integer,SolucionMochila> p = new ProblemaMochilaAGRange("ficheros/objetosmochila.txt");
		
		AlgoritmoAG<ValuesInRangeChromosome<Integer>> ap = AlgoritmoAG.create(p);
		ap.ejecuta();
		
		System.out.println(DatosMochila.getObjetos());
		System.out.println(RangeChromosome.bitsNumber);
		System.out.println("================================");
		System.out.println(ap.getBestChromosome().decode());
		System.out.println(p.getSolucion(ap.getBestChromosome()));
		System.out.println("================================");
		System.out.println(SolutionsNumber.numeroDeGeneraciones);
	}

}
