package us.lsi.common;

public class IntPair extends Pair<Integer, Integer> {

	
	public static IntPair of(Integer a, Integer b) {
		return new IntPair(a, b);
	}

	private IntPair(Integer a, Integer b) {
		super(a, b);
	}

	public IntPair add(IntPair p) {
		return IntPair.of(this.first()+p.first(), this.second()+p.second());
	}

	public IntPair minus(IntPair p) {
		return IntPair.of(this.first()-p.first(), this.second()-p.second());
	}
	
	public IntPair multiply(Integer e) {
		return IntPair.of(e*this.first(), e*this.second());
	}
	
	public Integer sumAbs() {
		return Math.abs(this.first())+Math.abs(this.second());
	}
	
	public Double module() {
		return Math.sqrt(this.first()*this.first()+this.second()*this.second());
	}
	
	public Integer manhattan(IntPair p) {
		return this.minus(p).sumAbs();
	}
}
