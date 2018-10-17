package us.lsi.iterativo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;


import us.lsi.common.Lists2;
import us.lsi.common.Preconditions;

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
			List<Integer> ls = Lists2.newList();
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
			lsa = Lists2.newList(ls);
		}
		return lsa.get(k);
	}
	
	/**
	 * @param b Base 
	 * @param n Exponente
	 * @return Resultado de elevar b a n calculado de forma iterativa
	 */
	public static long pot(int b,int n){
		int e = b;
		int a = 1;
		while( n > 0){
	        	if(n%2==1){
			     a = a * e;
			}
			e = e * e;
			n = n/2;
		}
		return a;
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

	/**
	 * @param a Un entero
	 * @param b Un entero
	 * @return Máximo Común Divisor de los dos enteros calculado de forma iterativa
	 */
	public static long mcd(int a,int b) {
		while(b > 0){
		   int a0 = a;
		   a = b;
		   b = a0%b;   
	    }
		return a;
	}
	public static <E> Boolean estaOrdenada(List<E> ls, Comparator<E> cmp) {
		return estaOrdenada(ls, 0, cmp);
	}
	public static <E> Boolean estaOrdenada(List<E> ls, int i, Comparator<E> cmp) {
		Boolean r;
		if(ls.size() -i < 2) {
			r = true;
		} else {
			r = cmp.compare(ls.get(i),ls.get(i+1))<=0 && estaOrdenada(ls,i+1,cmp);
		}
		return r;
	}
	public static <E> Boolean estaOrdenada2(List<E> ls,Comparator<E> cmp) {
		return IntStream.range(0,ls.size()-1)
				.allMatch(i->cmp.compare(ls.get(i),ls.get(i+1))<=0);
	}
	
	public static <E> Boolean estaOrdenada3(List<E> ls,Comparator<E> cmp) {
		Integer i = 0;
		Boolean a = true;
		while(i<=ls.size()-2 && a) {
			a = cmp.compare(ls.get(i),ls.get(i+1))<=0;
			i++;
		}
		return a;
	}
	
	public static <E> List<E> inversa(List<E> ls) {
		return inversa(ls, 0);
	}
	public static <E> List<E> inversa(List<E> ls, int i) {
		List<E> r;
		if(ls.size() -i == 0) {
			r = new ArrayList<>();
		} else {			
			r = inversa(ls,i+1);
			r.add(ls.get(i));
		}
		return r;
	}
//	public static <E> List<E> inversa2(List<E> ls) {
//		Accumulator<List<E>,List<E>,List<E>>  a = null;
//		return Streams2.accumulateLeft(ls.stream(),a);
//	}
	public static <E> List<E> inversa3(List<E> ls) {
		Integer i = 0;
		List<E> b = new ArrayList<>();;
		while(i < ls.size()){	
			b.add(0,ls.get(i));
			i++;
		}
		return b;
	}
	
	public static Integer cercano(List<Integer> ls, Integer e){
		if(ls.isEmpty()) Preconditions.checkArgument(!ls.isEmpty());
		int n = ls.size();
		return cercano(ls, e, 0, n, n/2);
	}
	public static Integer cercano(List<Integer> ls, Integer e, Integer i,Integer j,Integer k) {	
		Integer r;
	 	if(j-i == 1) {
	 		r = ls.get(i);
	 	} else if(j-i == 2){
	 		r = masCercano(e,ls.get(i),ls.get(i+1));
		} else if(e == ls.get(k)) {
	 		r = ls.get(k);
	 	} else if(e < ls.get(k)){
	 		r = cercano(ls,e,i,k+1,(i+k+1)/2);
	 	} else {
			r = cercano(ls,e,k,j,(k+j)/2);
	 	}
		return r;
	}
	public static Integer masCercano(Integer e, Integer e1, Integer e2){
	 	Integer r;
		if(Math.abs(e-e1) <= Math.abs(e-e2)){
	 		r = e1;
	 	} else {
	 		r = e2;
	 	}
	 	return r;
	}

	
	
	public static void main(String[] args) {
		System.out.println(binom(10,5));
		System.out.println(fib(10));
		System.out.println(pot(8,5));
		System.out.println(mcd(10546,3280));
		var s = List.of(1,2,3,41,30,64,71,88);
		Comparator<Integer> cmp = Comparator.naturalOrder();
		System.out.println(estaOrdenada(s,cmp));
		System.out.println(estaOrdenada2(s,cmp));
		System.out.println(estaOrdenada3(s,cmp));
		System.out.println(inversa(s));
		System.out.println(inversa3(s));
		var r = cercano(s,60);
		System.out.println(r);
		var s2 = List.of(0,2,6,10);
		r = cercano(s2,5);
		System.out.println(r);
	}

}
