package us.lsi.alg.monedas;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

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
				SimpleVirtualGraph.sum(e1,MonedaVertex.goal(),e->e.weight(),MonedaVertex.constraint());
		
		GreedyOnGraph<MonedaVertex, MonedaEdge> rr = GreedyOnGraph.of(graph,MonedaVertex::aristaVoraz);
		
		GraphPath<MonedaVertex, MonedaEdge> path = rr.path();
		System.out.println(rr.isSolution(path));
		System.out.println("G "+path.getWeight());
		System.out.println("H "+MonedasHeuristica.heuristic(e1,MonedaVertex.goal(),e2));
		
		Collections.sort(Moneda.monedas,Comparator.comparing(m->m.pesoUnitario()));
//		System.out.println(Moneda.monedas);
		
		e1 = MonedaVertex.first();
		MonedaVertex e3 = MonedaVertex.last();
		
//		System.out.println("3 = "+e1);
//		System.out.println("4 = "+e2);
		
		graph = SimpleVirtualGraph.sum(e1,MonedaVertex.goal(),e->e.weight(),MonedaVertex.constraint());
		
		rr = GreedyOnGraph.of(graph,MonedaVertex::aristaVoraz);
		
		path = rr.path();
		System.out.println(rr.isSolution(path));
		System.out.println("G "+path.getWeight());
		System.out.println("H "+MonedasHeuristica.heuristic(e1,MonedaVertex.goal(),e3));
	}

}
