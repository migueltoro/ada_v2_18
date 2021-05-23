package us.lsi.common;

public record IntPair(Integer first,Integer second) {

	
	public static IntPair of(Integer a, Integer b) {
		return new IntPair(a, b);
	}
	
	public static IntPair parse(String s) {
		String[] partes = s.split("[(),]");
		return new IntPair(Integer.parseInt(partes[0].trim()), Integer.parseInt(partes[1].trim()));
	}
	
	@Override
	public String toString() {
		return String.format("(%d,%d)",this.first(),this.second());
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
