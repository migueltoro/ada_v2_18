package us.lsi.alg.reinas;


import java.util.function.Predicate;

import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.SimpleVirtualGraph;
import us.lsi.graphs.virtual.EGraph;

public class TestBT {

	public static void main(String[] args) {
		ReinasVertex.n = 8;
		ReinasVertex e1 = ReinasVertex.first();
		Predicate<ReinasVertex> goal = v -> v.index() == ReinasVertex.n;

		SimpleVirtualGraph.constraintG = ReinasVertex.constraint();
		EGraph<ReinasVertex,SimpleEdgeAction<ReinasVertex,Integer>> graph = 
				SimpleVirtualGraph.last(e1,goal,v->v.errores().doubleValue());

		BackTracking<ReinasVertex,SimpleEdgeAction<ReinasVertex,Integer>, SolucionReinas> ms = 
				BackTracking.of(graph, 
				(v1, p, v2) -> 0.,
				SolucionReinas::of,  
				BTType.All);

		ms.search();
		System.out.println(ms.getSolutions());

	}
}
