package us.lsi.alg.reinas;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.AstarRandom;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;
import us.lsi.graphs.virtual.EGraph;

public class TestAStarRandom {

	public static void main(String[] args) {
		ReinasVertex.n = 110;
		AstarRandom.threshold = 15;
		ReinasVertex v1 = ReinasVertex.first();
		
		EGraph<ReinasVertex,SimpleEdgeAction<ReinasVertex,Integer>> graph = 
				EGraph.virtual(v1,ReinasVertex.goal(), PathType.Last, Type.Min)
				.goalHasSolution(ReinasVertex.goalHasSolution())
				.vertexWeight(v->v.errores().doubleValue())
				.build();			
		
		AStar<ReinasVertex,SimpleEdgeAction<ReinasVertex,Integer>> ms = AstarRandom.of(
				graph,
				e->ReinasVertex.n-e.index());
		
		GraphPath<ReinasVertex,SimpleEdgeAction<ReinasVertex,Integer>> path = ms.search().get();
		Integer n = 0;
		while(path == null) {
			ms = AstarRandom.of(
					graph, 
					e->ReinasVertex.n-e.index());
			path = ms.search().orElse(null);
			System.out.println(n);
			n++;
		}
		System.out.println(SolucionReinas.of(path));
		System.out.println(n);

	}

}
