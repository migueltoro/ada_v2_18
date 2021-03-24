package us.lsi.sa;

//import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.MutationPolicy;

import us.lsi.ag.Chromosome;
import us.lsi.ag.Data;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class StateSaChromosome extends org.apache.commons.math3.genetics.Chromosome implements StateSa {

	public static  StateSaChromosome of(Chromosome<?> chromosome, MutationPolicy mutationPolicy) {
		return new StateSaChromosome(chromosome, mutationPolicy);
	}
	
	public static StateSaChromosome random(Data data, ChromosomeType tipo) {
		ChromosomeFactory.iniValues(data,tipo);
		MutationPolicy mutationPolicy = ChromosomeFactory.getMutationPolicy(tipo);
		Chromosome<?> chr = ChromosomeFactory.randomChromosome(tipo);
		return new StateSaChromosome(chr, mutationPolicy);
	}
	
	public final Chromosome<?> chromosome;
	private ChromosomeFactory.ChromosomeType type;
	private MutationPolicy mutationPolicy;
	
	private StateSaChromosome(Chromosome<?> chromosome, MutationPolicy mutationPolicy) {
		super();
		this.chromosome = chromosome;
		this.mutationPolicy = mutationPolicy;
		
	}
	
	@Override
	public double fitness() {
		return -this.chromosome.fitness();
	}
	@Override
	public StateSa mutate() {
		return StateSaChromosome.of((Chromosome<?>) this.mutationPolicy.mutate(this),this.mutationPolicy);
	}

	@Override
	public StateSa random() {
		return StateSaChromosome.of(ChromosomeFactory.randomChromosome(this.type),this.mutationPolicy);
	}

	@Override
	public StateSa copy() {
		return StateSaChromosome.of(this.chromosome,this.mutationPolicy);
	}
	
	public Object decode() {
		return this.chromosome.decode();
	}
	
}
