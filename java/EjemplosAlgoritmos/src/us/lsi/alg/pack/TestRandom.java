package us.lsi.alg.pack;


import java.util.Locale;
import java.util.function.Predicate;


import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearchOnGraph;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.alg.BackTrackingRandom;
import us.lsi.graphs.virtual.EGraph;

public class TestRandom {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		Data.data("ficheros/pack.txt",12);
		Data.m = Data.n;
		PackVertex e1 = PackVertex.first();
		Predicate<PackVertex> goal  = PackVertex.goal();
		
		EGraph<PackVertex,PackEdge> graph = Graphs2.simpleVirtualGraphLast(e1,v->(double)v.nc);	
		
		GreedySearchOnGraph<PackVertex,PackEdge> rr = GraphAlg.greedy(
				graph,
				PackVertex::greedyEdge,
				goal,
				v->true);
				
		Double bv = rr.weightToEnd().orElse(null);
		System.out.println(bv);
		
		PackVertex.m = bv.intValue();
		
		BackTrackingRandom<PackVertex, PackEdge,SolucionPack> ms = BT.randomGoal(
				graph,
				goal,
				SolucionPack::of,
				PackVertex::copy,
				BTType.Min,
				v->v.index);	
		
		BackTrackingRandom.threshold = 12;
		BackTrackingRandom.solutionsNumber =  2;
		
		ms.bestValue = bv;
		ms.search();

		SolucionPack s1 = ms.getSolution().orElse(null);
		System.out.println(s1);
	}

}
