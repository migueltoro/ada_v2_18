package us.lsi.alg.monedas;



import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import org.jgrapht.GraphPath;


import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearch;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;


public class TestMonedasPDR {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		MonedaVertex.datosIniciales("ficheros/monedas.txt", 307);

		MonedaVertex e1 = MonedaVertex.first();
		MonedaVertex e2 = MonedaVertex.last();

		EGraph<MonedaVertex, MonedaEdge> graph = Graphs2.simpleVirtualGraph(e1);

		GreedySearch<MonedaVertex, MonedaEdge> rr = GraphAlg.greedy(graph, MonedaVertex::accionVoraz,
				e -> e.equals(e2));
		GreedySearch<MonedaVertex, MonedaEdge> rrh = GraphAlg.greedy(graph, MonedaVertex::accionHeuristica,
				e -> e.equals(e2));

		EGraphPath<MonedaVertex, MonedaEdge> path1 = rr.search().orElse(null);
		EGraphPath<MonedaVertex, MonedaEdge> path1h = rrh.search().orElse(path1);
		
		System.out.println("1 = " + SolucionMonedas.of(path1));
		System.out.println("1.1 = " + SolucionMonedas.of(path1h));

		DynamicProgrammingReduction<MonedaVertex, MonedaEdge> ms1 = DPR.dynamicProgrammingReductionGoal(
				graph, 
				v->v.index()==MonedaVertex.n,
				MonedasHeuristica::heuristica, 
				PDType.Max);

		ms1.bestValue = path1.getWeight();
		ms1.solutionPath = path1;

		GraphPath<MonedaVertex, MonedaEdge> s1 = ms1.search().get();

		System.out.println("2 = " + SolucionMonedas.of(s1));

		Collections.sort(Moneda.monedas, Comparator.comparing(m -> m.pesoUnitario));

		MonedaVertex e3 = MonedaVertex.first();
		MonedaVertex e4 = MonedaVertex.last();

		graph = Graphs2.simpleVirtualGraph(e3);

		rr = GraphAlg.greedy(graph, MonedaVertex::accionVoraz, e -> e.equals(e4));
		rrh = GraphAlg.greedy(graph,MonedaVertex::accionHeuristica,e -> e.equals(e4));
		
		EGraphPath<MonedaVertex, MonedaEdge> path2 = rr.search().orElse(path1h);
		EGraphPath<MonedaVertex, MonedaEdge> path2h = rrh.search().orElse(path2);
		

		System.out.println("3 = " + SolucionMonedas.of(path2));
		System.out.println("3.1 = " + SolucionMonedas.of(path2h));

		DynamicProgrammingReduction<MonedaVertex, MonedaEdge> ms2 = DPR.dynamicProgrammingReductionGoal(
				graph, 
				v->v.index()==MonedaVertex.n,
				(v1,p,v2)->0., 
				PDType.Min);

		ms2.bestValue = path2.getWeight();
		ms2.solutionPath = path2;
		
		GraphPath<MonedaVertex, MonedaEdge> s2 = ms2.search().orElse(null);
		System.out.println("4 = " + SolucionMonedas.of(s2));

	}
}
