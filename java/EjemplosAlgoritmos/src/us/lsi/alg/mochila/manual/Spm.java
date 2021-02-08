package us.lsi.alg.mochila.manual;


public class Spm implements Comparable<Spm> {
	public Integer weight;
	public Integer a;

	public static Spm of(Integer a, Integer weight) {
		return new Spm(a, weight);
	}
	
	public static Spm of(Integer weight) {
		return new Spm(null, weight);
	}

	public static Spm empty() {
		return new Spm(null, 0);
	}

	public Spm(Integer a, Integer weight) {
		super();
		this.weight = weight;
		this.a = a;
	}

	@Override
	public int compareTo(Spm sp) {
		return this.weight.compareTo(sp.weight);
	}

	@Override
	public String toString() {
		return String.format("(%d,%s)", weight, a != null ? a.toString() : "_");
	}

}

