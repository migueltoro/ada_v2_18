package us.lsi.ag.agchromosomes;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import us.lsi.ag.Chromosome;
import us.lsi.ag.Data;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.math.Math2;

/**
 * @author Miguel Toro
 * 
 * 
 * <p> Una implementación del tipo ValuesInRangeCromosome&lt;Integer&gt;. Toma como información la definición de un problema que implementa el interfaz 
 * ValuesInRangeProblemAG. </p>
 * 
 * <p> Asumimos que el número de varibles es n. La lista decodificada está formada por una lista de  
 * enteros de tamaño n cuyos elementos para cada i son 
 * valores en en rango [getMin(i),getMax(i)]. </p>
 * 
 * <p> La implementación usa un cromosoma binario del tamaño n*nbits. 
 * Siendo nbits el número de bits usados para representar cada uno de los enteros. </p>
 * 
 * <p> Es un cromosoma adecuado para codificar problemas de subconjuntos de multiconjuntos</p>
 *
 */
public class RangeChromosome extends BinaryChromosome 
             implements ValuesInRangeData<Integer,Object>, Chromosome<List<Integer>> {
	
	/**
	 * Número de bits usado para representar un entero. El rango de enteros que podemos obtener dependerá de este número de bits.
	 */
	public static Integer bitsNumber = 10;
	
	public static ValuesInRangeData<Integer,Object> data;
	
	/**
	 * Dimensión del cromosoma igual a bitsNumber*getVariableNumber()
	 */
	
	public static int DIMENSION;
	
	@SuppressWarnings("unchecked")
	public static void iniValues(Data data){
		RangeChromosome.data = (ValuesInRangeData<Integer,Object>) data; 
		RangeChromosome.DIMENSION = RangeChromosome.bitsNumber*RangeChromosome.data.size();
	}
	
	private static Integer pow = Math2.pow(2., bitsNumber).intValue();
	
	public RangeChromosome(Integer[] representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public RangeChromosome(List<Integer> representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ls) {
		return new RangeChromosome(ls);
	}
	
	public List<Integer> decode() {
		List<Integer> ls = super.getRepresentation();
		List<Integer> r = new ArrayList<Integer>();
		int index1 = 0;
		for(int i = 0; i < this.size(); i++){			
			int index2 = index1+bitsNumber;
			Integer e = Math2.decode(ls.subList(index1, index2));
			Integer d = getMin(i)+Math2.escala(e, pow, getMax(i)-getMin(i));
			r.add(d);
			index1 = index1+bitsNumber;;
		}
		return r;
	}

	public List<Integer> getRepresentation(){
		return super.getRepresentation();
	}
	
	public static RangeChromosome getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(RangeChromosome.DIMENSION);
		return new RangeChromosome(ls);
	}

	@Override
	public double fitness() {
		return ft;
	}
	
	private double ft;
	
	protected double calculateFt(){
		return RangeChromosome.data.fitnessFunction(this.decode());
	}

	@Override
	public Integer getMax(Integer i) {
		return RangeChromosome.data.getMax(i);
	}

	@Override
	public Integer getMin(Integer i) {
		return RangeChromosome.data.getMin(i);
	}

	@Override
	public ChromosomeType getType() {
		return ChromosomeFactory.ChromosomeType.Range;
	}

	@Override
	public Integer size() {
		return RangeChromosome.data.size();
	}

	@Override
	public Double fitnessFunction(List<Integer> dc) {
		return RangeChromosome.data.fitnessFunction(dc);
	}

	@Override
	public Object getSolucion(List<Integer> dc) {
		return RangeChromosome.data.getSolucion(dc);
	}
}
