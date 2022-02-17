package us.lsi.alg.reinas;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.SimpleVirtualGraph;
import us.lsi.graphs.virtual.EGraph;

public class TestAStar {
	
	public static void main(String[] args) {
		ReinasVertex.n = 10;	
		ReinasVertex v1 = ReinasVertex.first();
		
		EGraph<ReinasVertex,SimpleEdgeAction<ReinasVertex,Integer>> 
			graph = SimpleVirtualGraph.last(v1,ReinasVertex.goal(),
					v->v.errores().doubleValue(),ReinasVertex.constraint());			
		
		AStar<ReinasVertex, SimpleEdgeAction<ReinasVertex,Integer>> ms = AStar.of(
				graph, 
				(e1,e2,e3)->0.,
				AStarType.Min);
		
		GraphPath<ReinasVertex, SimpleEdgeAction<ReinasVertex,Integer>> path = ms.search().orElse(null);
		System.out.println(SolucionReinas.of(path));
	}
}
