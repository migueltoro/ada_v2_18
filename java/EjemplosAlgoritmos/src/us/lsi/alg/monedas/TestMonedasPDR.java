package us.lsi.alg.monedas;



import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;


import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearchOnGraph;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;


public class TestMonedasPDR {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		MonedaVertex.datosIniciales("ficheros/monedas3.txt", 36);

		MonedaVertex e1 = MonedaVertex.first();
		MonedaVertex e2 = MonedaVertex.last();

		EGraph<MonedaVertex, MonedaEdge> graph = 
				Graphs2.simpleVirtualGraphSum(e1,MonedaVertex.goal(),e2,MonedaVertex.constraint());

		GreedySearchOnGraph<MonedaVertex, MonedaEdge> rr = GraphAlg.greedy(graph, MonedaVertex::accionVoraz);
		

		EGraphPath<MonedaVertex, MonedaEdge> path1 = rr.search().orElse(null);
		
		DynamicProgrammingReduction<MonedaVertex, MonedaEdge> ms1 = DPR.dynamicProgrammingReduction(
				graph, 
				MonedasHeuristica::heuristic, 
				PDType.Max);

		if (path1 != null) {
			System.out.println("1 = " + SolucionMonedas.of(path1));
			ms1.bestValue = path1.getWeight();
			ms1.solutionPath = path1;
		}
		
		Optional<GraphPath<MonedaVertex, MonedaEdge>> s1 = ms1.search();
		
		if (s1.isPresent()) System.out.println("2 = " + SolucionMonedas.of(s1.get()));
		else System.out.println("2 = No hay solucion");

		Collections.sort(Moneda.monedas, Comparator.comparing(m -> m.pesoUnitario()));

		MonedaVertex e3 = MonedaVertex.first();
		MonedaVertex e4 = MonedaVertex.last();

		graph = Graphs2.simpleVirtualGraphSum(e3,MonedaVertex.goal(),e4,MonedaVertex.constraint());

		rr = GraphAlg.greedy(graph, MonedaVertex::accionVoraz);
		
		EGraphPath<MonedaVertex, MonedaEdge> path2 = rr.search().orElse(null);

		DynamicProgrammingReduction<MonedaVertex, MonedaEdge> ms2 = DPR.dynamicProgrammingReduction(
				graph, 
				MonedasHeuristica::heuristic, 
				PDType.Min);

		if (path2 != null) {
			System.out.println("3 = " + SolucionMonedas.of(path2));
			ms2.bestValue = path2.getWeight();
			ms2.solutionPath = path2;
		}
		Optional<GraphPath<MonedaVertex, MonedaEdge>> s2 = ms2.search();
		if (s2.isPresent()) System.out.println("4 = " + SolucionMonedas.of(s2.get()));
		else System.out.println("4 = No hay solucion");

	}
}
