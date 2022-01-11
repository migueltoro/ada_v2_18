package us.lsi.alg.reinas;


import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.alg.BackTrackingRandom;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.SimpleVirtualGraph;
import us.lsi.graphs.virtual.EGraph;


public class TestBTRandom {

	public static void main(String[] args) {
			ReinasVertex.n = 110;
			BackTrackingRandom.threshold = 15;
			BackTrackingRandom.solutionsNumber = 1;
			ReinasVertex e1 = ReinasVertex.first();
			
			SimpleVirtualGraph.constraintG = ReinasVertex.constraint();
			EGraph<ReinasVertex,SimpleEdgeAction<ReinasVertex,Integer>> graph = 
					SimpleVirtualGraph.last(e1,ReinasVertex.goal(),v->v.errores().doubleValue());		
			
			BackTrackingRandom<ReinasVertex,SimpleEdgeAction<ReinasVertex,Integer>, SolucionReinas> ms = 
					BackTrackingRandom.of(
					graph, 
					SolucionReinas::of, 
					BTType.One, 
					v->ReinasVertex.n-v.index());	
			
			long startTime = System.nanoTime();
			ms.search();
			System.out.println("Iteraciones = "+ms.iterations);
			long endTime = System.nanoTime() - startTime;
			System.out.println("1 = "+endTime);
			System.out.println(ms.getSolution().get());

	}

}
