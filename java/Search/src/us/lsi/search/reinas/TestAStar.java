package us.lsi.search.reinas;


import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.search.AStarSearch;
import us.lsi.graphs.search.GSearch;
import us.lsi.graphs.virtual.EGraph;

public class TestAStar {
	
	public static void main(String[] args) {
		ReinasVertex.n = 10;	
		ReinasVertex v1 = ReinasVertex.first();
		EGraph<ReinasVertex, ReinasEdge> graph = Graphs2.sum(v1);			
		
		AStarSearch<ReinasVertex, ReinasEdge> ms = GSearch.aStarGoal(
				graph, 
				v->v.index == ReinasVertex.n,  
				(e1,e2,e3)->0.);
		
		GraphPath<ReinasVertex, ReinasEdge> path = ms.pathToEnd();
		System.out.println(SolucionReinas.of(path));
	}

}
