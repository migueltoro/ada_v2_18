package us.lsi.alg.matrices;


import java.util.List;
import java.util.function.Function;

import us.lsi.hypergraphs.SimpleHyperEdge;

public class MatrixEdge extends SimpleHyperEdge<MatrixVertex,Integer> {
	
	public static MatrixEdge of(MatrixVertex source, List<MatrixVertex> targets, Integer action) {
		return new MatrixEdge(source, targets, action,null);
	}

	public static MatrixEdge of(MatrixVertex source, List<MatrixVertex> targets, Integer action, Double weight) {
		return new MatrixEdge(source, targets, action, weight);
	}

	private MatrixEdge(MatrixVertex source, List<MatrixVertex> targets, Integer action, Double weight) {
		super(source, targets, action, weight);
	}

	@Override
	public Double getWeight(Function<MatrixVertex, Double> sol) {		
		Double weight = sol.apply(targets.get(0))+sol.apply(targets.get(1));
		Integer i = targets.get(0).i;
		Integer a = targets.get(0).j;
		Integer j = targets.get(1).j;
		weight += MatrixVertex.matrices.get(i).nf*MatrixVertex.matrices.get(a).nf*MatrixVertex.matrices.get(j-1).nc;
		return weight;
	}

	
}
