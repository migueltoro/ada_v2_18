package us.lsi.ag.agoperators;

import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.MutationPolicy;

import us.lsi.ag.agchromosomes.PermutationIntegerChromosome;
import us.lsi.common.IntPair;
import us.lsi.common.Lists2;
import us.lsi.common.Preconditions;
import us.lsi.math.Math2;

public class PermutationIntegerMutationPolicy implements MutationPolicy {
	
	public PermutationIntegerMutationPolicy() {
		super();
	}

	@Override
	public Chromosome mutate(Chromosome cr) throws MathIllegalArgumentException {
		Preconditions.checkArgument(cr instanceof PermutationIntegerChromosome, 
				"El cromosoma no es una instancia de AbstractListChromosome");
		PermutationIntegerChromosome c = (PermutationIntegerChromosome) cr;
		List<Integer> r = c.decode();
		Integer n = c.getLength();
		IntPair p = Math2.getParAleatorioYDistinto(0, n);
		Integer i = p.first;
		Integer j = p.second;
		List<Integer> ls1 = Lists2.copy(r.subList(0,i));
		List<Integer> ls2 = Lists2.copy(r.subList(i,j));
		Collections.reverse(ls2);
		List<Integer> ls3 = Lists2.copy(r.subList(j,n));
		ls1.addAll(ls2);
		ls1.addAll(ls3);	
		return PermutationIntegerChromosome.of(r);
	}

}
