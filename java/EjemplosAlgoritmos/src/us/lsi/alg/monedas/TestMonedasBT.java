package us.lsi.alg.monedas;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearchOnGraph;
import us.lsi.graphs.virtual.EGraph;

public class TestMonedasBT {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		MonedaVertex.datosIniciales("ficheros/monedas3.txt", 36);

		MonedaVertex e1 = MonedaVertex.first();
		MonedaVertex e2 = MonedaVertex.last();

		EGraph<MonedaVertex, MonedaEdge> graph = Graphs2.simpleVirtualGraphSum(e1);
//
		GreedySearchOnGraph<MonedaVertex, MonedaEdge> rr = GraphAlg.greedy(graph, MonedaVertex::accionVoraz,
				v->v.goal(), v->true);
//
		GraphPath<MonedaVertex, MonedaEdge> path1 = rr.search().orElse(null);
		
		Double bv = MonedasHeuristica.voraz(e1,e->e.goal(),e2).doubleValue();
		
//		System.out.println("1 = "+bv);
		SolucionMonedas ss = SolucionMonedas.of(path1);
//		System.out.println("1 = "+ss);

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
//			System.out.println("1.1 = "+ms1.bestValue);
			ss = SolucionMonedas.of(path1);
//			System.out.println("1.1 = "+ss);
			if (ss.valor().equals(MonedaVertex.valorInicial)) {
				ms1.solutions.add(ss);
			}
		}
		ms1.search();
		
		Optional<SolucionMonedas> s = ms1.getSolution();
//		System.out.println("2 = Soluciones\n"+ms1.toStringSolutions());
		if(s.isPresent()) System.out.println("2 = " + s.get().toString());
		else System.out.println("2 = No hay solucion");

		Collections.sort(Moneda.monedas, Comparator.comparing(m -> m.pesoUnitario()));

		MonedaVertex e3 = MonedaVertex.first();
		MonedaVertex e4 = MonedaVertex.last();

		graph = Graphs2.simpleVirtualGraphSum(e3);

		rr = GraphAlg.greedy(graph, MonedaVertex::accionVoraz,v->v.goal(), v->true);
		
		GraphPath<MonedaVertex, MonedaEdge> path2 = rr.search().orElse(null);
		bv = MonedasHeuristica.voraz(e3,e->e.goal(),e4).doubleValue();
		
//		System.out.println("3 = "+bv);
		ss = SolucionMonedas.of(path2);
//		System.out.println("3 = "+ss);

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
//			System.out.println("3.1 = "+ms2.bestValue);
			ss = SolucionMonedas.of(path2);
//			System.out.println("3.1 = "+ss);
			if (ss.valor().equals(MonedaVertex.valorInicial)) {
				ms2.solutions.add(ss);
			}
		}
//		ms2.withGraph = true;
		ms2.search();
	
		Optional<SolucionMonedas> s2 = ms2.getSolution();
		
//		System.out.println("4 = Soluciones\n"+ms2.toStringSolutions());
		if(s2.isPresent()) System.out.println("4 = " + s2.get().toString());
		else System.out.println("4 = No hay solucion");
		
//		Graphs2.toDot(ms2.outGraph,"ficheros/MonedasBTGraph.gv",
//				v->String.format("(%d,%d)",v.index(),v.valorRestante()),
//				e->e.action().toString(),
//				v->GraphColors.getColorIf(Color.red,v.goal()),
//				e->GraphColors.getColor(Color.black)
//				);
	}
	}

