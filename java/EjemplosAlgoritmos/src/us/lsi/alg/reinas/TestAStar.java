package us.lsi.alg.reinas;


import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.EGraph;

public class TestAStar {
	
	public static void main(String[] args) {
		ReinasVertex.n = 10;	
		ReinasVertex v1 = ReinasVertex.first();
		EGraph<ReinasVertex, ReinasEdge> graph = Graphs2.simpleVirtualGraph(v1);			
		
		AStar<ReinasVertex, ReinasEdge> ms = GraphAlg.aStarGoal(
				graph, 
				v->v.index == ReinasVertex.n,  
				(e1,e2,e3)->0.);
		
		GraphPath<ReinasVertex, ReinasEdge> path = ms.search();
		System.out.println(SolucionReinas.of(path));
	}

}
