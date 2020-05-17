package us.lsi.search.floyd;

import java.util.List;
import java.util.function.Function;

import us.lsi.hypergraphs.SimpleHyperEdge;
import us.lsi.search.floyd.FloydVertex.ActionFloyd;

public class FloydEdge<V,E> extends SimpleHyperEdge<FloydVertex<V,E>,ActionFloyd>{
	
	public static <V, E> FloydEdge<V, E> of(FloydVertex<V, E> source, List<FloydVertex<V, E>> targets,
			ActionFloyd action) {
		return new FloydEdge<V, E>(source, targets, action, null);
	}

	private FloydEdge(FloydVertex<V, E> source, List<FloydVertex<V, E>> targets, ActionFloyd action, Double weight) {
		super(source, targets, action, weight);
	}

	public Double getWeight(Function<FloydVertex<V, E>,Double> sol) {
		Double weight = null;
		switch(action) {
		case No: weight = sol.apply(targets.get(0)); break;
		case Yes: weight = sol.apply(targets.get(0))+sol.apply(targets.get(1)); break;
		}
		return weight;
	}
	
}
	
