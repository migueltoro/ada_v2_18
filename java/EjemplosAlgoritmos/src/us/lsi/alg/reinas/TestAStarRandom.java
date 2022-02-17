package us.lsi.alg.reinas;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.AstarRandom;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.SimpleVirtualGraph;
import us.lsi.graphs.virtual.EGraph;

public class TestAStarRandom {

	public static void main(String[] args) {
		ReinasVertex.n = 110;
		AstarRandom.threshold = 15;
		ReinasVertex v1 = ReinasVertex.first();
		
		
		EGraph<ReinasVertex,SimpleEdgeAction<ReinasVertex,Integer>> graph = 
				SimpleVirtualGraph.last(v1,ReinasVertex.goal(),
						v->v.errores().doubleValue(),ReinasVertex.constraint());			
		
		AStar<ReinasVertex,SimpleEdgeAction<ReinasVertex,Integer>> ms = AstarRandom.of(
				graph, 
				(e1,e2,e3)->0.,
				AStarType.Min,
				e->ReinasVertex.n-e.index());
		
		GraphPath<ReinasVertex,SimpleEdgeAction<ReinasVertex,Integer>> path = ms.search().get();
		Integer n = 0;
		while(path == null) {
			ms = AstarRandom.of(
					graph, 
					(e1,e2,e3)->0.,
					AStarType.Min,
					e->ReinasVertex.n-e.index());
			path = ms.search().orElse(null);
			System.out.println(n);
			n++;
		}
		System.out.println(SolucionReinas.of(path));
		System.out.println(n);

	}

}
