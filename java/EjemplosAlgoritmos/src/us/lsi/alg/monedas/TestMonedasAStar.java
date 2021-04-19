package us.lsi.alg.monedas;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import org.jgrapht.GraphPath;


import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.EGraph;

public class TestMonedasAStar {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		MonedaVertex.datosIniciales("ficheros/monedas.txt", 307);
		MonedaVertex e1 = MonedaVertex.first();
		MonedaVertex e2 = MonedaVertex.last();
		
		EGraph<MonedaVertex, MonedaEdge> graph = Graphs2.simpleVirtualGraph(e1,x->-x.getEdgeWeight());		
		
		AStar<MonedaVertex, MonedaEdge> ms = GraphAlg.aStarGoal(graph,v->v.index==MonedaVertex.n,MonedasHeuristica::heuristica);
		
		GraphPath<MonedaVertex,MonedaEdge> path = ms.search().orElse(null);
//		List<MonedaEdge> edges = path.getEdgeList();
//		System.out.println(edges);
		SolucionMonedas s = SolucionMonedas.of(path);
		System.out.println(s);
		
		Collections.sort(Moneda.monedas, Comparator.comparing(m -> m.pesoUnitario));
		
		e1 = MonedaVertex.first();
		e2 = MonedaVertex.last();

		graph = Graphs2.simpleVirtualGraph(e1,x->x.getEdgeWeight());		
		
	    ms = GraphAlg.aStarEnd(graph,e2,MonedasHeuristica::heuristica);
		
		path = ms.search().orElse(path);
//		edges = path.getEdgeList();
//		System.out.println(edges);
		s = SolucionMonedas.of(path);
		System.out.println(s);
	}

}
