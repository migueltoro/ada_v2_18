package us.lsi.search.reinas;


import us.lsi.graphs.search.BTSearch;
import us.lsi.graphs.search.BackTracking.BTType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestBT {

	
	public static void main(String[] args) {
		ReinasVertex.n = 8;	
		ReinasVertex e1 = ReinasVertex.first();
		EGraph<ReinasVertex, ReinasEdge> graph = SimpleVirtualGraph.of(e1);	
		
		BTSearch<ReinasVertex, ReinasEdge, SolucionReinas> ms = BTSearch.backTrackingGoal(
				graph, 
				v->v.index == ReinasVertex.n, 
				(v1,p,v2)->0.,
				SolucionReinas::of,
				ReinasVertex::copy, 
				BTType.All);					
		ms.search();
		System.out.println(ms.getSolutions());

}
}
