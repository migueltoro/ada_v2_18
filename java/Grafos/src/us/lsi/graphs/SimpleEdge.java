package us.lsi.graphs;

/**
 * <p> Una arista simple entre dos vértices de tipo V </p>
 * 
 * @author Miguel Toro
 *
 * @param <V> El tipo de los vértices de la arista
 */
public class SimpleEdge<V>  {

	
	private final V source;
	private final V target;
	private final Double weight;
	
	/**
	 * @param v1 Un vértice
	 * @param v2 Un segundo vértice
	 * @param weight El peso de la arista
	 * @param <V> el tipo de los vértices
	 * @return Una arista entre ambos vértices
	 */
	public static <V> SimpleEdge<V> of(V v1, V v2, double weight) {
		return new SimpleEdge<V>(v1, v2, weight);
	}
	
	protected SimpleEdge(V source, V target, Double weight) {
		super();
		this.source = source;
		this.target = target;
		this.weight = weight;
	}

	/**
	 * @param v Un vértice de la arista
	 * @return El otro vértice
	 */

	public  V otherVertex(V v){
		V r = null;
		if(v.equals(this.source)) r = this.target;
		else if(v.equals(this.target)) r = this.source;
		return r;
	}

	public V getSource() {
		return source;
	}

	public V getTarget() {
		return target;
	}

	public Double getWeight() {
		return weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
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
		SimpleEdge<?> other = (SimpleEdge<?>) obj;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
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
		return "SimpleEdge [source=" + source + ", target=" + target + ", weight=" + weight + "]";
	}

}

