package us.lsi.ag.sudoku;

import java.util.List;
import java.util.stream.IntStream;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.sudoku.datos.DatosSudoku;

public class TestInsetSudokuAG {
	
	
	public static void main(String[] args) {
		AlgoritmoAG.ELITISM_RATE  = 0.1;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 70;
		
		StoppingConditionFactory.NUM_GENERATIONS = 5000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 0.;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.SolutionsNumber;
		
		DatosSudoku.tamSubCuadro = 3;
		DatosSudoku.iniDatos("ficheros/sudoku.txt");
		
		InSetDatosSudokuAG p = new InSetDatosSudokuAG();
		
		AlgoritmoAG<List<Integer>> a = AlgoritmoAG.create(p);		
		a.ejecuta();
		
		System.out.println(p.getSolucion(a.getBestChromosome().decode()));
		System.out.println(IntStream.range(0,p.n)
				.allMatch(i->p.values(i).contains(a.getBestChromosome().decode().get(i))));
		System.out.println(a.getBestChromosome().fitness());
		System.out.println(p.values);
		
	}

}
