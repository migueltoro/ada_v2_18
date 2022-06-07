package us.lsi.alg.reinas;


import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;
import us.lsi.graphs.virtual.EGraph;

public class TestBT {

	public static void main(String[] args) {
		ReinasVertex.n = 8;
		ReinasVertex e1 = ReinasVertex.first();
		
		EGraph<ReinasVertex,SimpleEdgeAction<ReinasVertex,Integer>> graph = 
				EGraph.virtual(e1,ReinasVertex.goal(), PathType.Last, Type.All)
				.goalHasSolution(ReinasVertex.goalHasSolution())
				.vertexWeight(v->v.errores().doubleValue())
				.build();	

		BackTracking<ReinasVertex,SimpleEdgeAction<ReinasVertex,Integer>, SolucionReinas> ms = 
				BackTracking.of(graph, 
				SolucionReinas::of);

		ms.search();
		System.out.println(ms.getSolutions());

	}
}
