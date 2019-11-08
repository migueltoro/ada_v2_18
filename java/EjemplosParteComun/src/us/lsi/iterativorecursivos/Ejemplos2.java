package us.lsi.iterativorecursivos;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Ejemplos2 {
	
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
			r = BigInteger.ZERO;
			m.put(n,r);
		} else if(n==1) {
			r = BigInteger.ONE;
			m.put(n,r);
		} else {
			r = a.multiply(fbm(m,n-1,a,b,d1,d0)).add(b.multiply(fbm(m,n-2,a,b,d1,d0)));
			m.put(n,r);
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
	 * @return El valor de f(n,a,b,d1,d0) calculado de forma iterativa obtenida bottom-up de versión 
	 * recursiva
	 */
	public static BigInteger fblin(Integer n,Integer a, Integer b, Integer d1, Integer d0){
		LinBigInteger.initialValues(a,b,d1,d0);
		LinBigInteger p = LinBigInteger.p0();
		while(p.i < n){
			p = p.next();
		}
		return p.c0;
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
	 * @return El valor de f(n,a,b,d1,d0) calculado de forma iterativa convirtiendo el problema en uno
	 * de potencias de matrices
	 */
	public static BigInteger fbmat(Integer n, Integer a, Integer b, Integer d1,Integer d0) {
		MatBigInteger.initialValues(a, b, d1, d0);
		MatBigInteger r = MatBigInteger.base(); //((1,1),(1,0))
		MatBigInteger u = MatBigInteger.one();  //((1,0),(0,1))
		while( n > 0){
		    if(n%2==1){
		    	u = u.multiply(r);  
		    }
		    r = r.square();
		    n = n/2;
		}
		return u.result();
		
	}
	
	
	public static void main(String[] args) {
	
	}
	

}
