package us.lsi.ag.agchromosomes;

import org.apache.commons.math3.genetics.BinaryMutation;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.CycleCrossover;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.NPointCrossover;
import org.apache.commons.math3.genetics.OnePointCrossover;
import org.apache.commons.math3.genetics.OrderedCrossover;
import org.apache.commons.math3.genetics.RandomKeyMutation;
import org.apache.commons.math3.genetics.SelectionPolicy;
import org.apache.commons.math3.genetics.TournamentSelection;
import org.apache.commons.math3.genetics.UniformCrossover;

import us.lsi.ag.ProblemAG;
import us.lsi.ag.agoperators.SubListCrossoverPolicy;
import us.lsi.ag.agoperators.SubListMutationPolicy;
import us.lsi.common.Preconditions;


/**
 * @author Miguel Toro
 * 
 * <p> Una factoría de cromosomas de los distintos tipos implementados. </p>
 *
 */
public class ChromosomeFactory {
	
	/**
	 * Los diferentes tipos de cromosmomas implementados
	 *
	 */
	public enum ChromosomeType {Binary,Range,Real,InSet,SubList,Permutation,PermutationSubList}
	
	public static ChromosomeType tipo;

	/**
	 * @param tipo El tipo de cromosoma
	 * @return Un cromosoma aleatorio del tipo indicado
	 */
	public static org.apache.commons.math3.genetics.Chromosome randomChromosome(ChromosomeType tipo){
		org.apache.commons.math3.genetics.Chromosome chromosome = null;
		switch(tipo){
		case Binary: chromosome = BinaryChromosome.getInitialChromosome(); break;
		case SubList: chromosome = SubListChromosome.getInitialChromosome(); break;
		case Range: chromosome = RangeChromosome.getInitialChromosome(); break;
		case Permutation: chromosome = PermutationChromosome.getInitialChromosome(); break;
		case PermutationSubList: chromosome = PermutationSubListChromosome.getInitialChromosome(); break;
		case Real: chromosome = DoubleChromosome.getInitialChromosome(); break;
		case InSet: chromosome = ValuesInSetChromosome.getInitialChromosome(); break;
		}
		return chromosome;
	}
	
	/**
	 * <p>Distintos tipo de operadores de cruce </p>
	 *
	 * <ul>
	 * <li> <a href="http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/genetics/OnePointCrossover.html" target="_blank"> OnePointCrossover </a>
	 * <li> <a href="http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/genetics/NPointCrossover.html" target="_blank"> NPointCrossover </a>
	 * <li> <a href="http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/genetics/CycleCrossover.html" target="_blank"> CycleCrossover </a>
	 * <li> <a href="http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/genetics/OrderedCrossover.html" target="_blank"> OrderedCrossover </a>
	 * <li> <a href="http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/genetics/UniformCrossover.html" target="_blank"> UniformCrossover </a>
	 * </ul> 
	 * 
	 */
	
	public enum CrossoverType{Cycle,NPoint,OnePoint,Ordered,Uniform};
	
	/**
	 * Tipo del operador de cruce
	 */
	public static CrossoverType crossoverType = CrossoverType.OnePoint;
	
	/**
	 * Número de puntos usados en la partición si se usa un operador de cruce de tipo NPointCrossover
	 */
	public static int NPOINTCROSSOVER = 3;
	/**
	 * La ratio si se usa el operador de cruce de tipo UniformCrossover
	 */
	public static double RATIO_UNIFORMCROSSOVER = 0.7;
	
	/**
	 * @param tipo El tipo del cromosoma
	 * @param problema Las propiedades del probblema a resolver
	 * @return Un operador de cruce adecuado para un cromosma del tipo indicado
	 */
	public static CrossoverPolicy getCrossoverPolicy(ChromosomeType tipo, ProblemAG problema){
		CrossoverPolicy crossOverPolicyBin = null;	
		switch(crossoverType){
		case Cycle: crossOverPolicyBin = new CycleCrossover<Integer>();break;
		case NPoint: crossOverPolicyBin = new NPointCrossover<Integer>(NPOINTCROSSOVER);break;
		case OnePoint: crossOverPolicyBin = new OnePointCrossover<Integer>();break;
		case Ordered: crossOverPolicyBin = new OrderedCrossover<Integer>(); break;
		case Uniform: crossOverPolicyBin = new UniformCrossover<Integer>(RATIO_UNIFORMCROSSOVER); break;
		}
		CrossoverPolicy crossOverPolicyKey = null;	
		switch(crossoverType){
		case Cycle: crossOverPolicyKey = new CycleCrossover<Double>();break;
		case NPoint: crossOverPolicyKey = new NPointCrossover<Double>(NPOINTCROSSOVER);break;
		case OnePoint: crossOverPolicyKey = new OnePointCrossover<Double>();break;
		case Ordered: crossOverPolicyKey = new OrderedCrossover<Double>(); break;
		case Uniform: crossOverPolicyKey = new UniformCrossover<Double>(RATIO_UNIFORMCROSSOVER); break;
		}
		CrossoverPolicy crossOverPolicy = null;	
		switch(tipo){
		case Binary: crossOverPolicy = crossOverPolicyBin; break;
		case SubList: crossOverPolicy = crossOverPolicyBin; break;
		case Range: crossOverPolicy = crossOverPolicyBin; break;
		case Permutation: crossOverPolicy = crossOverPolicyKey; break;
		case PermutationSubList: crossOverPolicy = new SubListCrossoverPolicy(crossOverPolicyBin,crossOverPolicyKey); break;
		case Real: crossOverPolicy = crossOverPolicyBin; break;
		case InSet: crossOverPolicy = crossOverPolicyBin; break;		
		}
		Preconditions.checkState(crossOverPolicy!=null);
		return crossOverPolicy;
	}
	
	/**
	 * @param tipo El tipo del cromosoma
	 * @param problema El problema a resolver
	 * @return Un operador de mutación adecuado para un cromosoma del tipo indicado
	 */
	public static MutationPolicy getMutationPolicy(ChromosomeType tipo, ProblemAG problema){
		MutationPolicy mutationPolicy = null;
		switch(tipo){
		case Binary:  mutationPolicy = new BinaryMutation()	; break;
		case SubList: mutationPolicy = new BinaryMutation(); break;
		case Range: mutationPolicy = new BinaryMutation();	; break;
		case Permutation: mutationPolicy = new RandomKeyMutation(); break;
		case PermutationSubList: mutationPolicy = new SubListMutationPolicy(); break;
		case Real: mutationPolicy = new BinaryMutation();	; break;
		case InSet: mutationPolicy = new BinaryMutation(); break;
		}
		Preconditions.checkState(mutationPolicy!=null);
		return mutationPolicy;
	}
	
	/**
	 * <p> Para aplicar los operadores de mutación se escogen dos cromosomas. 
	 * La técnica implementada para escoger cada uno de los dos cromosomas se denomina selección por torneo. 
	 * Se trata de organizar dos torneos. 
	 * En cada uno se elige el mejor cromosoma de entrre <code> TOURNAMENT_ARITY </code> cromosomas de la población seleccionados al azar. 
	 * Si el tamaño de <code> TOURNAMENT_ARITY </code> es más grande, los cromosomas
	 *  débiles tienen menor probabilidad de ser seleccionados.</p>
	 * 
	 * <p> Número de participantes en el torneo para elegir los cromosomas que participarán en el cruce </p>
	 * <p> Un valor típico es 2 </p>
	 */
	
	public static int TOURNAMENT_ARITY = 2;
	
	/**
	 * @return Un operador que implementa la política de selección escogida
	 */
	public static SelectionPolicy getSelectionPolicy(){	
		SelectionPolicy selectionPolicy = new TournamentSelection(TOURNAMENT_ARITY);
		Preconditions.checkState(selectionPolicy!=null);
		return selectionPolicy;
	}
	
	/**
	 * @param tipo Tipo de cromosoma
	 * @param problema El problema a resolver 
	 * @post El método inicializa los parámetros relevantes de la clase que implementa el tipo indicado de cromosoma
	 */
	public static void iniValues(ChromosomeType tipo, ProblemAG problema){
		switch(tipo){
		case Binary: BinaryChromosome.iniValues(problema);break;
		case SubList: SubListChromosome.iniValues(problema);break;
		case Range: RangeChromosome.iniValues(problema); break;
		case Permutation: PermutationChromosome.iniValues(problema);break;
		case PermutationSubList: PermutationSubListChromosome.iniValues(problema);break;
		case Real: DoubleChromosome.iniValues(problema);break;
		case InSet: ValuesInSetChromosome.iniValues(problema); break;
		}
	}
	
	/**
	 * @param <E> El tipo de los elementos del cromosoma
	 * @pre Es un ValuesInRangeChromosome
	 * @param cr Un cromosoma instancia de la clase Chromosome de Apache.
	 * @return Un cromosoma de tipo ValuesInRangeChromosome
	 */
	@SuppressWarnings("unchecked")
	public static <E> ValuesInRangeChromosome<E> asValuesInRange(Chromosome cr){
		Preconditions.checkArgument(cr instanceof ValuesInRangeChromosome);
		return (ValuesInRangeChromosome<E>) cr;
	}
	
	/**
	 * @pre Es un IndexChromosome
	 * @param cr Un cromosoma instancia de la clase Chromosome de Apache.
	 * @return Un cromosoma de tipo IndexChromosome
	 */
	public static SeqNomalChromosome asIndex(Chromosome cr){
		Preconditions.checkArgument(cr instanceof SeqNomalChromosome);
		return (SeqNomalChromosome) cr;
	}
	
	
	/**
	 * @pre Es un ValuesInSetChromosome
	 * @param cr Un cromosoma instancia de la clase Chromosome de Apache.
	 * @return Un cromosoma de tipo ValuesInSetChromosome
	 */
	
	
	public static  ValuesInSetChromosome asValuesInSetChromosome(Chromosome cr) {
		Preconditions.checkArgument(cr instanceof ValuesInSetChromosome);
		return (ValuesInSetChromosome) cr;
	}
}
