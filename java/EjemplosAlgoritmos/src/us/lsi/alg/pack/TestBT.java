package us.lsi.alg.pack;


import java.util.Locale;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;


import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearch;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.EGraph;

public class TestBT {

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
	
		GraphPath<PackVertex, PackEdge> path = rr.search();
		SolucionPack sp = SolucionPack.of(path);
	
		PackVertex.m = sp.nc();
		
		BackTracking<PackVertex, PackEdge,SolucionPack> ms = BT.backTrackingGoal(
				graph,
				goal,
				Heuristica::heuristic,
				SolucionPack::of,
				PackVertex::copy,
				BTType.Min);	
		
		ms.bestValue = PackVertex.m.doubleValue();
		ms.solutions.add(sp);
		
		ms.search();

		System.out.println(ms.solutions);
		System.out.println(sp);
	}

}
