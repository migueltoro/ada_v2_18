package us.lsi.alg.pack;


import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.alg.BackTrackingRandom;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestRandom {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		Data.data("ficheros/pack.txt",12);
		Data.m = Data.n;
		PackVertex e1 = PackVertex.first();
		
		EGraph<PackVertex,PackEdge> graph = 
				SimpleVirtualGraph.last(e1,PackVertex.goal(),v->(double)v.nc);	
		
		GreedyOnGraph<PackVertex,PackEdge> rr = GreedyOnGraph.of(
				graph,
				PackVertex::greedyEdge);
	
		GraphPath<PackVertex, PackEdge> path = rr.path();
		Double bv = path.getWeight();
		System.out.println(bv);
		
		PackVertex.m = bv.intValue();
		
		BackTrackingRandom<PackVertex, PackEdge,SolucionPack> ms = BackTrackingRandom.of(
				graph,
				SolucionPack::of,
				BTType.Min,
				v->v.index);	
		
		BackTrackingRandom.threshold = 12;
		BackTrackingRandom.solutionsNumber =  2;
		
		ms.bestValue = bv;
		ms.optimalPath = path;
		ms.search();

		SolucionPack s1 = ms.getSolution().orElse(null);
		System.out.println(s1);
	}

}
