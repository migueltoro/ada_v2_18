package us.lsi.problemas.coloreado;

import java.util.Map;

import us.lsi.ag.ValuesInRangeChromosome;
import us.lsi.ag.ValuesInRangeProblemAG;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.common.Strings2;
import us.lsi.grafos.datos.Ciudad;

public class TestColoreadoAG {

	public static void main(String[] args){
		setConstantes();
		
		ValuesInRangeProblemAG<Integer,Map<Ciudad,Integer>> problem = new ProblemaColorAG("./ficheros/Andalucia.txt");
		var alg = AlgoritmoAG.<ValuesInRangeChromosome<Integer>>create(ChromosomeType.Range, problem);
		alg.ejecuta();
		
		ValuesInRangeChromosome<Integer> mejorSolucion = alg.getBestChromosome();
		System.out.println("================================");
		Strings2.toConsole(problem.getSolucion(mejorSolucion).entrySet(),"Coloreado obtenido");
		System.out.println("Valor de la función de fitness: "+problem.fitnessFunction(mejorSolucion));
		System.out.println("================================");
	}

	private static void setConstantes() {
		// Condiciones "evolutivas" (tasas y demás)
		AlgoritmoAG.ELITISM_RATE  = 0.3;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 100;
		
		// Condiciones de parada
		StoppingConditionFactory.NUM_GENERATIONS = 10000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 623.;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.SolutionsNumber;
	}	

}
