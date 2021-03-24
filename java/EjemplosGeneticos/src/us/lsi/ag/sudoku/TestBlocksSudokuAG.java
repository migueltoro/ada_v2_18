package us.lsi.ag.sudoku;

import java.util.List;
import java.util.stream.IntStream;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.sudoku.datos.DatosSudoku;
import us.lsi.sudoku.datos.SolucionSudoku;

public class TestBlocksSudokuAG {
	
	public static void main(String[] args) {
		AlgoritmoAG.ELITISM_RATE  = 0.1;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 70;
		
		StoppingConditionFactory.NUM_GENERATIONS = 10000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 0.;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.SolutionsNumber;
		
		DatosSudoku.tamSubCuadro = 3;
		DatosSudoku.iniDatos("ficheros/sudoku.txt");
		System.out.println(SolucionSudoku.of());
		
		BlocksDatosSudokuAG p = new BlocksDatosSudokuAG();
		
		AlgoritmoAG<List<Integer>> a = AlgoritmoAG.create(p);		
		a.ejecuta();
		
		List<Integer> values = a.getBestChromosome().decode();		
		System.out.println(p.getSolucion(values));
//		System.out.println(IntStream.range(0,p.n)
//				.allMatch(i->p.values(i).contains(a.getBestChromosome().decode().get(i))));
//		System.out.println(a.getBestChromosome().fitness());
//		System.out.println(p.values);

		System.out.println(p.initialValues.subList(0,p.blocksLimits().get(0)));
		for(int i=0;i<p.blocksLimits().size()-1;i++) {
			System.out.println(p.initialValues.subList(p.blocksLimits().get(i),p.blocksLimits().get(i+1)));
		}
		System.out.println(p.initialValues.subList(p.blocksLimits().get(p.blocksLimits.size()-1),p.initialValues.size()));
		System.out.println(p.initialValues);
		System.out.println(p.blocksLimits);
	}

}
