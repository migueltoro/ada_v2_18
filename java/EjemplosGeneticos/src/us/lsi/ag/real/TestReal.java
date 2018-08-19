package us.lsi.ag.real;

import us.lsi.ag.ValuesInRangeChromosome;
import us.lsi.ag.ValuesInRangeProblemAG;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;

import java.util.List;

public class TestReal {

	public static void main(String[] args){
		
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 200;
		
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.GenerationCount;
		StoppingConditionFactory.NUM_GENERATIONS = 100;
		
		ChromosomeFactory.crossoverType = ChromosomeFactory.CrossoverType.OnePoint;
		
		ValuesInRangeProblemAG<Double,List<Double>> p = new ProblemaReal();
		var ap = AlgoritmoAG.<ValuesInRangeChromosome<Double>>create(ChromosomeFactory.ChromosomeType.Real,p);
		ap.ejecuta();
		
		ValuesInRangeChromosome<Double> cr = ap.getBestChromosome();
		System.out.println("================================");
		System.out.println(p.getSolucion(cr)+","+(cr.fitness()));
		System.out.println("================================");
		
	}	
	
}
