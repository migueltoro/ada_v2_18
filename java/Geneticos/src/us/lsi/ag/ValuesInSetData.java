package us.lsi.ag;

import java.util.List;


/**
 * @author Miguel Toro
 *
 * @param <S> El tipo de solución del problema
 */
public interface ValuesInSetData<S> extends Data {
	
	/**
	 * @return Número de variables.
	 */
	Integer size();
	
	/**
	 * @pre 0 &le; i &lt; getVariableNumber()
	 * @param i Un entero 
	 * @return El conjunto de valores de la variable i
	 */
	List<Integer> values(Integer i);
	
	/**
	 * @param cr Un cromosoma
	 * @return La función de fitness del cromosoma
	 */
	
	Double fitnessFunction(List<Integer> cr);
	
	/**
	 * @param cr Un cromosoma
	 * @return La solución definida por el cromosoma
	 */
	S getSolucion(List<Integer> cr);

}
