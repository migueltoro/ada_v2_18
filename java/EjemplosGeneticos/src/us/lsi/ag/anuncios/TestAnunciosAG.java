package us.lsi.ag.anuncios;



import us.lsi.ag.IndexChromosome;
import us.lsi.ag.IndexProblemAG;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.anuncios.datos.ListaDeAnunciosAEmitir;
import us.lsi.anuncios.datos.DatosAnuncios;


public class TestAnunciosAG {
	

	public static void main(String[] args){
		
		
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 100;
		
		StoppingConditionFactory.NUM_GENERATIONS = 400;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.GenerationCount;
		
		DatosAnuncios.tiempoTotal = 30;
		System.out.println("ficheros/anuncios.txt");
		IndexProblemAG<ListaDeAnunciosAEmitir> p = new ProblemaAnunciosAG("ficheros/anuncios.txt");		
		AlgoritmoAG<IndexChromosome> ap = AlgoritmoAG.create(ChromosomeType.IndexPermutationSubList,p);
		ap.ejecuta();
		
		
		System.out.println("================================");
		System.out.println(p.getSolucion(ap.getBestChromosome()));
		System.out.println("================================");		
	}	

}
