package us.lsi.alg.reinas;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.AStarRandom;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.ActionSimpleEdge;
import us.lsi.graphs.virtual.EGraph;

public class TestAStarRandom {

	public static void main(String[] args) {
		ReinasVertex.n = 110;
		AStarRandom.threshold = 15;
		ReinasVertex v1 = ReinasVertex.first();
		
		EGraph<ReinasVertex,ActionSimpleEdge<ReinasVertex,Integer>> graph = Graphs2.simpleVirtualGraph(v1);			
		
		AStar<ReinasVertex,ActionSimpleEdge<ReinasVertex,Integer>> ms = GraphAlg.aStarRandom(
				graph, 
				ReinasVertex.goal(), 
				ReinasVertex.last(),
				(e1,e2,e3)->0.,
				e->ReinasVertex.n-e.index());
		
		GraphPath<ReinasVertex,ActionSimpleEdge<ReinasVertex,Integer>> path = ms.search().orElse(null);
		Integer n = 0;
		while(path == null) {
			ms = GraphAlg.aStarRandom(
					graph, 
					ReinasVertex.goal(), 
					ReinasVertex.last(),
					(e1,e2,e3)->0.,
					e->ReinasVertex.n-e.index());
			path = ms.search().orElse(null);
			System.out.println(n);
			n++;
		}
		System.out.println(SolucionReinas.of(path));
		System.out.println(n);

	}

}
