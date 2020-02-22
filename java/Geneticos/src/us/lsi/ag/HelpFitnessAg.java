package us.lsi.ag;

import java.util.List;
import java.util.stream.Collectors;

public class HelpFitnessAg {
	
	/**
	 * @param in Valor que tiene que cumplir la condic&oacute;n in &lt; 0.
	 * @return in*in si in no cumple la condic&oacute;n, si la cumple cero
	 */
	public static Double menorOIgualACero(Double in) {
		Double r = 0.;		
		if(in > 0) {
			r = in*in;
		}
		return r;
	}
	
	/**
	 * @param in Valor que tiene que cumplir la condic&oacute;n in &gt; 0.
	 * @return in*in si in no cumple la condic&oacute;n, si la cumple cero
	 */
	public static Double mayorOIgualACero(Double in) {
		Double r = 0.;		
		if(in < 0) {
			r = in*in;
		}
		return r;
	}
	
	/**
	 * @param in Valor que tiene que cumplir la condic&oacute;n in = 0.
	 * @return in*in
	 */
	public static Double igualACero(Double in) {
		return in*in;
	}
	
	/**
	 * @param ls Una lista 
	 * @return Genera el cuadrdao de la diferencia entre el n&uacute;mero de el elementos diferentes y el de la lista
	 */
	public static <E> Double allDifferents(List<E> ls) {
		Integer n = ls.size();
		Integer m = ls.stream().collect(Collectors.toSet()).size();
		return (double)(n-m)*(n-m);
	}

}
