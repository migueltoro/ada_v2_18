package us.lsi.ag;

import java.util.List;

public interface ValuesInRangeData<E,S> extends Data {

	/**
	 * @return Numero de casillas del cromosoma
	 */
	Integer size();
	
	/**
	 * @pre 0 &le; i &lt; getVariableNumber()
	 * @param i Un entero 
	 * @return El máximo valor, sin incluir, del rango de valores de la variable i
	 */
	E getMax(Integer i);
	/**
	 * @pre 0 &le; i &lt; 
	 * @param i Un entero getVariableNumber()
	 * @return El mínimo valor del rango de valores de la variable i
	 */
	E getMin(Integer i);
	
	/**
	 * @param cr Un cromosoma
	 * @return La función de fitness del cromosoma
	 */
	
	Double fitnessFunction(List<E> cr);
	
	/**
	 * @param cr Un cromosoma
	 * @return La solución definida por el cromosoma
	 */
	S getSolucion(List<E> cr);
}
