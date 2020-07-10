package us.lsi.graphs.alg;

public class Sp<E> implements Comparable<Sp<E>> {
	public Double weight;
	public E edge;

	public static <E> Sp<E> of(E edge, Double weight) {
		return new Sp<>(edge, weight);
	}
	
	public static <E> Sp<E> of(Double weight) {
		return new Sp<>(null, weight);
	}

	public static <E> Sp<E> empty() {
		return new Sp<>(null, 0.);
	}

	public Sp(E edge, Double weight) {
		super();
		this.weight = weight;
		this.edge = edge;
	}

	@Override
	public int compareTo(Sp<E> sp) {
		return this.weight.compareTo(sp.weight);
	}

	@Override
	public String toString() {
		return String.format("(%.2f,%s)", weight, edge != null ? edge.toString() : "_");
	}

}
