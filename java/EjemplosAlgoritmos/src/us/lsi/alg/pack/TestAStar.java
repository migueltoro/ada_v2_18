package us.lsi.alg.pack;


import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;


public class TestAStar {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		Data.data("ficheros/pack.txt",15);
		Data.m = Data.n;
		PackVertex e1 = PackVertex.first();
		
		EGraph<PackVertex,PackEdge> graph = 
				SimpleVirtualGraph.last(e1,PackVertex.goal(),v->(double)v.nc);	
		
		GreedyOnGraph<PackVertex,PackEdge> rr = GreedyOnGraph.of(
				graph,
				PackVertex::greedyEdge);
	
		GraphPath<PackVertex, PackEdge> p = rr.path();
		SolucionPack sp = SolucionPack.of(p);
		System.out.println(sp);
		PackVertex.m = sp.nc();
		
		AStar<PackVertex, PackEdge> ms = 
				AStar.of(graph,(v1,pd,v2)->0.,AStarType.Min);
//		AStar<PackVertex, PackEdge> ms = GraphAlg.aStarGoal(graph,goal,Heuristica::heuristic);
		
		GraphPath<PackVertex,PackEdge> path = ms.search().orElse(null);
		SolucionPack s = SolucionPack.of(path);
		System.out.println(s);

	}

}
