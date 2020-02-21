package us.lsi.pli;

import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class HelpPLI {
	
	
	/**
	 * @param n El rango del sumatorio
	 * @param varId El identificador de la variable
	 * @param end La parte derecha de la restrcci&oacute;n
	 * @return Genera varId_0+varId_0+... end
	 */
	public static String sum(int n, String varId, String end) {
		return IntStream.range(0, n).boxed().map(i -> String.format("%s_%d", varId,i))
				.collect(Collectors.joining("+", "", end));
	}
	
	/**
	 * @param n El rango del sumatorio en la i
	 * @param j Un valor fijo para j
	 * @param varId El identificador de la variable
	 * @param end La parte derecha de la restrcci&oacute;n
	 * @return Genera varId_0_j+varId_1_j+... end
	 */
	public static String sum_i(int j, int n, String varId, String end) {
		return IntStream.range(0, n).boxed().map(i -> String.format("%s_%d_%d", varId,i,j))
				.collect(Collectors.joining("+", "", end));
	}
	/**
	 * @param n El rango del sumatorio en la i
	 * @param i Un valor fijo para i
	 * @param varId El identificador de la variable
	 * @param end La parte derecha de la restrcci&oacute;n
	 * @return Genera varId_i_0+varId_i_1+... end
	 */
	public static String sum_j(int i, int n, String varId, String end) {
		return IntStream.range(0, n).boxed().map(j -> String.format("%s_%d_%d",varId,i,j))
				.collect(Collectors.joining("+", "", end));
	}

}
