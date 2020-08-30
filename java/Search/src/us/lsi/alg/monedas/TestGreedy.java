package us.lsi.alg.monedas;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearch;
import us.lsi.graphs.virtual.EGraph;

public class TestGreedy {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		MonedaVertex.datosIniciales("ficheros/monedas.txt", 307);
//		System.out.println(Moneda.monedas);
//		System.out.println(Moneda.monedas.size());

		MonedaVertex e1 = MonedaVertex.first();
		MonedaVertex e2 = MonedaVertex.last();

		System.out.println("1 = "+e1);
		System.out.println("2 = "+e2);
		
		EGraph<MonedaVertex, MonedaEdge> graph = Graphs2.simpleVirtualGraph(e1);
		
		GreedySearch<MonedaVertex, MonedaEdge> rr = GraphAlg.greedy(
				graph,
				MonedaVertex::accionVoraz,
				e->e.equals(e2));
		
		GraphPath<MonedaVertex, MonedaEdge> path = rr.search();
		System.out.println(path.getWeight());
		System.out.println(Heuristica.heuristica(e1,null,e2));
		
		Collections.sort(Moneda.monedas,Comparator.comparing(m->m.pesoUnitario));
//		System.out.println(Moneda.monedas);
		
		MonedaVertex e3 = MonedaVertex.first();
		MonedaVertex e4 = MonedaVertex.last();
		
		System.out.println("3 = "+e1);
		System.out.println("4 = "+e2);
		
		graph = Graphs2.simpleVirtualGraph(e3);
		
		rr = GraphAlg.greedy(
				graph,
				MonedaVertex::accionVoraz,
				e->e.equals(e4));
		
		path = rr.search();
//		System.out.println(path);
		System.out.println(path.getWeight());
		System.out.println(Heuristica.heuristica(e3,null,e4));

	}

}
