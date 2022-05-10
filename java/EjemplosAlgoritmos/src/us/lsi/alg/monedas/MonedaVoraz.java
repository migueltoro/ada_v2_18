package us.lsi.alg.monedas;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class MonedaVoraz {
	
	
	public static Double voraz() {	
		Integer valorInicial = MonedaVertex.valorInicial;
		Double r = 0.;
		for (int i = 0; ; i++) {
			MonedaVertex.valorInicial = valorInicial + i;		
			MonedaVertex e1 = MonedaVertex.first();		
			EGraph<MonedaVertex, MonedaEdge> graph = SimpleVirtualGraph.sum(e1, MonedaVertex.goal(), e -> e.weight(),
					MonedaVertex.constraint());
			GreedyOnGraph<MonedaVertex, MonedaEdge> rr = GreedyOnGraph.of(graph, MonedaVertex::aristaVoraz);
			GraphPath<MonedaVertex, MonedaEdge> path = rr.path();
			if(rr.isSolution(path)) {
				r = path.getWeight();
				break; 
			}
		}
		MonedaVertex.valorInicial = valorInicial;
		return r;
	}

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		MonedaVertex.datosIniciales("ficheros/monedas2.txt", 401);
		System.out.println(Moneda.monedas);
		System.out.println(Moneda.monedas.size());
		System.out.println(voraz());
	}

}
