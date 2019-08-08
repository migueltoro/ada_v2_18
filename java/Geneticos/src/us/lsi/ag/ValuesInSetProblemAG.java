package us.lsi.ag;

import java.util.List;

import us.lsi.ag.agchromosomes.ValuesInSetChromosome;


/**
 * @author Miguel Toro
 *
 * @param <S> El tipo de solución del problema
 */
public interface ValuesInSetProblemAG<S> extends ProblemAG {
	
	/**
	 * @return Número de variables.
	 */
	Integer getVariableNumber();
	
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
	
	Double fitnessFunction(ValuesInSetChromosome cr);
	
	/**
	 * @param cr Un cromosoma
	 * @return La solución definida por el cromosoma
	 */
	S getSolucion(ValuesInSetChromosome cr);

}
