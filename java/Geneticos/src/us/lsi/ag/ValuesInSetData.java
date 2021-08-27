package us.lsi.ag;

import java.util.List;


/**
 * @author Miguel Toro
 *
 * @param <S> El tipo de soluci�n del problema
 */
public interface ValuesInSetData<S> extends ChromosomeData<List<Integer>,S> {
	
	/**
	 * @pre 0 &le; i &lt; getVariableNumber()
	 * @param i Un entero 
	 * @return El conjunto de valores de la variable i
	 */
	List<Integer> values(Integer i);

}
