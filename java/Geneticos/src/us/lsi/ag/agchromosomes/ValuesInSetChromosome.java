package us.lsi.ag.agchromosomes;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import us.lsi.ag.ProblemAG;
import us.lsi.ag.ValuesInSetProblemAG;
import us.lsi.math.Math2;

public class ValuesInSetChromosome extends BinaryChromosome implements Chromosome<List<Integer>> {
	
	/**
	 * Número de bits usado para representar un entero. El rango de enteros que podemos obtener dependerá de este número de bits.
	 */
	public static Integer bitsNumber = 10;
	
	public static ValuesInSetProblemAG<?> problem;
	
	/**
	 * Dimensión del cromosoma igual a bitsNumber*getVariableNumber()
	 */
	
	public static int DIMENSION;
	
	public static void iniValues(ProblemAG problema){
		ValuesInSetChromosome.problem = (ValuesInSetProblemAG<?>) problema; 
		ValuesInSetChromosome.DIMENSION = ValuesInSetChromosome.bitsNumber*ValuesInSetChromosome.problem.getVariableNumber();
	}
	
	private static Integer pow = Math2.pow(2., bitsNumber).intValue();
	
	public ValuesInSetChromosome(Integer[] representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public ValuesInSetChromosome(List<Integer> representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ls) {
		return new ValuesInSetChromosome(ls);
	}
	
	public List<Integer> decode() {
		List<Integer> ls = super.getRepresentation();
		List<Integer> r = new ArrayList<Integer>();
		int index1 = 0;
		for(int i = 0; i < getObjectsNumber(); i++){			
			int index2 = index1+bitsNumber;
			Integer e = Math2.decode(ls.subList(index1, index2));
			Integer d = getMin(i)+Math2.escala(e, pow, getMax(i)-getMin(i));
			r.add(values(i).get(d));
			index1 = index1+bitsNumber;
		}
		return r;
	}

	public List<Integer> getRepresentation(){
		return super.getRepresentation();
	}
	
	public static ValuesInSetChromosome getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(ValuesInSetChromosome.DIMENSION);
		return new ValuesInSetChromosome(ls);
	}

	@Override
	public double fitness() {
		return ft;
	}
	
	private double ft;
	
	protected double calculateFt(){
		return ValuesInSetChromosome.problem.fitnessFunction(this);
	}

	public Integer getObjectsNumber() {
		return ValuesInSetChromosome.problem.getVariableNumber();
	}

	private Integer getMax(int i) {
		return ValuesInSetChromosome.problem.values(i).size();
	}

	private Integer getMin(int i) {
		return 0;
	}
	
	public List<Integer> values(int i){
		return ValuesInSetChromosome.problem.values(i);
	}
	
	public ValuesInSetProblemAG<?> getProblem() {
		return ValuesInSetChromosome.problem;
	}
}
