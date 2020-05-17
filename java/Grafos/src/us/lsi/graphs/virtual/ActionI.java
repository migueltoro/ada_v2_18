package us.lsi.graphs.virtual;

import java.util.function.Function;
import java.util.function.Predicate;

public class ActionI<V> implements Action<V> {
	
	public Integer id;
	public String name;
	public final Predicate<V> isApplicable;	
	public final Function<V,V> neighbor;	
	public Function<V,Double> weight;
	
	public ActionI(Integer id, String name,Predicate<V> isApplicable,Function<V, V> neighbor,Function<V, Double> weight) {
		super();
		this.id = id;
		this.name = name;
		this.isApplicable = isApplicable;
		this.neighbor = neighbor;		
		this.weight = weight;
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.graphs.virtual.Action#neighbor(V)
	 */
	@Override
	public V neighbor(V v) {
		return this.neighbor.apply(v);
	}
	/* (non-Javadoc)
	 * @see us.lsi.graphs.virtual.Action#isApplicable(V)
	 */
	@Override
	public boolean isApplicable(V v) {
		return this.isApplicable.test(v);
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.graphs.virtual.Action#weight(V)
	 */
	@Override
	public Double weight(V v) {
		return this.weight.apply(v);
	}

}
