package us.lsi.alg.reinas;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.alg.BackTrackingRandom;
import us.lsi.graphs.virtual.ActionSimpleEdge;
import us.lsi.graphs.virtual.EGraph;


public class TestBTRandom {

	public static void main(String[] args) {
			ReinasVertex.n = 110;
			BackTrackingRandom.threshold = 15;
			BackTrackingRandom.solutionsNumber = 1;
			ReinasVertex e1 = ReinasVertex.first();
			EGraph<ReinasVertex,ActionSimpleEdge<ReinasVertex,Integer>> graph = Graphs2.simpleVirtualGraphLast(e1,v->v.errores().doubleValue());		
			
			BackTrackingRandom<ReinasVertex,ActionSimpleEdge<ReinasVertex,Integer>, SolucionReinas> ms = 
					BT.<ReinasVertex,ActionSimpleEdge<ReinasVertex,Integer>,SolucionReinas>random(
					graph, 
					v->v.index() == ReinasVertex.n, 
					null,
					SolucionReinas::of, 
					ReinasVertex::copy, 
					BTType.One,
					v->ReinasVertex.n-v.index());	
			long startTime = System.nanoTime();
			ms.search();
			System.out.println("Iteraciones = "+ms.iterations);
			long endTime = System.nanoTime() - startTime;
			System.out.println("1 = "+endTime);
			System.out.println(ms.getSolution());

	}

}
