package us.lsi.ag;

import java.util.List;

public interface ValuesInRangeChromosome<E> extends Chromosome<List<E>> {

	ValuesInRangeProblemAG<Integer,?> getProblem();

	E getMin(int i);

	E getMax(int i);

	Integer getObjectsNumber();
	
}
