package us.lsi.ag.agchromosomes;

import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import us.lsi.ag.ProblemAG;
import us.lsi.ag.ValuesInRangeProblemAG;

/**
 * @author Miguel Toro
 * 
 * <p> 
 * Un cromosoma cuya valor decodificado es una lista de ceros y unos del tamaño especificado en el problema.
 * La implementación es una adaptación de la clase {@link org.apache.commons.math3.genetics.Chromosome Chromosome} de Apache. </p>
 *
 */
public class BinaryChromosome extends org.apache.commons.math3.genetics.BinaryChromosome implements ValuesInRangeChromosome<Integer> {
	
	public static ValuesInRangeProblemAG<Integer,?> problem;
	
	/**
	 * Dimensión del cromosoma
	 */
	
	protected static int DIMENSION;
	
	@SuppressWarnings("unchecked")
	public static void iniValues(ProblemAG problema){
		BinaryChromosome.problem = (ValuesInRangeProblemAG<Integer,?>) problema; 
		BinaryChromosome.DIMENSION = BinaryChromosome.problem.getCellsNumber();
	}

	public BinaryChromosome(List<Integer> representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public BinaryChromosome(Integer[] representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	@Override
	public List<Integer> decode() {
		return getRepresentation();
	}

	@Override
	public double fitness() {
		return ft;
	}
	
	private Double ft = null;
	
	private double calculateFt(){
		return BinaryChromosome.problem.fitnessFunction(this);
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ar) {
		return new BinaryChromosome(ar);
	}

	public ValuesInRangeProblemAG<Integer,?> getProblem() {
		return problem;
	}

	@Override
	public Integer getMin(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getMax(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getObjectsNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	public static int getDimension() {
		return BinaryChromosome.problem.getCellsNumber();
	}
	
	public static BinaryChromosome getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(BinaryChromosome.getDimension());
		return new BinaryChromosome(ls);
	}

}
