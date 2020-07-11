package us.lsi.alg.reinas;

import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.AStarRandom;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.EGraph;

public class TestAStarRandom {

	public static void main(String[] args) {
		ReinasVertex.n = 120;
		AStarRandom.threshold = 15;
		ReinasVertex v1 = ReinasVertex.first();
		Predicate<ReinasVertex> goal = v->v.index == ReinasVertex.n;
		
		EGraph<ReinasVertex, ReinasEdge> graph = Graphs2.simpleVirtualGraph(v1);			
		
		AStar<ReinasVertex, ReinasEdge> ms = GraphAlg.aStarRandomGoal(
				graph, 
				goal,  
				(e1,e2,e3)->0.,
				e->ReinasVertex.n-e.index);
		
		GraphPath<ReinasVertex, ReinasEdge> path = ms.pathToEnd();
		System.out.println(SolucionReinas.of(path));

	}

}
