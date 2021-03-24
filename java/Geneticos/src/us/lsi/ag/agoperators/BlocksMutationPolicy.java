package us.lsi.ag.agoperators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.MutationPolicy;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.BlocksChromosome;
import us.lsi.common.Preconditions;

public class BlocksMutationPolicy implements MutationPolicy {
	
	public BlocksMutationPolicy() {
		super();
	}

	@Override
	public Chromosome mutate(Chromosome cr) throws MathIllegalArgumentException {
		Preconditions.checkArgument(cr instanceof BlocksChromosome, 
				"El cromosoma no es una instancia de AbstractListChromosome");
		BlocksChromosome c = (BlocksChromosome) cr;
		List<Integer> r = new ArrayList<>(c.decode());
		Integer rn = r.size();
		List<Integer> p = c.blocksLimits();
		Integer pn = p.size();
		Collections.shuffle(r.subList(0,p.get(0)),AlgoritmoAG.random);
		for(int i=0; i<pn-1;i++) {
			Collections.shuffle(r.subList(p.get(i),p.get(i+1)),AlgoritmoAG.random);
		}
		Collections.shuffle(r.subList(p.get(pn-1),rn),AlgoritmoAG.random);
		return BlocksChromosome.of(r);
	}

}
