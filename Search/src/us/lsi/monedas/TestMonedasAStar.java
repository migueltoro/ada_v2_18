package us.lsi.monedas;

import java.util.List;
import java.util.function.BiFunction;

import us.lsi.astar.AStarAlgorithm;
import us.lsi.astar.AStarGraph;
import us.lsi.astar.AStarSimpleVirtualGraph;

public class TestMonedasAStar {
	
	public static void main(String[] args) {

		MonedasVertex.datosIniciales("ficheros/monedas.txt", 500);

		MonedasVertex e1 = MonedasVertex.first();
		MonedasVertex e2 = MonedasVertex.last();

		BiFunction<MonedasVertex, MonedasVertex, Double> heuristic = (v1, v2) -> -Heuristica.heuristic(v1, v2);

		AStarGraph<MonedasVertex, MonedasEdge> graph = AStarSimpleVirtualGraph.create(e1, e -> -e.getEdgeWeight());

		AStarAlgorithm<MonedasVertex, MonedasEdge> a = AStarAlgorithm.of(graph, e1, e2, heuristic);

		List<MonedasEdge> edges = a.getPathEdgeList();

		System.out.println(edges);

		SolucionMonedas s = SolucionMonedas.getSolucion(edges);
		
		System.out.println(s);
	}
}
