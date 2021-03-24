package us.lsi.ag.agchromosomes;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import us.lsi.ag.Chromosome;
import us.lsi.ag.Data;
import us.lsi.ag.ValuesInSetData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.math.Math2;

public class ValuesInSetChromosomeC extends BinaryChromosome 
       implements ValuesInSetData<Object>,Chromosome<List<Integer>> {
	
	/**
	 * Número de bits usado para representar un entero. El rango de enteros que podemos obtener dependerá de este número de bits.
	 */
	public static Integer bitsNumber = 10;
	public static ValuesInSetData<Object> data;
	
	/**
	 * Dimensión del cromosoma igual a bitsNumber*getVariableNumber()
	 */
	
	public static int DIMENSION;
	
	@SuppressWarnings("unchecked")
	public static void iniValues(Data data){
		ValuesInSetChromosomeC.data = (ValuesInSetData<Object>) data; 
		ValuesInSetChromosomeC.DIMENSION = ValuesInSetChromosomeC.bitsNumber*ValuesInSetChromosomeC.data.size();
	}
	
	private static Integer pow = Math2.pow(2., bitsNumber).intValue();
	
	public ValuesInSetChromosomeC(Integer[] representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public ValuesInSetChromosomeC(List<Integer> representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ls) {
		return new ValuesInSetChromosomeC(ls);
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
	
	public static ValuesInSetChromosomeC getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(ValuesInSetChromosomeC.DIMENSION);
		return new ValuesInSetChromosomeC(ls);
	}

	@Override
	public double fitness() {
		return ft;
	}
	
	private double ft;
	
	protected double calculateFt(){
		return ValuesInSetChromosomeC.data.fitnessFunction(this.decode());
	}

	public Integer getObjectsNumber() {
		return ValuesInSetChromosomeC.data.size();
	}

	private Integer getMax(int i) {
		return ValuesInSetChromosomeC.data.values(i).size();
	}

	private Integer getMin(int i) {
		return 0;
	}
	
	public List<Integer> values(int i){
		return ValuesInSetChromosomeC.data.values(i);
	}

	@Override
	public ChromosomeType getType() {
		return ChromosomeFactory.ChromosomeType.InSet;
	}

	@Override
	public Integer size() {
		return ValuesInSetChromosomeC.data.size();
	}

	@Override
	public List<Integer> values(Integer i) {
		return ValuesInSetChromosomeC.data.values(i);
	}

	@Override
	public Double fitnessFunction(List<Integer> dc) {
		return ValuesInSetChromosomeC.data.fitnessFunction(dc);
	}

	@Override
	public Object getSolucion(List<Integer> dc) {
		return ValuesInSetChromosomeC.data.getSolucion(dc);
	}
}
