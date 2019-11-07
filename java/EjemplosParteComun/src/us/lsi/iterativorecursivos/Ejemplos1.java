package us.lsi.iterativorecursivos;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import us.lsi.common.Pair;
import us.lsi.flujossecuenciales.CollectS;
import us.lsi.flujossecuenciales.IteratorZip;
import us.lsi.flujossecuenciales.SeqCollector;

public class Ejemplos1 {
	
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
	
	/**
	 * 
	 * @param base Base
	 * @param n Exponente
	 * @return Valor de base^n de forma iterativa
	 */
	public static Long pow(Long base, Integer n){
		Long r = base;
		Long u = 1L;
		while( n > 0){
	       if(n%2==1){
			     u = u * r;
		   }
		   r = r * r;
		   n = n/2;
		}
		return u;
	}

	
	/**
	 * @param ls Coeficientes
	 * @param v Valor de la variable
	 * @return Valor de polinomio cuyos coficientes, de menor a mayor grado, 
	 * están en ls y el valor de la variable en v. 
	 * Calculado de izquierda a derecha
	 */
	public static Double polinomioEvalLeft(List<Double> ls, Double v) {
		Double b = 0.;
		Integer i = 0;
		Double r = 1.;
		Integer n = ls.size();
		while (n - i > 0) {
			Double e = ls.get(i);
			b = b +  e* r;
			i = i + 1;
			r = r * v;
		}
		return b;
	}
	
	/**
	 * @param ls Coeficientes
	 * @param v Valor de la variable
	 * @return Valor de polinomio cuyos coficientes, de menor a mayor grado, 
	 * están en ls y el valor de la variable en v. 
	 * Calculado de izquierda a derecha y usando un acumulador y secuencia adecuados
	 */
	public static Double polinomioEvalLeft2(List<Double> ls, Double v) {
		SeqCollector<Pair<Double,Double>,Double,Double> a = 
				SeqCollector.of(()->0.,(b,p)->b+p.b*p.a);
		Iterator<Double> pt = Stream.iterate(1., p->p*v).iterator();
		Iterator<Double> els = ls.stream().iterator();
		Iterator<Pair<Double,Double>> s = IteratorZip.of(pt,els);		
		return CollectS.seqCollectLeft(s, a);
	}
	
	/**
	 * @param ls Coeficientes 
	 * @param v Valor de la variable
	 * @return Valor de polinomio cuyos coficientes, de menor a mayor grado,
	 * están en ls y el valor de la variable en v. 
	 * Calculado de derecha a izquierda
	 */
	public static Double polinomioEvalRight(List<Double> ls, Double v) {
		Double b = polinomioEvalRightP(ls,v,0);
		return b;
	}

	public static Double polinomioEvalRightP(List<Double> ls, Double v, Integer i) {
		Double r = 0.;
		Integer n = ls.size();
		if (n - i > 0) {
			Double e = ls.get(i);
			Double b = polinomioEvalRightP(ls, v, i + 1);
			r = b * v + e;
		} 
		return r;
	}
	
	/**
	 * @param ls Coefincientes 
	 * @param v Valor de la variable
	 * @return Valor de polinomio cuyos coficientes, de menor a mayor grado,
	 * están en ls y el valor de la variable en v. 
	 * Calculado de derecha a izquierda y suando un acumulador y secuencia adecuados
	 */
	public static Double polinomioEvalRight2(List<Double> ls, Double v) {
		SeqCollector<Double,Double,Double> a = 
				SeqCollector.of(()->0.,(b,e)->b*v+e);
		Iterator<Double> s = ls.stream().iterator();
		return CollectS.seqCollectRight(s, a);
	}
	
	
	/**
	 * @param n Entero
	 * @param bs Base
	 * @return Devuelve el número n en la base bs
	 */
	public static String toBase(Integer n, Integer bs) {
		SeqCollector<String,String,String> a = SeqCollector.of((b,e)-> b+e);		
		Iterator<String> s = Stream.iterate(n,e->e>0,e->e/bs)
				.map(x->x%bs)
				.map(x->x.toString())
				.iterator();
		return CollectS.reduceRight(s, a).get();
	}
	
	
	/**
	 * @param n Entero
	 * @param bs Base
	 * @return Evalua a la cadena de dígitos en base bs
	 */
	public static Double eval(String n, Integer bs) {
		Iterator<Double> digits = n.chars().mapToDouble(x->Character.getNumericValue(x)).iterator();
		SeqCollector<Double,Double,Double> a = SeqCollector.of(()->0.,(b,e)->b*bs+e);
		return CollectS.seqCollectLeft(digits, a);
	}
	
	public static void main(String[] args) {
		Double v = -14.3;
		List<Double> ls = List.of(2.0,1.,3.,-0.45,1.34);
		Double r1 = polinomioEvalLeft(ls,v);
		Double r2 = polinomioEvalLeft2(ls,v);
		Double r3 = polinomioEvalRight(ls,v);
		Double r4 = polinomioEvalRight2(ls,v);
		System.out.println(String.format("%.0f,%.0f,%.0f,%.0f",r1,r2,r3,r4));
		String r = toBase(123,3);
		System.out.println(String.format("======="));
		Double nd = eval(r,3);
		System.out.println(String.format("%d,%s,%.0f",123,r,nd));
		System.out.println(String.format("======="));
	}
	

}
