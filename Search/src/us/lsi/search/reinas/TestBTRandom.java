package us.lsi.search.reinas;

import us.lsi.graphs.search.BTSearch;
import us.lsi.graphs.search.BackTrackingRandom;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;


public class TestBTRandom {

	public static void main(String[] args) {
			ReinasVertex.n = 200;
			BackTrackingRandom.threshold = 15;
			BackTrackingRandom.solutionsNumber = 1;
			ReinasVertex e1 = ReinasVertex.first();
			EGraph<ReinasVertex, ReinasEdge> graph = SimpleVirtualGraph.of(e1);		
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
