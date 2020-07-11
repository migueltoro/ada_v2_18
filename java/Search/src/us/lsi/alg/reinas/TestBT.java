package us.lsi.alg.reinas;


import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.EGraph;

public class TestBT {

	
	public static void main(String[] args) {
		ReinasVertex.n = 8;	
		ReinasVertex e1 = ReinasVertex.first();
		EGraph<ReinasVertex, ReinasEdge> graph = Graphs2.simpleVirtualGraphLast(e1,v->v.errores.doubleValue());	
		
		BT<ReinasVertex, ReinasEdge, SolucionReinas> ms = BT.backTrackingGoal(
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
