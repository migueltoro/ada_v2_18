package us.lsi.alg.reinas;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.alg.BackTrackingRandom;
import us.lsi.graphs.virtual.ActionSimpleEdge;
import us.lsi.graphs.virtual.EGraph;


public class TestBTRandom {

	public static void main(String[] args) {
			ReinasVertex.n = 100;
			BackTrackingRandom.threshold = 15;
			BackTrackingRandom.solutionsNumber = 1;
			ReinasVertex e1 = ReinasVertex.first();
			EGraph<ReinasVertex,ActionSimpleEdge<ReinasVertex,Integer>> graph = Graphs2.simpleVirtualGraphLast(e1,v->v.errores().doubleValue());		
			
			BackTrackingRandom<ReinasVertex,ActionSimpleEdge<ReinasVertex,Integer>, SolucionReinas> ms = 
					BT.<ReinasVertex,ActionSimpleEdge<ReinasVertex,Integer>,SolucionReinas>randomGoal(
					graph, 
					v->v.index() == ReinasVertex.n, 
					SolucionReinas::of, 
					ReinasVertex::copy, 
					BTType.One,
					v->ReinasVertex.n-v.index());							
			ms.search();
			System.out.println(ms.iterations);
			System.out.println(ms.getSolution());

	}

}
