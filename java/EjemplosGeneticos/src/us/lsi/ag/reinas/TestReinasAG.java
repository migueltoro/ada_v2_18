package us.lsi.ag.reinas;

import java.util.List;
import java.util.Set;

import us.lsi.ag.IndexChromosome;
import us.lsi.ag.IndexProblemAG;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.agchromosomes.ChromosomeFactory.CrossoverType;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.common.Sets2;
import us.lsi.reinas.datos.Reina;



public class TestReinasAG {

	

	public static void main(String[] args){
		
		AlgoritmoAG.ELITISM_RATE  = 0.20;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 40;
		
		StoppingConditionFactory.NUM_GENERATIONS = 3000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 0.;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.SolutionsNumber;
		
		ChromosomeFactory.crossoverType = CrossoverType.OnePoint;
		
		ProblemaReinasAG.numeroDeReinas = 20;
		IndexProblemAG<List<Reina>> p = ProblemaReinasAG.create();
		AlgoritmoAG<IndexChromosome> ap = AlgoritmoAG.<IndexChromosome>create(ChromosomeType.IndexPermutation,p);
		ap.ejecuta();
		System.out.println("================================");
		
		System.out.println("================================");
/*		Set<Chromosome> s = AlgoritmoAG.bestChromosomes.stream().collect(Collectors.toSet());
		for (Chromosome c: s) {
			System.out.println(ChromosomeFactory.asIndex(c).fitness()+","+p.getSolucion(ChromosomeFactory.asIndex(c)));
		}
		System.out.println("================================");
		System.out.println(s.size());
*/		IndexChromosome cr = ap.getBestChromosome();
		System.out.println(p.getSolucion(cr)+","+cr.fitness()+", ");
		List<Integer> ls = cr.decode();
		Set<Integer> dp = Sets2.newHashSet();
		Set<Integer> ds = Sets2.newHashSet();
		for (int i = 0; i < ls.size(); i++) {
			dp.add(ls.get(i)-i);
			ds.add(ls.get(i)+i);
		}
		System.out.println(ProblemaReinasAG.numeroDeReinas+","+dp.size()+","+ds.size());
	}	

}

