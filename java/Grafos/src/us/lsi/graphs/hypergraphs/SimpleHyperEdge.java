package us.lsi.graphs.hypergraphs;

import java.util.List;

public class SimpleHyperEdge<V,A> {
	
	public static <V, A> SimpleHyperEdge<V, A> ofAction(V source, List<V> targets, A action) {
		return ofWeight(source, targets, action, 1.);
	}

	public static <V, A> SimpleHyperEdge<V, A> ofWeight(V source, List<V> targets, A action, Double weight) {
		return new SimpleHyperEdge<V, A>(source, targets, action, weight);
	}

	public V source;
	public List<V> targets;
	public A action;
	public Double weight;
	
	
	public SimpleHyperEdge(V source, List<V> targets, A action, Double weight) {
		super();
		this.source = source;
		this.targets = targets;
		this.action = action;
		this.weight = weight;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((targets == null) ? 0 : targets.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleHyperEdge<?,?> other = (SimpleHyperEdge<?,?>) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (targets == null) {
			if (other.targets != null)
				return false;
		} else if (!targets.equals(other.targets))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("(%s,%s,%s,%.2f)",source.toString(),targets.toString(),action.toString(),weight);
	}
}
