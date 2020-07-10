package us.lsi.monedas;

import java.util.List;
import java.util.function.Predicate;
import us.lsi.common.TriFunction;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GSearch;
import us.lsi.graphs.virtual.EGraph;


public class TestMonedasAStar {
	
	public static void main(String[] args) {

		MonedasVertex.datosIniciales("ficheros/monedas.txt", 500);

		MonedasVertex e1 = MonedasVertex.first();
		MonedasVertex e2 = MonedasVertex.last();

		TriFunction<MonedasVertex, Predicate<MonedasVertex>,MonedasVertex, Double> heuristic = 
				(v1, p, v2) -> - Heuristica.heuristic(v1,p,v2);

		EGraph<MonedasVertex, MonedasEdge> graph = Graphs2.simpleVirtualGraph(e1,e ->-e.getEdgeWeight());

		AStar<MonedasVertex, MonedasEdge> a = GSearch.aStarEnd(graph, e2, heuristic);

		List<MonedasEdge> edges = a.pathToEnd().getEdgeList();

		System.out.println(edges);

		SolucionMonedas s = SolucionMonedas.getSolucion(edges);
		
		System.out.println(s);
	}
}
