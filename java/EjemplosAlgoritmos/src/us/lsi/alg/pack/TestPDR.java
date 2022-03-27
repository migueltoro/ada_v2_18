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
		Data.data("ficheros/pack1.txt");
		PackVertex e1 = PackVertex.first();
		
		EGraph<PackVertex,PackEdge> graph = 
				SimpleVirtualGraph.sum(e1,PackVertex.goal(),e->e.weight());	
		
		GreedyOnGraph<PackVertex,PackEdge> rr = GreedyOnGraph.of(
				graph,
				PackVertex::greedyEdge);
	
		GraphPath<PackVertex, PackEdge> path = rr.path();
		SolucionPack sp = SolucionPack.of(path);
	
		Integer nc = sp.nc();
		System.out.println("Valor Voraz = "+nc);
		System.out.println("Heuristica = "+Heuristica.heuristic(e1, PackVertex.goal(), null));
		
		DynamicProgrammingReduction<PackVertex, PackEdge> ms = DynamicProgrammingReduction.of(
				graph,
				Heuristica::heuristic,
				PDType.Min);	
		
		ms.bestValue = nc.doubleValue();
		ms.optimalPath = path;
		
		ms.search();
		System.out.println(String.format("Volumen contenedor = %d,Numero de Objetos = %d",
				Data.volumenContenedor,Data.n));
		System.out.println(sp);

	}

}
