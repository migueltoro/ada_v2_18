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
		Data.data("ficheros/pack1.txt");
		PackVertex e1 = PackVertex.first();
		
		EGraph<PackVertex,PackEdge> graph = 
				SimpleVirtualGraph.sum(e1,PackVertex.goal(),e->e.weight());	
		
		GreedyOnGraph<PackVertex,PackEdge> rr = GreedyOnGraph.of(
				graph,
				PackVertex::greedyEdge);
	
		GraphPath<PackVertex, PackEdge> p = rr.path();
		SolucionPack sp = SolucionPack.of(p);
		System.out.println("Solucion Voraz = "+sp);
		System.out.println("Heuristica = "+Heuristica.heuristic(e1, PackVertex.goal(), null));
		
		AStar<PackVertex, PackEdge> ms = 
				AStar.of(graph,
						Heuristica::heuristic,
						AStarType.Min);
		
		GraphPath<PackVertex,PackEdge> path = ms.search().orElse(null);
		SolucionPack s = SolucionPack.of(path);
		System.out.println(String.format("Volumen contenedor = %d,Numero de Objetos = %d",
				Data.volumenContenedor,Data.n));
		System.out.println(s);
		
	}

}
