package us.lsi.iterativorecursivos;

import java.math.BigInteger;

public class LinBigInteger {
	
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

