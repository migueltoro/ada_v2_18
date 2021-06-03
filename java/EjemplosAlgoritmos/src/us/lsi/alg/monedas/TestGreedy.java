package us.lsi.alg.monedas;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearchOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;

public class TestGreedy {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		MonedaVertex.datosIniciales("ficheros/monedas3.txt", 36);
		System.out.println(Moneda.monedas);
		System.out.println(Moneda.monedas.size());

		MonedaVertex e1 = MonedaVertex.first();
		MonedaVertex e2 = MonedaVertex.last();

//		System.out.println("1 = "+e1);
//		System.out.println("2 = "+e2);
		
		EGraph<MonedaVertex, MonedaEdge> graph = 
				Graphs2.simpleVirtualGraphSum(e1,MonedaVertex.goal(),e2,MonedaVertex.constraint());
		
		GreedySearchOnGraph<MonedaVertex, MonedaEdge> rr = GraphAlg.greedy(
				graph,
				MonedaVertex::accionVoraz);
		
		Optional<EGraphPath<MonedaVertex, MonedaEdge>> path = rr.search();
		System.out.println("G "+path.get().getWeight());
		System.out.println("H "+MonedasHeuristica.heuristic(e1,MonedaVertex.goal(),e2));
		
		Collections.sort(Moneda.monedas,Comparator.comparing(m->m.pesoUnitario()));
//		System.out.println(Moneda.monedas);
		
		e1 = MonedaVertex.first();
		MonedaVertex e3 = MonedaVertex.last();
		
//		System.out.println("3 = "+e1);
//		System.out.println("4 = "+e2);
		
		graph = Graphs2.simpleVirtualGraphSum(e1,MonedaVertex.goal(),e2,MonedaVertex.constraint());
		
		rr = GraphAlg.greedy(
				graph,
				MonedaVertex::accionVoraz);
		
		path = rr.search();
//		System.out.println(path);
		System.out.println("G "+path.get().getWeight());
		System.out.println("H "+MonedasHeuristica.heuristic(e1,MonedaVertex.goal(),e3));
	}

}
