package us.lsi.ag.agchromosomes;

import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;
import org.apache.commons.math3.genetics.RandomKey;

import us.lsi.ag.Chromosome;
import us.lsi.ag.Data;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;


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
public class DoubleChromosome extends RandomKey<Double>  
             implements ValuesInRangeData<Double,Object>, Chromosome<List<Double>> {
	
	public static ValuesInRangeData<Double,Object> data;
	
	/**
	 * Dimensión del cromosoma igual a bitsNumber*getVariableNumber()
	 */
	
	public static int DIMENSION;
	
	@SuppressWarnings("unchecked")
	public static void iniValues(Data data){
		DoubleChromosome.data = (ValuesInRangeData<Double,Object>) data; 
		DoubleChromosome.DIMENSION = DoubleChromosome.data.size();
	}
	
	public DoubleChromosome(Double[] representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public DoubleChromosome(List<Double> representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	@Override
	public AbstractListChromosome<Double> newFixedLengthChromosome(List<Double> ls) {
		return new RangeChromosome(ls);
	}
	
	private Double convert(Double e, Integer i) {
		return (this.getMin(i) + (this.getMax(i)-this.getMin(i))*e);
	}
	
	public List<Double> decode() {
		List<Double> ls = super.getRepresentation();
		return IntStream.range(0,ls.size()).boxed().map(i->this.convert(ls.get(i),i)).toList();
	}

	public List<Double> getRepresentation(){
		return super.getRepresentation();
	}
	
	public static DoubleChromosome getInitialChromosome() {
		List<Double> ls = RandomKey.randomPermutation(RangeChromosome.DIMENSION);
		return new DoubleChromosome(ls);
	}

	@Override
	public double fitness() {
		return ft;
	}
	
	private double ft;
	
	protected double calculateFt(){
		return DoubleChromosome.data.fitnessFunction(this.decode());
	}

	@Override
	public Double getMax(Integer i) {
		return DoubleChromosome.data.getMax(i);
	}

	@Override
	public Double getMin(Integer i) {
		return DoubleChromosome.data.getMin(i);
	}

	@Override
	public ChromosomeType getType() {
		return ChromosomeFactory.ChromosomeType.Real;
	}

	@Override
	public Integer size() {
		return DoubleChromosome.data.size();
	}

	@Override
	public Double fitnessFunction(List<Double> dc) {
		return DoubleChromosome.data.fitnessFunction(dc);
	}

	@Override
	public Object getSolucion(List<Double> dc) {
		return DoubleChromosome.data.getSolucion(dc);
	}

	
}


