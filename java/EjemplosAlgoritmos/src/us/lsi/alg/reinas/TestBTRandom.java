package us.lsi.alg.reinas;


import java.util.Optional;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.BackTrackingRandom;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;
import us.lsi.graphs.virtual.EGraph;


public class TestBTRandom {

	public static void main(String[] args) {
			ReinasVertex.n = 110;
			BackTrackingRandom.threshold = 15;
			ReinasVertex e1 = ReinasVertex.first();
			
			EGraph<ReinasVertex,SimpleEdgeAction<ReinasVertex,Integer>> graph = 
					EGraph.virtual(e1,ReinasVertex.goal(), PathType.Last, Type.One)
					.goalHasSolution(ReinasVertex.goalHasSolution())
					.vertexWeight(v->v.errores().doubleValue())
					.build();		
			
			BackTrackingRandom<ReinasVertex,SimpleEdgeAction<ReinasVertex,Integer>, SolucionReinas> ms = 
					BackTrackingRandom.of(
					graph, 
					SolucionReinas::of, 
					v->ReinasVertex.n-v.index());	
			
			long startTime = System.nanoTime();
			Optional<GraphPath<ReinasVertex, SimpleEdgeAction<ReinasVertex, Integer>>> gp = ms.search();
			System.out.println("Iteraciones = "+ms.iterations);
			long endTime = System.nanoTime() - startTime;
			System.out.println("1 = "+endTime);
			System.out.println(SolucionReinas.of(gp.get()));

	}

}
