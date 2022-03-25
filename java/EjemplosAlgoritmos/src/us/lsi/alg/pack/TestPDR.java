package us.lsi.alg.pack;

import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestPDR {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		Data.data("ficheros/pack.txt",15);
		PackVertex e1 = PackVertex.first();
		
		EGraph<PackVertex,PackEdge> graph = 
				SimpleVirtualGraph.last(e1,PackVertex.goal(),v->(double)v.nc());	
		
		GreedyOnGraph<PackVertex,PackEdge> rr = GreedyOnGraph.of(
				graph,
				PackVertex::greedyEdge);
	
		GraphPath<PackVertex, PackEdge> path = rr.path();
		SolucionPack sp = SolucionPack.of(path);
	
		Integer nc = sp.nc();
		System.out.println(nc);
		
		DynamicProgrammingReduction<PackVertex, PackEdge> ms = DynamicProgrammingReduction.of(
				graph,
				Heuristica::heuristic,
				PDType.Min);	
		
		ms.bestValue = nc.doubleValue();
		ms.optimalPath = path;
		
		ms.search();

		System.out.println(sp);

	}

}
