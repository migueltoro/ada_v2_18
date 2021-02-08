package us.lsi.alg.floyd;

import java.util.List;
import java.util.function.Function;

import us.lsi.alg.floyd.FloydVertex.ActionFloyd;
import us.lsi.hypergraphs.SimpleHyperEdge;

public class FloydEdge extends SimpleHyperEdge<FloydVertex,ActionFloyd>{
	
	public static FloydEdge of(FloydVertex source, List<FloydVertex> targets,
			ActionFloyd action) {
		return new FloydEdge(source, targets, action, null);
	}

	private FloydEdge(FloydVertex source, List<FloydVertex> targets, ActionFloyd action, Double weight) {
		super(source, targets, action, weight);
	}

	public Double getWeight(Function<FloydVertex,Double> sol) {
		Double weight = null;
		switch(super.action) {
		case No: weight = sol.apply(super.targets.get(0)); break;
		case Yes: weight = sol.apply(super.targets.get(0))+sol.apply(super.targets.get(1));		
		}
		return weight;
	}
	
}
	
