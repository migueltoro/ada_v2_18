package us.lsi.alg.monedas;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.EGraph;

public class TestMonedasAStar {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		MonedaVertex.datosIniciales("ficheros/monedas2.txt", 307);
		MonedaVertex e1 = MonedaVertex.first();
		MonedaVertex e2 = MonedaVertex.last();
		Predicate<MonedaVertex> constraint = v->v.valorRestante() == 0;
		
		EGraph<MonedaVertex, MonedaEdge> graph = Graphs2.simpleVirtualGraph(e1,x->-x.getWeight());		
		
		AStar<MonedaVertex, MonedaEdge> ms = 
				GraphAlg.aStar(graph,v->v.goal(),e2,constraint,MonedasHeuristica::heuristic_negate);
		
//		ms.stream().limit(100).forEach(v->System.out.printf("%s,actions = %s\n",v,v.actions()));
		
		ms.withGraph = true;
		
		final GraphPath<MonedaVertex,MonedaEdge> path = ms.search().orElse(null);
		SolucionMonedas s;
		if (path != null) {
			s = SolucionMonedas.of(path);
			System.out.println(s);
		} else {
			System.out.println("No hay solucion");
		}
		Graphs2.toDot(ms.outGraph,"ficheros/MonedasAstarGraph.gv",
				v->String.format("(%d,%d)",v.index(),v.valorRestante()),
				e->e.getAction().toString(),
				v->GraphColors.getColorIf(Color.red,v.goal()),
				e->GraphColors.getColor(Color.black)
				);
	
		System.out.println("_________________________________");
		
		Collections.sort(Moneda.monedas, Comparator.comparing(m -> m.pesoUnitario()));
		
		MonedaVertex e3 = MonedaVertex.first();
		MonedaVertex e4 = MonedaVertex.last();

		graph = Graphs2.simpleVirtualGraph(e3,x->x.getWeight());		
		
	    ms = GraphAlg.aStar(graph,e->e.goal(),e4,constraint,MonedasHeuristica::heuristic);
//	    ms.withGraph = true;
		
	    GraphPath<MonedaVertex,MonedaEdge> path2 = ms.search().orElse(null);
//	    ms.stream()..limit(100).forEach(v->System.out.printf("%s,actions = %s\n",v,v.actions()));
		if (path != null) {
			s = SolucionMonedas.of(path2);
			System.out.println(s);
		} else {			
			System.out.println("No hay solucion");
		}
		
	}

}
