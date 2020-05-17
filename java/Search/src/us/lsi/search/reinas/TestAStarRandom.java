package us.lsi.search.reinas;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.search.AStarSearchRandom;
import us.lsi.graphs.search.GSearch;
import us.lsi.graphs.virtual.EGraph;

public class TestAStarRandom {
	
	public static void main(String[] args) {
		ReinasVertex.n = 120;	
		AStarSearchRandom.threshold = 15;
		ReinasVertex v1 = ReinasVertex.first();
		EGraph<ReinasVertex, ReinasEdge> graph = Graphs2.last(v1,v->v.errores.doubleValue());			
		
		AStarSearchRandom<ReinasVertex, ReinasEdge> ms = GSearch.aStarRandomGoal(
				graph, 
				v->v.index == ReinasVertex.n,  
				(e1,e2,e3)->0., 
				v->v.errores);
		
		GraphPath<ReinasVertex, ReinasEdge> path = ms.pathToEnd();
		System.out.println(AStarSearchRandom.iterations);
		System.out.println(SolucionReinas.of(path));
	}

}
