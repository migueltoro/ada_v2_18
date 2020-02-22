package us.lsi.pli;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.IntPair;
import us.lsi.common.Streams2;


public class HelpPLI {
	
	public static Locale locale = new Locale("en", "US");
	
	
	/**
	 * @param n El rango del sumatorio
	 * @param id El identificador de la variable
	 * @param right La parte derecha de la restrcci&oacute;n: " >= 1; \n", etc.
	 * @return Genera varId_0+varId_0+... end
	 */
	public static String sum_1(int n, String id, String right) {
		return IntStream.range(0, n).boxed().map(i -> String.format("%s_%d", id,i))
				.collect(Collectors.joining("+", "", right));
	}
	
	/**
	 * @param n El rango del sumatorio
	 * @param p Un predicado que filtra los &iacute;ndices
	 * @param id El identificador de la variable
	 * @param right La parte derecha de la restrcci&oacute;n: " >= 1; \n", etc.
	 * @return Genera varId_0+varId_0+... end
	 */
	public static String sum_1(int n, Predicate<Integer> p, String id, String right) {
		return IntStream.range(0, n).boxed().map(i -> String.format("%s_%d", id,i))
				.collect(Collectors.joining("+", "", right));
	}
	
	/**
	 * @param n El rango del sumatorio
	 * @param p Un predicado que filtra los &iacute;ndices
	 * @param coef Los coeficientes
	 * @param id El identificador de la variable
	 * @param right La parte derecha de la restrcci&oacute;n: " >= 1; \n", etc.
	 * @return Genera c0*id_0+c1*id_1+... right
	 */
	public static String sum_1(int n, Predicate<Integer> p, Function<Integer,Double> coef, String id, String right) {
		return IntStream.range(0, n).boxed()
				.map(i -> String.format(locale,"%.2f*%s_%d",coef.apply(i),id,i))
				.collect(Collectors.joining("+", "", right));
	}
	
	/**
	 * @param n El rango del sumatorio en la i
	 * @param j Un valor fijo para j
	 * @param id El identificador de la variable
	 * @param right La parte derecha de la restrcci&oacute;n: " >= 1; \n", etc.
	 * @return Genera varId_0_j+varId_1_j+... end
	 */
	public static String sum_2_i(int j, int n, String id, String right) {
		return IntStream.range(0, n).boxed().map(i -> String.format("%s_%d_%d", id,i,j))
				.collect(Collectors.joining("+", "", right));
	}
	/**
	 * @param n El rango del sumatorio en la i
	 * @param i Un valor fijo para i
	 * @param id El identificador de la variable
	 * @param end La parte derecha de la restrcci&oacute;n: " >= 1; \n", etc.
	 * @return Genera varId_i_0+varId_i_1+... end
	 */
	public static String sum_2_j(int i, int n, String id, String end) {
		return IntStream.range(0, n).boxed().map(j -> String.format("%s_%d_%d",id,i,j))
				.collect(Collectors.joining("+", "", end));
	}
	
	/**
	 * @param n El rango del &iacute;ndice i
	 * @param m El rango del &iacute;ndice j
	 * @param id El identificador de la variable
	 * @param right La parte derecha de la restrcci&oacute;n: " >= 1; \n", etc.
	 * @return Genera varId_i_0+varId_i_1+... end
	 */
	public static String sum_2(int n, int m, String id, String right) {
		return Streams2.allPairs(n, m)
				.map(p -> String.format("%s_%d_%d",id,p.a,p.b))
				.collect(Collectors.joining("+", "", right));
	}
	
	/**
	 * @param n El rango del &iacute;ndice i
	 * @param m El rango del &iacute;ndice j
	 * @param p Un predicado par filtrar pares de &iacute;ndices
	 * @param id El identificador de la variable
	 * @param right La parte derecha de la restrcci&oacute;n: " >= 1; \n", etc.
	 * @return Genera id_0_0+id_0_1+... end
	 */
	public static String sum_2(int n, int m, Predicate<IntPair> pd, String id, String right) {
		return Streams2.allPairs(n, m)
				.filter(pd)
				.map(p -> String.format("%s_%d_%d",id,p.a,p.b))
				.collect(Collectors.joining("+", "", right));
	}
		
	/**
	 * @param n El rango del &iacute;ndice i
	 * @param m El rango del &iacute;ndice j
	 * @param p Un predicado par filtrar pares de &iacute;ndices
	 * @param coef Los coeficientes
	 * @param id El identificador de la variable
	 * @param right La parte derecha de la restrcci&oacute;n: " >= 1; \n", etc.
	 * @return Genera id_0_0+id_0_1+... end
	 */
	public static String sum_2(int n, int m, Predicate<IntPair> pd, Function<IntPair,Double> coef, String id, String right) {
		return Streams2.allPairs(n, m)
				.filter(pd)
				.map(p -> String.format(locale,"%.2f*%s_%d_%d",coef.apply(p),id,p.a,p.b))
				.collect(Collectors.joining("+", "", right));
	}
	/**
	 * @param n El rango del &iacute;ndice i
	 * @param m El rango del &iacute;ndice j
	 * @param id El identificador de la variable
	 * @param tipo El tipo de la variable: bin, int, ...
	 * @return La declaraci&oacute;n de esas variables de dos sub&iacute;ndices
	 */
	public static String tipoVariables_2(int n, int m, String id, String tipo) {
		return Streams2.allPairs(n, m).map(p -> String.format("%s_%d_%d", id, p.a, p.b))
				.collect(Collectors.joining(",", tipo+" ", "; \n"));
	}
	
	/**
	 * @param n El rango del &iacute;ndice i
	 * @param id El identificador de la variable
	 * @param tipo El tipo de las variables: bin, int, ...
	 * @return La declaraci&oacute;n de esas variables de un sub&iacute;ndice
	 */
	public static String tipoVariables_1(int n, String id, String tipo) {
		return IntStream.range(0,n).boxed().map(i -> String.format("%s_%d", tipo, i))
				.collect(Collectors.joining(",", tipo, "; \n"));
	}
	
	public static String constraints(int n, int m, String id, String right) {
		return IntStream.range(0, n).boxed()
			.map(i -> HelpPLI.sum_2_j(i,n,id,right))
			.collect(Collectors.joining("", "", "\n"));
	}
}
