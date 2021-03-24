package us.lsi.ag.agoperators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ChromosomePair;
import org.apache.commons.math3.genetics.CrossoverPolicy;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.BlocksChromosome;
import us.lsi.common.Pair;
import us.lsi.common.Preconditions;
import us.lsi.math.Math2;

public class BlocksCrossoverPolicy implements CrossoverPolicy {

	public BlocksCrossoverPolicy(){
		super();
	}
	
	private static Pair<List<Integer>,List<Integer>> crossList(List<Integer> ls0, List<Integer> ls1, Random rnd){
		Preconditions.checkArgument(ls0.size()==ls1.size() && ls0.size() > 0,"Las dos listas deben tener el mismo tamño");
		Integer n = ls0.size();
		Pair<List<Integer>,List<Integer>> r;
		if(n==1) r = Pair.of(ls1,ls0);
		else {
			Integer m = Math2.getEnteroAleatorio(1, n-1);
			List<Integer> r0 = new ArrayList<>(ls0.subList(0,m));
			r0.addAll(ls1.subList(m,n));
			List<Integer> r1 =  new ArrayList<>(ls1.subList(0,m));
			r1.addAll(ls0.subList(m,n));
			r = Pair.of(r0,r1);
		}
		return r;
	}
	
	@Override
	public ChromosomePair crossover(Chromosome cr0, Chromosome cr1) throws MathIllegalArgumentException {
		Preconditions.checkArgument(cr0 instanceof BlocksChromosome, 
				"El cromosoma no es una instancia de AbstractListChromosome");
		BlocksChromosome c0 = (BlocksChromosome) cr0;
		Preconditions.checkArgument(cr1 instanceof BlocksChromosome, 
				"El cromosoma no es una instancia de AbstractListChromosome");
		BlocksChromosome c1 = (BlocksChromosome) cr1;
		List<Integer> r0 = c0.decode();
		List<Integer> r1 = c1.decode();
		Integer rn = r0.size();
		List<Integer> lm = c0.blocksLimits();
		Integer lms = lm.size();
		List<Integer> s0 = new ArrayList<>();
		List<Integer> s1 = new ArrayList<>();
		Pair<List<Integer>,List<Integer>> p = crossList(r0.subList(0,lm.get(0)),r1.subList(0,lm.get(0)),AlgoritmoAG.random);
		s0.addAll(p.first);
		s1.addAll(p.second);
		for(int i=0; i<lms-1;i++) {
			p = crossList(r0.subList(lm.get(i),lm.get(i+1)),r1.subList(lm.get(i),lm.get(i+1)),AlgoritmoAG.random);
			s0.addAll(p.first);
			s1.addAll(p.second);
		}
		p = crossList(r0.subList(lm.get(lms-1),rn),r1.subList(lm.get(lms-1),rn),AlgoritmoAG.random);
		s0.addAll(p.first);
		s1.addAll(p.second);
		return new ChromosomePair(BlocksChromosome.of(s0),BlocksChromosome.of(s1));
	}

}
