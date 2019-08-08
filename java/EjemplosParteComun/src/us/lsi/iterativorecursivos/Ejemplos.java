package us.lsi.iterativorecursivos;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ejemplos {
	
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
	
	public static BigInteger fblin(Integer n,Integer a, Integer b, Integer d1, Integer d0){
		LinBigInteger.initialValues(a,b,d1,d0);
		LinBigInteger p = LinBigInteger.p0();
		while(p.i < n){
			p = p.next();
		}
		return p.c0;
	}
	
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
	

}
