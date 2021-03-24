package us.lsi.ag;

import java.util.List;

public interface BlocksData<S> extends Data {
	
	
	/**
	 * @param cr Un cromosoma
	 * @return La función de fitnes del cromosoma
	 */
	Double fitnessFunction(List<Integer> cr);
	
	/**
	 * @param cr Un cromosoma
	 * @return La solución definida por el cromosoma
	 */
	S getSolucion(List<Integer> cr);
	
	List<Integer> blocksLimits();
	List<Integer> initialValues();

}
