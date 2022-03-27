package us.lsi.alg.pack;


import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestBT {

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
		System.out.println("Valor voraz = "+nc);
		System.out.println("Heuristica = "+Heuristica.heuristic(e1, PackVertex.goal(), null));
		
		BackTracking<PackVertex, PackEdge,SolucionPack> ms = BackTracking.of(
				graph,
				Heuristica::heuristic,
				SolucionPack::of,
				BTType.Min);	
		
		ms.bestValue = (double) nc;
		ms.optimalPath = path;
		
		System.out.println(String.format("Volumen contenedor = %d,Numero de Objetos = %d",
				Data.volumenContenedor,Data.n));
		
		ms.search();
		
		System.out.println(sp);
	}

}
