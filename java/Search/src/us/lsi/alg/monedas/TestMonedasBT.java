package us.lsi.alg.monedas;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearch;
import us.lsi.graphs.virtual.EGraph;

public class TestMonedasBT {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		MonedaVertex.datosIniciales("ficheros/monedas.txt", 307);

		MonedaVertex e1 = MonedaVertex.first();
		MonedaVertex e2 = MonedaVertex.last();

		EGraph<MonedaVertex, MonedaEdge> graph = Graphs2.simpleVirtualGraph(e1);

		GreedySearch<MonedaVertex, MonedaEdge> rr = GraphAlg.greedy(graph, MonedaVertex::accionVoraz,
				e -> e.equals(e2));
//		GreedySearch<MonedaVertex, MonedaEdge> rrh = GraphAlg.greedy(graph, MonedaVertex::accionHeuristica,
//				e -> e.equals(e2));

		GraphPath<MonedaVertex, MonedaEdge> path1 = rr.search();
//		GraphPath<MonedaVertex, MonedaEdge> path1h = rrh.search();
		
//		System.out.println("1 = " + SolucionMonedas.of(path1));
//		System.out.println("1.1 = " + SolucionMonedas.of(path1h));

		BackTracking<MonedaVertex,MonedaEdge,SolucionMonedas> ms1 = BT.backTrackingGoal(
				graph, 
				v->v.index==MonedaVertex.n,
				MonedasHeuristica::heuristica, 
				SolucionMonedas::of,
				MonedaVertex::copy,
				BTType.Max);

		ms1.bestValue = path1.getWeight();
		ms1.solutions.add(SolucionMonedas.of(path1));
		
		ms1.search();

		System.out.println("2 = " + ms1.getSolution());

		Collections.sort(Moneda.monedas, Comparator.comparing(m -> m.pesoUnitario));

		MonedaVertex e3 = MonedaVertex.first();
		MonedaVertex e4 = MonedaVertex.last();

		graph = Graphs2.simpleVirtualGraph(e3);

		rr = GraphAlg.greedy(graph, MonedaVertex::accionVoraz, e -> e.equals(e4));
//		rrh = GraphAlg.greedy(graph,MonedaVertex::accionHeuristica,e -> e.equals(e4));
		
		GraphPath<MonedaVertex, MonedaEdge> path2 = rr.search();
//		GraphPath<MonedaVertex, MonedaEdge> path2h = rrh.search();
		

//		System.out.println("3 = " + SolucionMonedas.of(path2));
//		System.out.println("3.1 = " + SolucionMonedas.of(path2h));

		BackTracking<MonedaVertex, MonedaEdge,SolucionMonedas> ms2 = BT.backTrackingGoal(
				graph, 
				v->v.index==MonedaVertex.n,
				MonedasHeuristica::heuristica, 
				SolucionMonedas::of,
				MonedaVertex::copy,
				BTType.Min);

		ms2.bestValue = path2.getWeight();
		ms2.solutions.add(SolucionMonedas.of(path2));
		
		ms2.search();
		System.out.println("4 = " + ms2.getSolution());

	}
	}

