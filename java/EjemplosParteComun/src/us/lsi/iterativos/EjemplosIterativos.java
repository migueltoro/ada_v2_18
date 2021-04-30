package us.lsi.iterativos;


import java.util.Arrays;
import java.util.List;
import us.lsi.common.List2;


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
	
	
	public static void main(String[] args) {
		System.out.println(binom(10,5));
		System.out.println(fib(10));
	}

}
