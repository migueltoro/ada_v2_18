package us.lsi.iterativorecursivos;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import us.lsi.common.Matrix;
import us.lsi.common.String2;

import org.apache.commons.math3.fraction.BigFraction;

public class NumericalSequence {

// fib(n) = fib(n-1)+fib(n-2), fib(0) = 0, fib(1) = 1
	
	
	/**
	 * Cálculo de la función f definida por:
	 * 
	 * f(n) = d1 si n=1
	 * f(n) = d0 si n=0
	 * f(n) = a*f(n-1)+b*f(n-2) si n &gt; 1
	 * 
	 * @param n Parámetro de entrada
	 * @param a Parámetro de entrada
	 * @param b Parámetro de entrada
	 * @param d1 Parámetro de entrada
	 * @param d0 Parámetro de entrada
	 * @return El valor de f(n,a,b,d1,d0) calculado de forma recursiva sin memoria
	 */
	public static BigInteger fbsm(Integer n,Integer a, Integer b, Integer d1,Integer d0){
		BigInteger r;
		if(n==0) {
			r = BigInteger.ZERO;
		} else if(n==1) {
			r = BigInteger.ONE;
		} else {
			BigInteger aa = new BigInteger(a.toString());
			BigInteger bb = new BigInteger(b.toString());
			r = aa.multiply(fbsm(n-1,a,b,d1,d0)).add(bb.multiply(fbsm(n-2,a,b,d1,d0)));
		}
		return r;
	}
	
	/**
	 * Cálculo de la función f definida por:
	 * 
	 * f(n) = d1 si n=1
	 * f(n) = d0 si n=0
	 * f(n) = a*f(n-1)+b*f(n-2) si n &gt; 1
	 * 
	 * @param n Parámetro de entrada
	 * @param a Parámetro de entrada
	 * @param b Parámetro de entrada
	 * @param d1 Parámetro de entrada
	 * @param d0 Parámetro de entrada
	 * @return El valor de f(n,a,b,d1,d0) calculado de forma recursiva con memoria
	 */
	public static BigInteger fbm(Integer n,Integer a, Integer b, Integer d1,Integer d0){
		Map<Integer,BigInteger> m = new HashMap<>();
		BigInteger aa = new BigInteger(a.toString());
		BigInteger bb = new BigInteger(b.toString());
		BigInteger dd1 = new BigInteger(d1.toString());
		BigInteger dd0 = new BigInteger(d0.toString());
		return fbm(m,n,aa,bb,dd1,dd0);
	}
	
	public static BigInteger fbm(Map<Integer,BigInteger> m, Integer n,BigInteger a, BigInteger b, BigInteger d1,BigInteger d0){
		BigInteger r;
		if(m.containsKey(n)) {
			r = m.get(n);
		} else if(n==0) {
			r = d0;
			m.put(n,r);
		} else if(n==1) {
			r = d1;
			m.put(n,r);
		} else {
			r = a.multiply(fbm(m,n-1,a,b,d1,d0)).add(b.multiply(fbm(m,n-2,a,b,d1,d0)));
			m.put(n,r);
		}
		return r;
	}
	
	public static BigInteger fblin(Integer n,Integer a, Integer b, Integer d1,Integer d0){
		Map<Integer,BigInteger> m = new HashMap<>();
		BigInteger aa = new BigInteger(a.toString());
		BigInteger bb = new BigInteger(b.toString());
		BigInteger dd1 = new BigInteger(d1.toString());
		BigInteger dd0 = new BigInteger(d0.toString());
		for(int i = 0;i<=n;i++) {
			BigInteger r;
			if(i==0) r = dd0;
			else if(i==1) r = dd1;
			else r = aa.multiply(m.get(i-1)).add(bb.multiply(m.get(i-2)));
			m.put(i, r);
			m.remove(i-3);
		}
		return m.get(n);
	}
	
	public static BigInteger fbMatrix(Integer n,Integer a, Integer b, Integer d1,Integer d0){
		Integer[] bb = {a,b,1,0};
		Integer[] cl = {d1,d0};
		Matrix<BigFraction> base = Matrix.of(bb, 2, 2).map(e->new BigFraction(e));
		Matrix<BigFraction> colum = Matrix.of(cl, 2, 1).map(e->new BigFraction(e));
		Matrix<BigFraction> m = Matrix.pow(base, n);
		Matrix<BigFraction> r = Matrix.multiply(m,colum);
		return r.get(1, 0).getNumerator();
	}
	

	public static void test1() {
		Long t0 = System.nanoTime();
		BigInteger r1 = fblin(10000,1,1,1,0);
		Long t1 = System.nanoTime();
		BigInteger r2 = fbm(10000,1,1,1,0);
		Long t2 = System.nanoTime();
		BigInteger r3 = fbMatrix(10000,1,1,1,0);
		Long t3 = System.nanoTime();
		String2.toConsole("%d == %s\n%d == %s\n%d == %s",t1-t0,r1,t2-t1,r2,t3-t2,r3);
	}
	
	public static void test2() {
		Long t0 = System.nanoTime();
		BigInteger r1 = fblin(100000,1,1,1,0);
		Long t1 = System.nanoTime();
//		BigInteger r2 = fbm(10000,1,1,1,0);
//		Long t2 = System.nanoTime();
		BigInteger r3 = fbMatrix(100000,1,1,1,0);
		Long t3 = System.nanoTime();
		String2.toConsole("%d == %s\n%d == %s",t1-t0,r1,t3-t1,r3);
	}
	
	
	public static void main(String[] args) {
		test2();
	}
	

}
