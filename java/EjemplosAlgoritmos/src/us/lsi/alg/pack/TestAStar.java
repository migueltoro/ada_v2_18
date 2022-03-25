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
		PackVertex e1 = PackVertex.first();
		
		EGraph<PackVertex,PackEdge> graph = 
				SimpleVirtualGraph.last(e1,PackVertex.goal(),v->(double)v.nc());	
		
		GreedyOnGraph<PackVertex,PackEdge> rr = GreedyOnGraph.of(
				graph,
				PackVertex::greedyEdge);
	
		GraphPath<PackVertex, PackEdge> p = rr.path();
		SolucionPack sp = SolucionPack.of(p);
		System.out.println(sp);
		
		AStar<PackVertex, PackEdge> ms = 
				AStar.of(graph,
						Heuristica::heuristic,
						AStarType.Min);
		
		GraphPath<PackVertex,PackEdge> path = ms.search().orElse(null);
		SolucionPack s = SolucionPack.of(path);
		System.out.println(s);

	}

}
