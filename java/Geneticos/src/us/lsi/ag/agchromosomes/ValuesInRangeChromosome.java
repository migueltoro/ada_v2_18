package us.lsi.ag.agchromosomes;

import java.util.List;

import us.lsi.ag.ValuesInRangeProblemAG;

public interface ValuesInRangeChromosome<E> extends Chromosome<List<E>> {

	ValuesInRangeProblemAG<Integer,?> getProblem();

	E getMin(int i);

	E getMax(int i);

	Integer getObjectsNumber();
	
	
	
}
