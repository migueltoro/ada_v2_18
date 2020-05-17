package us.lsi.ag.sudoku;

import java.util.stream.IntStream;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.ChromosomeFactory.CrossoverType;
import us.lsi.ag.agchromosomes.ValuesInSetChromosome;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.sudoku.datos.DatosSudoku;

public class TestSudokuAG {
	
	
	public static void main(String[] args) {
		AlgoritmoAG.ELITISM_RATE  = 0.1;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 70;
		
		StoppingConditionFactory.NUM_GENERATIONS = 500;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 0.;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.SolutionsNumber;
		
		ChromosomeFactory.crossoverType = CrossoverType.OnePoint;
		
		DatosSudoku.tamSubCuadro = 3;
		DatosSudoku.iniDatos("ficheros/sudoku.txt");
		
		var p = new ProblemaSudokuAG();
		
		AlgoritmoAG<ValuesInSetChromosome> a = AlgoritmoAG.create(ChromosomeFactory.ChromosomeType.InSet ,p);		
		a.ejecuta();
		
		System.out.println(p.getSolucion(a.getBestChromosome()));
		System.out.println(IntStream.range(0,p.n)
				.allMatch(i->p.values(i).contains(a.getBestChromosome().decode().get(i))));
		System.out.println(a.getBestChromosome().fitness());
		System.out.println(p.values);
		
	}

}
