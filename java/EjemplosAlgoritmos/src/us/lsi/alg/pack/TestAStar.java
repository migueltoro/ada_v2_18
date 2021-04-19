package us.lsi.alg.pack;


import java.util.Locale;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearch;
import us.lsi.graphs.virtual.EGraph;


public class TestAStar {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		Data.data("ficheros/pack.txt",15);
		Data.m = Data.n;
		PackVertex e1 = PackVertex.first;
		Predicate<PackVertex> goal  = PackVertex.goal;
		
		EGraph<PackVertex,PackEdge> graph = Graphs2.simpleVirtualGraphLast(e1,v->(double)v.nc);	
		
		GreedySearch<PackVertex,PackEdge> rr = GraphAlg.greedy(
				graph,
				PackVertex::greedyEdge,
				goal);
	
		GraphPath<PackVertex, PackEdge> p = rr.search().orElse(null);
		SolucionPack sp = SolucionPack.of(p);
		System.out.println(sp);
		PackVertex.m = sp.nc();
		
		AStar<PackVertex, PackEdge> ms = GraphAlg.aStarGoal(graph,goal,(v1,pd,v2)->0.);
//		AStar<PackVertex, PackEdge> ms = GraphAlg.aStarGoal(graph,goal,Heuristica::heuristic);
		
		GraphPath<PackVertex,PackEdge> path = ms.search().orElse(null);
		SolucionPack s = SolucionPack.of(path);
		System.out.println(s);

	}

}
