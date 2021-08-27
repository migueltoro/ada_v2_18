package us.lsi.ag.agchromosomes;

import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import us.lsi.ag.Chromosome;
import us.lsi.ag.SeqNormalData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.common.Preconditions;
import us.lsi.common.List2;

/**
 * @author Miguel Toro
 * 
 * * <p> Una implementación del tipo IndexChromosome. Toma como información la definición de un problema que implementa el interfaz IndexProblemAG.
 * A partir de esa información construye una secuencia normal. 
 * Asumimos que el número de objetos es <code>n </code>y el tamaño de la secuencia normal <code>r</code>. 
 * La lista decodificada es un subconjunto no permutado de la secuencia normal.</p>
 *  
 * <p> La lista decodificada está formada por una lista de  tamaño <code>r</code>, cuyos valores son 
 * índices en el rango <code> [0,n-1]</code>, y cada índice <code>i</code> se  repite un número de veces igual al 
 * dado por la multiplicidad máxima del objeto <code> i </code>
 * definida en el problema. </p>
 * 
 * <p> La implementación usa un cromosoma binario de tamaño <code> r </code>.
 * Es un cromosoma adecuado para codificar problemas de multiconjuntos.
 **/

public class IndexSubListChromosome extends BinaryChromosome 
		implements SeqNormalData<Object>, Chromosome<List<Integer>> {

	
	public static List<Integer> normalSequence;
	public static SeqNormalData<Object> data;
	
	/**
	 * Dimensión del cromosoma
	 */
	
	public static int DIMENSION;
	
	public static void iniValues(SeqNormalData<Object> data){
		IndexSubListChromosome.data =  data; 
		IndexSubListChromosome.normalSequence = IndexSubListChromosome.data.getNormalSequence();
		IndexSubListChromosome.DIMENSION = IndexSubListChromosome.normalSequence.size();
	}
	
	public IndexSubListChromosome(List<Integer> representation)
			throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public IndexSubListChromosome(Integer[] representation)
			throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ls) {
		return new IndexSubListChromosome(ls);
	}

	/**
	 * @return Una lista de enteros obtenida filtrando la secuencia normal para incluir 
	 * sólo los seleccionados por el cromosoma binario 
	 */
	@Override
	public List<Integer> decode() {	
		List<Integer> r = List2.empty();
		List<Integer> bn = this.getRepresentation();
		Preconditions.checkArgument(normalSequence.size() == bn.size(),normalSequence.size()+","+bn.size());
		for (int i = 0; i < normalSequence.size(); i++) {
			if (bn.get(i) == 1) {
				r.add(normalSequence.get(i));
			}
		}
		return r;
	}
	
	public static IndexSubListChromosome getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(IndexSubListChromosome.DIMENSION);
		return new IndexSubListChromosome(ls);
	}

	@Override
	public double fitness() {
		return ft;
	}
	
	private Double ft = null;
	
	private double calculateFt(){
		return IndexSubListChromosome.data.fitnessFunction(this.decode());
	}


	public Integer getObjectsNumber() {
		return IndexSubListChromosome.data.size();
	}

	public Integer getMax(Integer i) {
		return IndexSubListChromosome.data.getMaxMultiplicity(i);
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeFactory.ChromosomeType.IndexSubList;
	}

	@Override
	public Integer size() {
		return IndexSubListChromosome.data.size();
	}

	@Override
	public Double fitnessFunction(List<Integer> dc) {
		return IndexSubListChromosome.data.fitnessFunction(dc);
	}

	@Override
	public Object getSolucion(List<Integer> dc) {
		return IndexSubListChromosome.data.getSolucion(dc);
	}	
	
}