package us.lsi.iterativorecursivos;

import java.math.BigInteger;

public class MatBigInteger {
	
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
	
//	c0*m.c0+c1*m.c1*b,c0*m.c1+c1*m.c0+c1*m.c1*a
	
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
//		System.out.println(Mfg.base().square().square().square());
		System.out.println(MatBigInteger.base().square().multiply(MatBigInteger.base().square()));
	}


}

