package us.lsi.iterativos;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;


import us.lsi.common.List2;

import org.apache.commons.math3.fraction.BigFraction;
import org.apache.commons.math3.fraction.BigFractionField;
import org.apache.commons.math3.linear.FieldMatrix;


public class EjemplosIterativos {

	/**
	 * @param n Un entero
	 * @param k Un entero
	 * @return Valor del número combinatorio n sobre k calculado de forma iterativa
	 */
	public static int binom(int n, int k) {
		List<Integer> lsa = Arrays.asList(1);
		int i = 1;
		while (i <= n) {
			List<Integer> ls = List2.empty();
			for (int s = 0; s <= i; s++) {
				if (s == 0 || s == i) {
					ls.add(1);
				} else if (s == 1 || s == i - 1) {
					ls.add(i);
				} else {
					ls.add(lsa.get(s - 1) + lsa.get(s));
				}
			}
			i = i + 1;
			lsa = List2.ofCollection(ls);
		}
		return lsa.get(k);
	}
	
	
	
	/**
	 * Adaptación del algoritmo anterior pot(b,n) para el cálculo de los número de Fibonacci
	 * 
	 * @param n Término
	 * @return Valor del n-esimo número de Fibonacci calculado de forma iterativa
	 */
	public static long fib(int n){
			int i = 0;
		    int a = 1;
			int b = 0;
			while(i < n){
				i = i+1;
				int a0 = a;
				a = a0+b;
				b = a0;
			}
			return b;
	}

	
	/**
	 * Adaptación del algoritmo anterior pot(b,n), siendo b, a, e de tipo Matriz. Alternativamente 
	 * se podria usar el método e.power(n) para calcular la potencia de la basea asociada a la recurrencia
	 * 
	 * @param n Término de la secuencia
	 * @param cf Coeficientes de la secuencia
	 * @param vi Valores iniciales de la secuencia
	 * @return Valor del término n-esimo calculado de forma iterativa
	 */
	public static BigInteger secuencia(Integer n, Integer[] cf, Integer[] vi){
		if(cf.length != vi.length) return null;
		int k = cf.length;
		FieldMatrix<BigFraction> e = Matrices.getBase(k,cf);			
		FieldMatrix<BigFraction> a = Matrices.getId(k,BigFractionField.getInstance());
		FieldMatrix<BigFraction> c = Matrices.getColumn(vi);
		while(n > 0){
	        if(n%2==1){
			     a = a.multiply(e);
			}
			e = e.multiply(e);
			n = n/2;
		}	
		FieldMatrix<BigFraction> r = a.multiply(c);
		return r.getEntry(1, 0).getNumerator();
	}

	
	
	
	public static void main(String[] args) {
		System.out.println(binom(10,5));
		System.out.println(fib(10));
	}

}
