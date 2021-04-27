package us.lsi.alg.monedas;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
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
//
		GreedySearch<MonedaVertex, MonedaEdge> rr = GraphAlg.greedy(graph, MonedaVertex::accionVoraz,
				v->v.goal(), v->v.constraint());
//
		GraphPath<MonedaVertex, MonedaEdge> path1 = rr.search().orElse(null);
		
		Double bv = MonedasHeuristica.voraz_left(0,MonedaVertex.valorInicial,MonedaVertex.n).peso().doubleValue();

		BackTracking<MonedaVertex,MonedaEdge,SolucionMonedas> ms1 = BT.backTracking(
				graph, 
				v->v.goal(),
				e2,
				v->v.constraint(),
				MonedasHeuristica::heuristic, 
				SolucionMonedas::of,
				MonedaVertex::copy,
				BTType.Max);

		if (path1 != null) {
			ms1.bestValue = path1.getWeight();
			ms1.solutions.add(SolucionMonedas.of(path1));
		}
		ms1.search();
		Optional<SolucionMonedas> s = ms1.getSolution();
		
		if(s.isPresent()) System.out.println("2 = " + s.get().toString());
		else System.out.println("2 = No hay solucion");

		Collections.sort(Moneda.monedas, Comparator.comparing(m -> m.pesoUnitario()));

		MonedaVertex e3 = MonedaVertex.first();
		MonedaVertex e4 = MonedaVertex.last();

		graph = Graphs2.simpleVirtualGraph(e3);

		rr = GraphAlg.greedy(graph, MonedaVertex::accionVoraz,v->v.goal(), v->v.constraint());
		
		GraphPath<MonedaVertex, MonedaEdge> path2 = rr.search().orElse(null);

		BackTracking<MonedaVertex, MonedaEdge,SolucionMonedas> ms2 = BT.backTracking(
				graph, 
				v->v.goal(),
				e4,
				v->v.constraint(),
				MonedasHeuristica::heuristic, 
				SolucionMonedas::of,
				MonedaVertex::copy,
				BTType.Min);

		if (path2 != null) {
			ms2.bestValue = path2.getWeight();
			ms2.solutions.add(SolucionMonedas.of(path2));
		}
		ms2.withGraph = true;
		ms2.search();
	
		Optional<SolucionMonedas> s2 = ms2.getSolution();
		
		if(s2.isPresent()) System.out.println("4 = " + s2.get().toString());
		else System.out.println("2 = No hay solucion");
		
		Graphs2.toDot(ms2.outGraph,"ficheros/MonedasBTGraph.gv",
				v->String.format("(%d,%d)",v.index(),v.valorRestante()),
				e->e.getAction().toString(),
				v->GraphColors.getColorIf(Color.red,v.goal()),
				e->GraphColors.getColor(Color.black)
				);
	}
	}

