package us.lsi.alg.pack;


import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestBT {

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
	
		GraphPath<PackVertex, PackEdge> path = rr.path();
		SolucionPack sp = SolucionPack.of(path);
	
		PackVertex.m = sp.nc();
		
		BackTracking<PackVertex, PackEdge,SolucionPack> ms = BackTracking.of(
				graph,
				Heuristica::heuristic,
				SolucionPack::of,
				BTType.Min);	
		
		ms.bestValue = PackVertex.m.doubleValue();
		ms.optimalPath = path;
		
		ms.search();

		System.out.println(ms.solutions);
		System.out.println(sp);
	}

}
