package us.lsi.alg.monedas;



import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;


public class TestMonedasPDR {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		MonedaVertex.datosIniciales("ficheros/monedas2.txt", 104);

		MonedaVertex e1 = MonedaVertex.first();
		
		EGraph<MonedaVertex, MonedaEdge> graph = 
				SimpleVirtualGraph.sum(e1,MonedaVertex.goal(),e->e.weight(),MonedaVertex.constraint());

		GreedyOnGraph<MonedaVertex, MonedaEdge> rr = GreedyOnGraph.of(graph, MonedaVertex::aristaVoraz);
		
		GraphPath<MonedaVertex, MonedaEdge> path1 = rr.path();
		
		DynamicProgrammingReduction<MonedaVertex, MonedaEdge> ms1 = DynamicProgrammingReduction.of(
				graph, 
				MonedasHeuristica::heuristic, 
				PDType.Max);

		if (rr.isSolution(path1)) {
			System.out.println("1 = " + SolucionMonedas.of(path1));
			ms1.bestValue = path1.getWeight();
			ms1.optimalPath = path1;
		}
		
		Optional<GraphPath<MonedaVertex, MonedaEdge>> s1 = ms1.search();
		
		if (s1.isPresent()) System.out.println("2 = " + SolucionMonedas.of(s1.get()));
		else System.out.println("2 = No hay solucion");

		Collections.sort(Moneda.monedas, Comparator.comparing(m -> m.pesoUnitario()));

		MonedaVertex e3 = MonedaVertex.first();
		
		graph = SimpleVirtualGraph.sum(e3,MonedaVertex.goal(),e->e.weight(),MonedaVertex.constraint());

		rr = GreedyOnGraph.of(graph, MonedaVertex::aristaVoraz);
		
		GraphPath<MonedaVertex, MonedaEdge> path2 = rr.path();

		DynamicProgrammingReduction<MonedaVertex, MonedaEdge> ms2 = DynamicProgrammingReduction.of(
				graph, 
				MonedasHeuristica::heuristic, 
				PDType.Min);

		if (rr.isSolution(path2)) {
			System.out.println("3 = " + SolucionMonedas.of(path2));
			ms2.bestValue = path2.getWeight();
			ms2.optimalPath = path2;
		}
		Optional<GraphPath<MonedaVertex, MonedaEdge>> s2 = ms2.search();
		if (s2.isPresent()) System.out.println("4 = " + SolucionMonedas.of(s2.get()));
		else System.out.println("4 = No hay solucion");

	}
}
