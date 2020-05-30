package us.lsi.search.reinas;

import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.search.AStarSearch;
import us.lsi.graphs.search.AStarSearchRandom;
import us.lsi.graphs.search.GSearch;
import us.lsi.graphs.virtual.EGraph;

public class TestAStarRandom {

	public static void main(String[] args) {
		ReinasVertex.n = 120;
		AStarSearchRandom.threshold = 15;
		ReinasVertex v1 = ReinasVertex.first();
		Predicate<ReinasVertex> goal = v->v.index == ReinasVertex.n;
		
		EGraph<ReinasVertex, ReinasEdge> graph = Graphs2.sum(v1);			
		
		AStarSearch<ReinasVertex, ReinasEdge> ms = GSearch.aStarRandomGoal(
				graph, 
				goal,  
				(e1,e2,e3)->0.,
				e->ReinasVertex.n-e.index);
		
		GraphPath<ReinasVertex, ReinasEdge> path = ms.pathToEnd();
		System.out.println(SolucionReinas.of(path));

	}

}
