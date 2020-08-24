package us.lsi.sa;

import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.MutationPolicy;

import us.lsi.ag.ProblemAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class StateSaChromosome implements StateSa {

	public static StateSaChromosome of(Chromosome chromosome, ChromosomeType type, MutationPolicy mutationPolicy) {
		return new StateSaChromosome(chromosome, type, mutationPolicy);
	}
	
	public static StateSaChromosome random(ProblemAG p, ChromosomeType type) {
		ChromosomeFactory.iniValues(type, p);
		MutationPolicy mutationPolicy = ChromosomeFactory.getMutationPolicy(type, p);
		org.apache.commons.math3.genetics.Chromosome chromosome = ChromosomeFactory.randomChromosome(type);
		return new StateSaChromosome(chromosome, type, mutationPolicy);
	}
	
	public final org.apache.commons.math3.genetics.Chromosome chromosome;
	private ChromosomeFactory.ChromosomeType type;
	private MutationPolicy mutationPolicy;
	
	private StateSaChromosome(Chromosome chromosome, ChromosomeType type, MutationPolicy mutationPolicy) {
		super();
		this.chromosome = chromosome;
		this.type = type;
		this.mutationPolicy = mutationPolicy;
	}
	
	@Override
	public Double fitness() {
		return -this.chromosome.fitness();
	}
	@Override
	public StateSa mutate() {
		return StateSaChromosome.of(this.mutationPolicy.mutate(this.chromosome),this.type,this.mutationPolicy);
	}

	@Override
	public StateSa random() {
		return StateSaChromosome.of(ChromosomeFactory.randomChromosome(this.type), this.type,this.mutationPolicy);
	}

	@Override
	public StateSa copy() {
		return StateSaChromosome.of(this.chromosome, this.type,this.mutationPolicy);
	}
	
	

}
