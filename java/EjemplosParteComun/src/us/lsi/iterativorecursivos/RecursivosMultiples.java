package us.lsi.iterativorecursivos;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.common.IntPair;
import us.lsi.common.IntQuartet;
import us.lsi.common.List2;
import us.lsi.common.Matrix;
import us.lsi.common.Pair;
import us.lsi.common.String2;
import us.lsi.common.View4;
import us.lsi.streams.Stream2;

import org.apache.commons.math3.fraction.BigFraction;

public class RecursivosMultiples {

// fib(n) = fib(n-1)+fib(n-2), fib(0) = 0, fib(1) = 1
	
	
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
	
	

	public static record Bn(Long a, Long b) {
		public static Bn of(Long a, Long b) {
			return new Bn(a,b);
		}
	}
		
	public static BigInteger binom(Long n, Long k) {
		Map<Bn,BigInteger> m = new HashMap<>();
		for(Long i=0L; i<=n; i++) {
			for(Long j=0L; j<=i;j++) {
				if(j==0) m.put(Bn.of(i,0L),BigInteger.ONE);
				else if(j==1 || i-j ==1) m.put(Bn.of(i,j),new BigInteger(i.toString()));
				else {
					BigInteger r = m.get(Bn.of(i-1, j-1)).add(m.get(Bn.of(i-1, j-1)));
					m.put(Bn.of(i,j),r);
				}
				m.remove(Bn.of(i-2,k));
			}				
		}
		return m.get(Bn.of(n, k));
	}

	
	
	public static record Rectangle(Integer xMin,Integer xMax,Integer yMin, Integer yMax) implements Comparable<Rectangle>{
		public static Rectangle of(Integer xMin,Integer xMax,Integer yMin, Integer yMax) {
			return new Rectangle(xMin,xMax,yMin,yMax);
		}
		public Integer area() {
			return (xMax-xMin)*(yMax-yMin);
		}
		@Override
		public int compareTo(Rectangle other) {
			return this.area().compareTo(other.area());
		}
	}
	
	
	public static <E> Matrix<E> largeRectangle1(Matrix<E> m, Predicate<E> pd){	
		return Matrix.allSubMatrix(m)
				.filter(mx->Matrix.allElements(mx,pd))
				.max(Comparator.comparing(my->my.area()))
				.orElse(Matrix.empty());	
	}
	
	private static <E> Matrix<E> center(Matrix<E> m, Predicate<E> pd) {
		IntPair center = m.centerIntPair();
		Integer fMin = 0;
		Integer fMax = m.nf()+1;
		Integer cMin = 0;
		Integer cMax = m.nc()+1;
		Integer f = center.first();;
		Integer c = center.second();
		String2.toConsole("C = %d,%d\n", f, c);
		if (!pd.test(m.get(f, c))) return Matrix.empty();
		while (c >= 0 && pd.test(m.get(f, c))) {
			while (f >= fMin && pd.test(m.get(f, c))) {
				String2.toConsole("1PF = %d,%d,%d\n", f, c,fMin);
				f--;
				String2.toConsole("1F = %d,%d,%d\n",f, c,fMin);
			}
			fMin = Math.max(fMin, f+1);
			f = center.first();
			c--;
			String2.toConsole("1C = %d,%d,%d\n",f, c,fMin);
		}
		cMin = c+1;
		String2.toConsole("1L = %d,%d,%d,%d\n", fMin, cMin, fMax, cMax);
		c = center.second();
		f = center.first();
		while (c >= cMin && pd.test(m.get(f, c))) {
//			f = center.first();
			while (f < m.nf() && pd.test(m.get(f, c))) {
				f++;
				String2.toConsole("2F = %d,%d,%d\n", f, c,fMax);
			}
			fMax = Math.min(fMax, f);
			c--;
			f = center.first();
			String2.toConsole("2C = f=%d,c=%d,cMin=%d,fMax=%d,%s\n",f, c,cMin,fMax,c >= cMin && pd.test(m.get(f, c)));
		}
		cMin = Math.max(cMin,c+1);
		String2.toConsole("2L = %d,%d,%d,%d\n", fMin, cMin, fMax, cMax);
		c = center.second();
		while (c < m.nc() && pd.test(m.get(f, c))) {
			f = center.first();
			while (f >= fMin && pd.test(m.get(f, c))) {
				f--;
			}
			fMin = Math.max(fMin, f+1);
			c++;
		}
		cMax = c;
//		String2.toConsole("3L = %d,%d,%d,%d\n", fMin, cMin, fMax, cMax);
		c = center.second();
		while (c < cMax && pd.test(m.get(f, c))) {
			f = center.first();
			while (f < fMax && pd.test(m.get(f, c))) {
				f++;
			}
			fMax = Math.min(fMax, f);
			c++;
		}
		cMax = Math.max(cMax,c);
//		String2.toConsole("4L = %d,%d,%d,%d\n", fMin, cMin, fMax, cMax);
		return m.view(fMin, cMin, fMax - fMin, cMax - cMin);
	}

	public static <E> Matrix<E> largeRectangle2(Matrix<E> m, Predicate<E> pd){
		Matrix<E> r;
		if(m.nf()>2 && m.nc()>2) {
			View4<Matrix<E>> vx = m.views();
			Matrix<E> ma = largeRectangle2(vx.a(),pd);
			Matrix<E> mb = largeRectangle2(vx.b(),pd);
			Matrix<E> mc = largeRectangle2(vx.c(),pd);
			Matrix<E> md = largeRectangle2(vx.d(),pd);
			Matrix<E> ct = center(m,pd);
			r = List.of(ma,mb,mc,md,ct).stream().max(Comparator.comparing(my->my.area())).get();
		} else {
			r = largeRectangle1(m,pd);
		}
		return r;
	}
	
	public static <E> Boolean cumplePropiedadRecursiva(Matrix<E> m, Predicate<Matrix<E>> pd) {
		Boolean r;
		if(m.nf()>2 && m.nc()>2) {
			View4<Matrix<E>> vx = m.views();
			r =	cumplePropiedadRecursiva(vx.a(),pd) &&
			    cumplePropiedadRecursiva(vx.b(),pd) &&
			    cumplePropiedadRecursiva(vx.c(),pd) &&
			    cumplePropiedadRecursiva(vx.d(),pd) &&
			    pd.test(m);
		} else {
			r =  pd.test(m);
		}
		return r;
	}
	
	public static record SolH(Integer inf, Integer sup, Integer min_h, Integer min_index, Integer a, List<Integer> ls)
			implements Comparable<SolH> {
		public static SolH of(Integer inf, Integer sup, Integer min_h, Integer min_index, Integer a, List<Integer> ls) {
			return new SolH(inf, sup, min_h, min_index, a, ls);
		}

		public static SolH of(Integer inf, Integer sup, Integer min_index, List<Integer> ls) {
			Integer h = ls.get(min_index);
			Integer a = (sup - inf) * h;
			return new SolH(inf, sup, h, min_index, a, ls);
		}

		public static SolH zero() {
			return of(-1, -1, 1000, -1, 0, null);
		}

		@Override
		public int compareTo(SolH other) {
			return this.a().compareTo(other.a());
		}

		@Override
		public String toString() {
			return String.format("(inf=%d,sup=%d,minIndex=%d,area=%d)", this.inf(), this.sup(), this.min_index(),
					this.a());
		}
	}
	
	public static Integer minIndex(List<Integer> ls, Integer i1, Integer i2) {
		return ls.get(i1) < ls.get(i2) ?i1:i2;
	}
	
	public static Integer minIndex(List<Integer> ls, Integer i1, Integer i2, Integer i3) {
		return minIndex(ls,i1,minIndex(ls,i2,i3));
	}


	public static SolH histograma0(List<Integer> ls) {
		Integer n = ls.size();
		SolH amax = SolH.zero();
		for (Integer i = 0; i < n; i++) {
			for (Integer j = i+1; j < n; j++) {
				Integer m = i;
				for(Integer k=i+1;k<j;k++) {
					m = minIndex(ls,m,k);
				}
				SolH e = SolH.of(i,j,m,ls);
				if (e.compareTo(amax) > 0) amax = e;
			}
		}
		return amax;
	}


	public static SolH histograma1(List<Integer> ls){
		Integer n = ls.size();
		SolH em = SolH.zero();
		for(Integer i = 0; i < n; i++){
			Integer m = i;
			for(Integer j = i+1; j <= n; j++){
				m = minIndex(ls,j-1,m);
				SolH e = SolH.of(i,j,m,ls);
				if (e.compareTo(em) > 0) em = e;
			}
		}
		return em;
	}

	public static SolH histograma2(List<Integer> ls){
		Integer n = ls.size();
		return histograma2(0,n,ls);
	}

	public static SolH histograma2(Integer i, Integer j, List<Integer> ls){
		SolH rm;
		if(j-i>1){
			Integer k = (j+i)/2;
			SolH r1 = histograma2(i,k,ls);
			SolH r2 = histograma2(k,j,ls);
			SolH r3 = centro(i,j,k,ls);
			rm = List.of(r1,r2,r3).stream().max(Comparator.naturalOrder()).get();
		} else if (j-i == 1) {
			rm = SolH.of(i,j,i,ls);
		} else {
			rm = SolH.zero();
		}
		return rm;
	}



	public static SolH centro(Integer i, Integer j, Integer k, List<Integer> ls) {
	        Integer i1 = k-1, j1 = k+1;
	        Integer min_index = minIndex(ls,i1,j1-1);
	        SolH amax = SolH.zero();
	        while(i1 >= i && j1 <= j) {
	            min_index = minIndex(ls,min_index,i1,j1-1);
	            SolH r = SolH.of(i1,j1,min_index,ls);
	            if(r.compareTo(amax) > 0) amax = r;
	            if (i1 == i) j1++;
	            else if (j1 == j) i1--;
	            else {
	                if (ls.get(i1-1) >= ls.get(j1))  i1--;
	                else j1++;
	            }
	        }
	        return amax;
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
	
	public static void test3() {
		Integer[] d = {0,1,1,0,1,1,1,0,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1,1,0,1,1,1,0,1,0,0,1,1,1,0,1,1,1,1,0,1,1,1,1,1,0,0,0,0,0};
		
		Matrix<Integer> r = Matrix.of(d,12,5);
		String2.toConsole(r.center().toString());
		String2.toConsole(r.centerIntPair().toString());
		String2.toConsole("___________________");
		String2.toConsole(r.toString());
		String2.toConsole("___________________");
		String2.toConsole(center(r,e->e.equals(1)).toString());
		String2.toConsole("___________________");
		String2.toConsole(largeRectangle1(r,e->e.equals(1)).toString());
		String2.toConsole("___________________");
		String2.toConsole(largeRectangle2(r,e->e.equals(1)).toString());
	}
	
	public static void test4() {
		Integer[] d = {0,1,1,0,1,1,1,0,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1,1,0,1,1,1,0,1,0,0,1,1,1,0,1,1,1,1,0,1,1,1,1,1,0,0,0,0,0};
		Matrix<Integer> r = Matrix.of(d,12,5);
		String2.toConsole("______________\n%s\n",r.toString());
		r.allSubMatrix()
		.filter(mt->mt.nf()==3 && mt.nc()==4)
		.forEach(m->String2.toConsole("______________\n%s\n",m));
//		.forEach(m->String2.toConsole("______________\n"));
	}
	
	public static void test5() {
		Integer[] d = {0,1,1,0,1,1,1,0,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1,1,0,1,1,1,0,1,0,0,1,1,1,0,1,1,1,1,0,1,1,1,1,1,0,0,0,0,0};
		Matrix<Integer> r = Matrix.of(d,12,5);
		Matrix<Integer> s = r.view(1,0,4,5);
		String2.toConsole("______________\n%s\n",r);
		String2.toConsole("______________\n%s\n",s);
		String2.toConsole("______________\n%s\n",center(s,e->e.equals(1)));
		String2.toConsole("______________\n%s\n",s.view(1,1,3,4));
	}
	
	
	public static <E> Matrix<E> conPropiedad(Matrix<E> mt, Predicate<Matrix<E>> pd){
		return mt.allSubMatrix()
				.filter(m->m.nf()>1 && m.nc()>1)
				.filter(pd)
				.findFirst()
				.get();
	}
	
	
	public static void test6() {
		Integer[][] mat =   { {1,0,0,1,0},{0,0,1,0,1},{0,0,0,1,0},{1,0,1,0,1}};
		Matrix<Integer> mt = Matrix.of(mat);
		Matrix<Integer> r = conPropiedad(mt,m->m.corners().stream().allMatch(e->e.equals(1)));
		String2.toConsole("Solucion \n%s",r);
	}
	
	
	
	public static void test7() {
		List<Integer> ls = List.of(6,2,5,4,5,1,6);
//		Integer h = minIndex(ls,0,1,2,3,4,5,6);
//		String2.toConsole("0 ___________\n%s\n",SolH.of(0, 7, h, ls));
//		String2.toConsole("0 ___________\n%s\n",minIndex(ls,0,1,2,3,4,5,6));
//		String2.toConsole("0 ___________\n%s\n",minIndex(ls,1,5));
//		String2.toConsole("0 ___________\n%s\n",minIndex2(ls,1,5));
//		String2.toConsole("0 ___________\n%s,%s\n",ls.get(1),ls.get(5));
		String2.toConsole("0 ___________\n%s\n",histograma0(ls));
		String2.toConsole("1 ___________\n%s\n",histograma1(ls));
		String2.toConsole("2 ___________\n%s\n",histograma2(ls));
//		String2.toConsole("0 ___________\n%s,%s,%s\n",SolH.of(2, 5, 3, ls),SolH.of(2, 6, 5, ls),SolH.of(2, 5, 3, ls).compareTo(SolH.of(2, 6, 5, ls)));
	}
	
	public static void main(String[] args) {
		test7();
	}
	

}
