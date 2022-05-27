package us.lsi.alg.monedas;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;


import org.jgrapht.GraphPath;
import org.jgrapht.graph.SimpleDirectedGraph;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestMonedasBT {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		MonedaVertex.datosIniciales("ficheros/monedas2.txt", 400);

		MonedaVertex e1 = MonedaVertex.first();

		EGraph<MonedaVertex, MonedaEdge> graph = SimpleVirtualGraph.sum(e1, 
				MonedaVertex.goal(), e -> e.weight(),MonedaVertex.constraint());

		GreedyOnGraph<MonedaVertex, MonedaEdge> rr = GreedyOnGraph.of(graph, MonedaVertex::aristaVoraz);

		graph = SimpleVirtualGraph.sum(e1, 
				MonedaVertex.goal(), e -> e.weight(),MonedaVertex.constraint());

		GraphPath<MonedaVertex, MonedaEdge> path1 = rr.path();

		BackTracking<MonedaVertex, MonedaEdge, SolucionMonedas> ms1 = BackTracking.of(graph,
				MonedasHeuristica::heuristic, SolucionMonedas::of, BTType.Max);

		if (rr.isSolution(path1)) {
			System.out.println("Hay solucion voraz 1"+path1.getWeight());
			ms1.bestValue = path1.getWeight();
			ms1.optimalPath = path1;
		} else {
			ms1.bestValue = MonedaVoraz.voraz();
			ms1.optimalPath = null;
		}
		
		ms1.withGraph = true;
		ms1.search();

		Optional<GraphPath<MonedaVertex, MonedaEdge>> ps = ms1.optimalPath();

		if (ps.isPresent()) {
			SolucionMonedas s = SolucionMonedas.of(ps.get());
			System.out.println("2 = " + s.toString());
			SimpleDirectedGraph<MonedaVertex, MonedaEdge> g = ms1.graph();
			GraphColors.toDot(g, "ficheros/MonedasBTGraph1.gv",
					v -> String.format("(%d,%d)", v.index(), v.valorRestante()), e -> e.action().toString(),
					v -> GraphColors.colorIf(Color.red, MonedaVertex.goal().test(v)),
					e -> GraphColors.color(Color.black));
		} else
			System.out.println("2 = No hay solucion");

		Collections.sort(Moneda.monedas, Comparator.comparing(m -> m.pesoUnitario()));

		MonedaVertex e3 = MonedaVertex.first();

		graph = SimpleVirtualGraph.sum(e3, MonedaVertex.goal(), e -> e.weight(), MonedaVertex.constraint());

		rr = GreedyOnGraph.of(graph, MonedaVertex::aristaVoraz);

		GraphPath<MonedaVertex, MonedaEdge> path2 = rr.path();

		BackTracking<MonedaVertex, MonedaEdge, SolucionMonedas> ms2 = BackTracking.of(graph,
				MonedasHeuristica::heuristic, SolucionMonedas::of, BTType.Min);

		if (rr.isSolution(path2)) {
			System.out.println("Hay solucion voraz 2 "+path2.getWeight());
			ms2.bestValue = path2.getWeight();
			ms2.optimalPath = path2;
		} else {
			ms2.bestValue =  MonedaVoraz.voraz();
			ms2.optimalPath = null;
		}
		ms2.search();

		Optional<SolucionMonedas> s2 = ms2.getSolution();

		if (s2.isPresent()) {
			System.out.println("4 = " + s2.get().toString());
		} else
			System.out.println("4 = No hay solucion");

	}
}
