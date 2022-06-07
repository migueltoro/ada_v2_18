package us.lsi.alg.reinas;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.path.EGraphPath.PathType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;

public class TestAStar {
	
	public static void main(String[] args) {
		ReinasVertex.n = 10;	
		ReinasVertex v1 = ReinasVertex.first();
		
		EGraph<ReinasVertex,SimpleEdgeAction<ReinasVertex,Integer>> graph = 
				EGraph.virtual(v1,ReinasVertex.goal(), PathType.Last, Type.Min)
				.goalHasSolution(ReinasVertex.goalHasSolution())
				.vertexWeight(v->v.errores().doubleValue())
				.build();			
		
		AStar<ReinasVertex, SimpleEdgeAction<ReinasVertex,Integer>> ms = AStar.of(graph);
		
		GraphPath<ReinasVertex, SimpleEdgeAction<ReinasVertex,Integer>> path = ms.search().orElse(null);
		System.out.println(SolucionReinas.of(path));
	}
}
