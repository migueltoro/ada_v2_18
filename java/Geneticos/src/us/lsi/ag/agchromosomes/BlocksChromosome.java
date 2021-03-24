package us.lsi.ag.agchromosomes;

import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import us.lsi.ag.BlocksData;
import us.lsi.ag.Chromosome;
import us.lsi.ag.Data;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class BlocksChromosome extends AbstractListChromosome<Integer> 
			implements BlocksData<Object>, Chromosome<List<Integer>> {

	public static BlocksData<Object> data;
	public static int DIMENSION;
	
	@SuppressWarnings("unchecked")
	public static void iniValues(Data data){
		BlocksChromosome.data = (BlocksData<Object>) data; 
		BlocksChromosome.DIMENSION = BlocksChromosome.data.size();
	}
	
	public static BlocksChromosome getInitialChromosome() {
		List<Integer> ls = BlocksChromosome.data.initialValues();
		BlocksChromosome cr = BlocksChromosome.of(ls);
		return (BlocksChromosome) AlgoritmoAG.mutationPolicy.mutate(cr);
	}
	
	public static BlocksChromosome of(List<Integer> representation) throws InvalidRepresentationException {
		return new BlocksChromosome(representation);
	}

	public static <S> BlocksChromosome of(Integer[] representation) throws InvalidRepresentationException {
		return new BlocksChromosome(representation);
	}

	private BlocksChromosome(Integer[] representation) throws InvalidRepresentationException {
		super(representation);
	}
	
	private BlocksChromosome(List<Integer> representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}
	
	@Override
	public List<Integer> decode() {
		return this.getRepresentation();
	}	
	
	private double ft;
	
	private double calculateFt(){
		return BlocksChromosome.data.fitnessFunction(this.decode());
	}


	@Override
	public double fitness() {
		return ft;
	}

	@Override
	protected void checkValidity(List<Integer> arg0) throws InvalidRepresentationException {
		
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ls) {
		return BlocksChromosome.of(ls);
	}
	
	@Override
	public Double fitnessFunction(List<Integer> cr) {
		return BlocksChromosome.data.fitnessFunction(cr);
	}

	@Override
	public Object getSolucion(List<Integer> cr) {
		return BlocksChromosome.data.getSolucion(cr);
	}

	@Override
	public Integer size() {
		return BlocksChromosome.data.size();
	}

	@Override
	public ChromosomeType getType() {
		return BlocksChromosome.data.getType();
	}

	@Override
	public List<Integer> blocksLimits() {
		return BlocksChromosome.data.blocksLimits();
	}

	@Override
	public List<Integer> initialValues() {
		return BlocksChromosome.data.initialValues();
	}

	

}
