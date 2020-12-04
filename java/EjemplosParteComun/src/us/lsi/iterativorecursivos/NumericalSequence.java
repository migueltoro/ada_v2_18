package us.lsi.iterativorecursivos;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class NumericalSequence {

// fib(n) = fib(n-1)+fib(n-2), fib(0) = 0, fib(1) = 1
	
	
	private static Long fib(Integer n) {
		Long r;
		if(n==0) {
			r = 0L;
		}else if(n==1) {
			r = 1L;
		} else {
			r = fib(n-1)+fib(n-2);
		}
		return r;
	}
	
	public static Long fib_mem(Integer n) {
		Map<Integer,Long> m = new HashMap<>();
		return fib(n,m);
	}
	private static Long fib(Integer n, Map<Integer, Long> m) {
		Long r;
		if(m.containsKey(n)) {
			r = m.get(n);
		} else if(n==0) {
			r = 0L;
			m.put(n,r);
		}else if(n==1) {
			r = 1L;
			m.put(n,r);
		} else {
			r = fib(n-1,m)+fib(n-2,m);
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
	
	
	public static class LinBigInteger {
		
		private static BigInteger d0,d1;
		private static BigInteger a,b;
		public Integer i;
		public BigInteger c0,c1;
		
		public static void initialValues(Integer aa, Integer bb, Integer dd1, Integer dd0) {
			a = new BigInteger(aa.toString());
			b = new BigInteger(bb.toString());
			d1 = new BigInteger(dd1.toString());
			d0 = new BigInteger(dd0.toString());
		}

		public static LinBigInteger of(Integer i, BigInteger c1, BigInteger c0) {
			return new LinBigInteger(i, c1, c0);
		}

		private LinBigInteger(Integer i, BigInteger c1, BigInteger c0) {
			super();
			this.i = i;
			this.c0 = c0;
			this.c1 = c1;
		}

	// (i,u,v) = (i+1,a*u+b*v,u);
		
		public LinBigInteger next() {
			return of(this.i+1,a.multiply(this.c1).add(b.multiply(this.c0)), this.c1);
		}
		
		public static LinBigInteger p0() {
			return LinBigInteger.of(0,d1,d0);
		}

		@Override
		public String toString() {
			return "("+i+","+ c1+","+c0+")";
		}
		
		
	}
	
	
	public static class MatBigInteger {
		
		private static BigInteger d0,d1;
		private static BigInteger a,b;
		private static BigInteger zero = BigInteger.ZERO;
		private static BigInteger one = BigInteger.ONE;
		private static BigInteger two = BigInteger.TWO;
		private BigInteger c0,c1;
		
		public static void initialValues(Integer aa, Integer bb, Integer dd1, Integer dd0) {
			a = new BigInteger(aa.toString());
			b = new BigInteger(bb.toString());
			d1 = new BigInteger(dd1.toString());
			d0 = new BigInteger(dd0.toString());
		}
		
		public static MatBigInteger base() {
			return new MatBigInteger(zero, one);
		}
		
		public static MatBigInteger one() {
			return new MatBigInteger(one,zero);
		}
		
		private static MatBigInteger of(BigInteger c0, BigInteger c1) {
			return new MatBigInteger(c0, c1);
		}

		private MatBigInteger(BigInteger c0, BigInteger c1) {
			super();
			this.c0 = c0;
			this.c1 = c1;
		}
		
	//c0*c0+c1*c1*b, c1*(c1*a+2*c0)
		
		public MatBigInteger square() {
			BigInteger b0 = c0.multiply(c0).add(c1.multiply(c1).multiply(b));
			BigInteger b1 = c1.multiply(c1.multiply(a).add(two.multiply(c0)));
			return MatBigInteger.of(b0, b1);
		}
		
//		c0*m.c0+c1*m.c1*b,c0*m.c1+c1*m.c0+c1*m.c1*a
		
		public MatBigInteger multiply(MatBigInteger m) {
			BigInteger b0 = c0.multiply(m.c0).add(c1.multiply(m.c1).multiply(b));
			BigInteger b1 = c0.multiply(m.c1).add(c1.multiply(m.c0).add(c1.multiply(m.c1).multiply(a)));
			return MatBigInteger.of(b0,b1);
		}

	//d1*c1*b+d0*c0
		public BigInteger result() {
			return d1.multiply(c1).add(d0.multiply(c0));
		}

		@Override
		public String toString() {
			return String.format("\n(%d,%d)\n(%d,%d)\n",c0.add(c1),c1,c1,c0);
		}
		
		public static void main(String[] args) {
			System.out.println(MatBigInteger.base());
			System.out.println(MatBigInteger.base().square());
			System.out.println(MatBigInteger.base().square().square());
//			System.out.println(Mfg.base().square().square().square());
			System.out.println(MatBigInteger.base().square().multiply(MatBigInteger.base().square()));
		}

	}	
	
	
	public static void main(String[] args) {
		Long r1 = fib(10);
		Long r2 = fib_mem(10);
		System.out.println(r1+","+r2);
	
	}
	

}
