package us.lsi.ag.agchromosomes;

import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import us.lsi.ag.Chromosome;
import us.lsi.ag.Data;
import us.lsi.ag.SeqNormalData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.common.Preconditions;

public class PermutationIntegerChromosome extends AbstractListChromosome<Integer> 
            implements SeqNormalData<Object>, Chromosome<List<Integer>>{


	public static List<Integer> normalSequence = null;
	public static SeqNormalData<Object> data;
	
	/**
	 * Dimensión del cromosoma
	 */
	
	public static int DIMENSION;
	
	@SuppressWarnings("unchecked")
	public static void iniValues(Data data){
		PermutationIntegerChromosome.data = (SeqNormalData<Object>) data; 
		PermutationIntegerChromosome.normalSequence = PermutationIntegerChromosome.data.getNormalSequence();
		PermutationIntegerChromosome.DIMENSION = PermutationIntegerChromosome.normalSequence.size();
	}

	public static PermutationIntegerChromosome of(List<Integer> representation) throws InvalidRepresentationException {
		return new PermutationIntegerChromosome(representation);
	}
	
	private PermutationIntegerChromosome(List<Integer> representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	
	public PermutationIntegerChromosome(Integer[] representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ls) {
		return of(ls);	
	}

	@Override
	public List<Integer> decode() {
		return this.getRepresentation();
	}	
	
	public static PermutationIntegerChromosome getInitialChromosome() {
		Preconditions.checkNotNull(PermutationIntegerChromosome.normalSequence,"La secuencia normal no puede ser null");
		List<Integer> ls = PermutationIntegerChromosome.normalSequence;
		Collections.shuffle(ls,AlgoritmoAG.random);
		return of(ls);
	}


	@Override
	public double fitness() {
		return ft;
	}
	
	private double ft;
	
	private double calculateFt(){
		return PermutationIntegerChromosome.data.fitnessFunction(this.decode());
	}

	@Override
	protected void checkValidity(List<Integer> arg0) throws InvalidRepresentationException {
		
	}

	@Override
	public Integer size() {
		return PermutationIntegerChromosome.data.size();
	}

	@Override
	public ChromosomeType getType() {
		return PermutationIntegerChromosome.data.getType();
	}

	@Override
	public Double fitnessFunction(List<Integer> cr) {
		return PermutationIntegerChromosome.data.fitnessFunction(cr);
	}

	@Override
	public Object getSolucion(List<Integer> cr) {
		return PermutationIntegerChromosome.data.getSolucion(cr);
	}

	
}
