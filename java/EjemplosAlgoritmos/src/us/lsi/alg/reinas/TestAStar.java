package us.lsi.alg.reinas;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.ActionSimpleEdge;
import us.lsi.graphs.virtual.EGraph;

public class TestAStar {
	
	public static void main(String[] args) {
		ReinasVertex.n = 10;	
		ReinasVertex v1 = ReinasVertex.first();
		EGraph<ReinasVertex,ActionSimpleEdge<ReinasVertex,Integer>> graph = Graphs2.simpleVirtualGraph(v1);			
		
		AStar<ReinasVertex, ActionSimpleEdge<ReinasVertex,Integer>> ms = GraphAlg.aStar(
				graph, 
				ReinasVertex.goal(),  
				ReinasVertex.last(),
				(e1,e2,e3)->0.);
		
		GraphPath<ReinasVertex, ActionSimpleEdge<ReinasVertex,Integer>> path = ms.search().orElse(null);
		System.out.println(SolucionReinas.of(path));
	}
}
