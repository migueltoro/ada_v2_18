package us.lsi.ag.agchromosomes;

import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;
import org.apache.commons.math3.genetics.RandomKey;

import us.lsi.ag.SeqNomalChromosome;
import us.lsi.ag.ProblemAG;
import us.lsi.ag.SeqNormalProblemAG;


import us.lsi.common.Preconditions;

/**
 * @author Miguel Toro
 * 
 * <p> Una implementación del tipo IndexChromosome. Toma como información la definición de un problema que implementa el interfaz IndexProblemAG.
 * A partir de esa información construye una secuencia normal. 
 * Asumimos que el número de objetos es <code>n </code>y el tamaño de la secuencia normal <code>r</code>. 
 * La lista decodificada es una permutación de la secuencia normal.</p>
 *  
 * <p> La lista decodificada está formada por una lista de  tamaño <code>r</code>, cuyos valores son 
 * enteros en el rango <code> [0,n-1]</code>, y cada índice <code>i</code> se  repite un número de veces igual al 
 * dado por la multiplicidad máxima del objeto <code> i </code>
 * definida en el problema. </p>
 * 
 * <p> La implementación usa un cromosoma de clave aleatoria de tamaño <code> r </code>.
 * Es un cromosoma adecuado para codificar problemas de permutaciones </p>
 *
 */
public class PermutationChromosome extends RandomKey<Integer> implements SeqNomalChromosome {

	public static List<Integer> normalSequence = null;
	public static SeqNormalProblemAG<?> problema;
	
	/**
	 * Dimensión del cromosoma
	 */
	
	public static int DIMENSION;
	
	public static void iniValues(ProblemAG problema){
		PermutationChromosome.problema = (SeqNormalProblemAG<?>) problema; 
		PermutationChromosome.normalSequence = PermutationChromosome.problema.getNormalSequence();
		PermutationChromosome.DIMENSION = PermutationChromosome.normalSequence.size();
	}

	
	public PermutationChromosome(List<Double> representation)
			throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public PermutationChromosome(Double[] representation)
			throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	@Override
	public AbstractListChromosome<Double> newFixedLengthChromosome(List<Double> ls) {
		return new PermutationChromosome(ls);
		
	}

	@Override
	public List<Integer> decode() {
		Preconditions.checkArgument(PermutationChromosome.normalSequence!=null);
		return this.decode(PermutationChromosome.normalSequence);
	}
	
	
	public static PermutationChromosome getInitialChromosome() {
		List<Double> ls = RandomKey.randomPermutation(PermutationChromosome.DIMENSION);
		return new PermutationChromosome(ls);
	}

	
	@Override
	public double fitness() {
		return ft;
	}
	
	private double ft;
	
	private double calculateFt(){
		return PermutationChromosome.problema.fitnessFunction(this);
	}

	@Override
	public SeqNormalProblemAG<?> getProblem() {
		return PermutationChromosome.problema;
	}

	public Integer getObjectsNumber() {
		return PermutationChromosome.problema.getObjectsNumber();
	}

	public Integer getMax(int i) {
		return PermutationChromosome.problema.getMaxMultiplicity(i);
	}
	
}