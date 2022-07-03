package us.lsi.hypergraphs2;

import java.util.List;
import us.lsi.graphs.alg.DP.Sp;

public interface HyperEdge2<V extends HyperVertex2<V, E, A, S>, 
		E extends HyperEdge2<V,E,A,S>, A, S> {
	V source();
	A action();
	Double solutionWeight(List<Double> solutions);
	S solution(List<S> solutions);
	E me();
	default List<V> targets() {
		return this.source().neighbors(this.action());
	}
	default Sp<E> solutionWeight() {
		List<Sp<E>> ls = this.targets().stream().map(v->v.solutionWeight()).toList();
		if(ls.contains(null)) return null;
		else {
			Double weight = this.solutionWeight(ls.stream().map(e->e.weight()).toList());
			return Sp.of(weight,me());
		}
	}
	default S solution() {
		return solution(this.targets().stream().map(v->v.solution()).toList());
	}
}
