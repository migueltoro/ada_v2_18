package us.lsi.expression;


import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.ExpressionChromosome;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.tiposrecursivos.ast.Exp;


public class TestExpression {

	
	public static void main(String[] args) {
		
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 50;
		
		StoppingConditionFactory.NUM_GENERATIONS = 50000;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.SolutionsNumber;
		StoppingConditionFactory.FITNESS_MIN = -1.;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		
		ChromosomeFactory.crossoverType = ChromosomeFactory.CrossoverType.OnePoint;
		
		DatosExpression d = new DatosExpression();
		AlgoritmoAG<Exp,Exp> ap = AlgoritmoAG.of(d);
		ap.ejecuta();

		System.out.println("================================");
		System.out.println(ap.bestSolution()+","+(ap.getBestChromosome().fitness()));
		System.out.println("================================");
//		for(int i=0;i<d.numConstants();i++){
//			System.out.println(String.format("%s = %f",d.getConstant(i).getName(),d.getConstant(i).getValue()));
//		}

	}

}
