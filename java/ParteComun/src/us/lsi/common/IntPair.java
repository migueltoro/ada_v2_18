package us.lsi.common;

public class IntPair extends Pair<Integer, Integer> {

	
	public static IntPair of(Integer a, Integer b) {
		return new IntPair(a, b);
	}

	private IntPair(Integer a, Integer b) {
		super(a, b);
	}

	public IntPair add(IntPair p) {
		return IntPair.of(super.a+p.a, super.b+p.b);
	}

	public IntPair minus(IntPair p) {
		return IntPair.of(super.a-p.a, super.b-p.b);
	}
	
	public IntPair multiply(Integer e) {
		return IntPair.of(e*super.a, e*super.b);
	}
	
	public Integer sumAbs() {
		return Math.abs(this.a)+Math.abs(this.b);
	}
	
	public Double module() {
		return Math.sqrt(this.a*this.a+this.b*this.b);
	}
	
	public Integer manhattan(IntPair p) {
		return this.minus(p).sumAbs();
	}
}
