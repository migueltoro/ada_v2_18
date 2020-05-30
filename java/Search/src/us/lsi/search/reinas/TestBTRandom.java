package us.lsi.search.reinas;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.search.BTSearch;
import us.lsi.graphs.search.BackTrackingRandom;
import us.lsi.graphs.virtual.EGraph;


public class TestBTRandom {

	public static void main(String[] args) {
			ReinasVertex.n = 100;
			BackTrackingRandom.threshold = 15;
			BackTrackingRandom.solutionsNumber = 1;
			ReinasVertex e1 = ReinasVertex.first();
			EGraph<ReinasVertex, ReinasEdge> graph = Graphs2.last(e1,v->v.errores.doubleValue());		
			
			BackTrackingRandom<ReinasVertex, ReinasEdge, SolucionReinas> ms = BTSearch.<ReinasVertex,ReinasEdge,SolucionReinas>random(
					graph, 
					v->v.index == ReinasVertex.n, 
					null, 
					SolucionReinas::of, 
					ReinasVertex::copy, 
					v->ReinasVertex.n-v.index);							
			ms.search();
			System.out.println(ms.iterations);
			System.out.println(ms.getSolutions());

	}

}
