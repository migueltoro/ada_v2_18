package us.lsi.ag.sudoku;

import us.lsi.ag.ValuesInRangeChromosome;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.ChromosomeFactory.CrossoverType;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.sudoku.datos.DatosSudoku;


public class TestSudokuAG {


	public static void main(String[] args) {
		AlgoritmoAG.ELITISM_RATE  = 0.2;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 50;
		
		StoppingConditionFactory.NUM_GENERATIONS = 300;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 0.;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.SolutionsNumber;
		
		ChromosomeFactory.crossoverType = CrossoverType.OnePoint;
		
		DatosSudoku.tamSubCuadro = 3;
		DatosSudoku.iniDatos("ficheros/sudoku.txt");
		
		var p = new ProblemaSudokuAG();
		AlgoritmoAG<ValuesInRangeChromosome<Integer>> a = AlgoritmoAG.create(ChromosomeFactory.ChromosomeType.Range ,p);		
		a.ejecuta();
		
		System.out.println(p.getSolucion(a.getBestChromosome()));
	}

}
