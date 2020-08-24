package us.lsi.problemas.asignacion;

import java.util.List;

import us.lsi.ag.SeqNormalProblemAG;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.SeqNomalChromosome;
import us.lsi.ag.agchromosomes.ChromosomeFactory.CrossoverType;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;



public class AsignacionTareasAGTest {

	

	public static void main(String[] args){
		
		AlgoritmoAG.ELITISM_RATE  = 0.20;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 40;
		
		StoppingConditionFactory.NUM_GENERATIONS = 6000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 0.;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.SolutionsNumber;
		
		ChromosomeFactory.crossoverType = CrossoverType.OnePoint;
		

		SeqNormalProblemAG<List<Integer>> p = AsignacionTareasAG.create("ficheros/asignacionDeTareas.txt");
		AlgoritmoAG<SeqNomalChromosome> ap = AlgoritmoAG.<SeqNomalChromosome>create(p);
		ap.ejecuta();
		System.out.println("================================");
		
		System.out.println("================================");
/*		Set<Chromosome> s = AlgoritmoAG.bestChromosomes.stream().collect(Collectors.toSet());
		for (Chromosome c: s) {
			System.out.println(ChromosomeFactory.asIndex(c).fitness()+","+p.getSolucion(ChromosomeFactory.asIndex(c)));
		}
		System.out.println("================================");
		System.out.println(s.size());
*/		SeqNomalChromosome cr = ap.getBestChromosome();
		System.out.println(p.getSolucion(cr)+","+cr.fitness()+", ");
		System.out.println("Asignacion de tareas: " );

		List<Integer> ls = cr.decode();
		Double sumCoste = 0.;
		for (int i = 0; i < ls.size(); i++) {
			Double coste = AsignacionTareasAG.a.getCoste(i,ls.get(i));
			System.out.println("Tarea(" + i +  ", " + ls.get(i) + ")=" + coste );
			sumCoste = sumCoste + AsignacionTareasAG.a.getCoste(i,ls.get(i));

		}
		System.out.println("Coste: " + sumCoste);
	}	

}

