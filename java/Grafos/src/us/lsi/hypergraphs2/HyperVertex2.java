package us.lsi.hypergraphs2;

import java.util.List;
import us.lsi.graphs.alg.DP.Sp;

public interface HyperVertex2<V extends HyperVertex2<V, E, A, S>, E extends HyperEdge2<V,E,A,S>, A, S> {
	
	public List<A> actions();
	public Boolean isBaseCase();
	public Double baseCaseSolutionWeight();
	public S baseCaseSolution();
	public V me();
	public List<V> neighbors(A a);
	public E edge(A a); 
	
	public default Boolean isValid() {
		return true;
	}
	
	public default Datos<V,E,A> datos() {
		return Datos.get();
	}
	
	public default Sp<E> solutionWeight() {
		Sp<E> r;
		if (datos().contains(me()))
			r = datos().get(me());
		else {
			r = null;
			if (this.isBaseCase()) {
				Double br = baseCaseSolutionWeight();
				if (br != null) r = Sp.of(br, null);
			} else if (!this.edgesOf().isEmpty()) {
				r = this.edgesOf().stream()
						.map(e -> e.solutionWeight())
						.filter(s -> s != null)
						.peek(e -> datos().putAll(me(), e))
						.min(datos().order()).orElse(null);
			}
			datos().put(me(), r);
		}
		return r;
	}

	default public S solution() {
		Sp<E> sp = this.solutionWeight();
		S r;
		if (this.isBaseCase())
			r = this.baseCaseSolution();
		else {
			r = sp.edge().solution();
		}
		return r;
	}

	/**
	 * Este m&eacute;todo podr&iacute;a ser sobrescrito en la clase que refine al
	 * tipo
	 * 
	 * @return El conjunto de las aristas hacia los vecinos
	 */
	default public List<E> edgesOf() {
		return this.actions().stream().map(a -> this.edge(a)).toList();
	}

	default GraphTree2<V, E, A, S> graphTree() {
		return GraphTree2.of(this.me(), this.solutionWeight().edge().action());
	}
}
