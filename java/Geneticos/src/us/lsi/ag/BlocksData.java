package us.lsi.ag;

import java.util.List;

public interface BlocksData<S> extends ChromosomeData<List<Integer>,S> {
	
	List<Integer> blocksLimits();
	List<Integer> initialValues();

}
