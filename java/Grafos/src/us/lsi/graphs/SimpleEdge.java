package us.lsi.graphs;

/**
 * <p> Una arista simple entre dos vértices de tipo V </p>
 * 
 * @author Miguel Toro
 *
 * @param <V> El tipo de los vértices de la arista
 */
public class SimpleEdge<V>  {

	/**
	 * 
	 */
	public double weight;
	public V source;
	public V target;
		
	/**
	 * @param v1 Un vértice
	 * @param v2 Un segundo vértice
	 * @param <V> El tipo de los vértices que une la arista
	 * @return Una arista entre ambos vértices
	 */
	public static <V> SimpleEdge<V> of(V v1, V v2) {
		return new SimpleEdge<>(v1,v2);
	}
	
	public static <V> SimpleEdge<V> of() {
		return new SimpleEdge<>();
	}
	
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

	public SimpleEdge() {
		super();
		this.source = null;
		this.target = null;
		this.weight = 1.;
	}

	public SimpleEdge(V c1, V c2) {
		super();
		this.source = c1;
		this.target = c2;
		this.weight = 1.;
	}

	public SimpleEdge(V c1, V c2, double weight) {
		super();
		this.source = c1;
		this.target = c2;
		this.weight = weight;
	}
	
	
	/**
	 * @return El vértice origen
	 */
	public V getSource(){
		return this.source;
	}
	/**
	 * @return El vértice destino
	 */
	public V getTarget(){
		return this.target;
	}
	/**
	 * @return El peso asociado a la arista
	 */
	public double getEdgeWeight(){
		return this.weight;
	}		
	/**
	 * @param weight El nuevo peso de la arista
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @param v Un vértice de la arista
	 * @return El otro vértice
	 */
	public V otherVertex(V v){
		V r = null;
		if(v.equals(this.source)){
			r = this.target;
		} else if(v.equals(this.target)){
			r = this.source;
		}
		return r;
	}
	
	@Override
	public String toString() {
		return "("+this.source+","+this.target+")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SimpleEdge))
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
		return true;
	}
	
}

