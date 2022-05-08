package us.lsi.alg.monedas;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.SimpleDirectedGraph;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestMonedasAStar {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		MonedaVertex.datosIniciales("ficheros/monedas2.txt", 401);
		MonedaVertex e1 = MonedaVertex.first();
		
		EGraph<MonedaVertex, MonedaEdge> graph = 
				SimpleVirtualGraph.sum(e1,MonedaVertex.goal(),x->x.weight(),MonedaVertex.constraint());		
		
		GreedyOnGraph<MonedaVertex, MonedaEdge> rr = GreedyOnGraph.of(graph, MonedaVertex::aristaVoraz);

		GraphPath<MonedaVertex, MonedaEdge> path1 = rr.path();
		
		AStar<MonedaVertex, MonedaEdge> ms = 
				AStar.of(graph,MonedasHeuristica::heuristic,AStarType.Max);
		
//		ms.stream().limit(100).forEach(v->System.out.printf("%s,actions = %s\n",v,v.actions()));
		
		if (rr.isSolution(path1)) {
			System.out.println("Hay solucion voraz 1 = " +path1.getWeight());
			ms.bestValue = path1.getWeight();
			ms.optimalPath = path1;
		} else {
			ms.bestValue = 72360.0;
			ms.optimalPath = null;
		}
		
		Optional<GraphPath<MonedaVertex, MonedaEdge>> path = ms.search();
		
		SolucionMonedas s;
		if (path.isPresent()) {
			s = SolucionMonedas.of(path.get());
			System.out.println(s);
			System.out.println(ms.tree.keySet().size());
			SimpleDirectedGraph<MonedaVertex, MonedaEdge> g = ms.graph();
			GraphColors.toDot(g,"ficheros/MonedasAstarGraph1.gv",
					v->String.format("(%d,%d)",v.index(),v.valorRestante()),
					e->e.action().toString(),
					v->GraphColors.colorIf(Color.red,MonedaVertex.goal().test(v)),
					e->GraphColors.color(Color.black)
					);
		} else {
			System.out.println("No hay solucion");
		}
		
		
	
		System.out.println("_________________________________");
		
		Collections.sort(Moneda.monedas, Comparator.comparing(m -> m.pesoUnitario()));
		
		MonedaVertex e3 = MonedaVertex.first();
//		MonedaVertex e4 = MonedaVertex.last();

		graph = SimpleVirtualGraph.sum(e3,MonedaVertex.goal(),x->x.weight());	
		
		rr = GreedyOnGraph.of(graph, MonedaVertex::aristaVoraz);

		GraphPath<MonedaVertex, MonedaEdge> path2 = rr.path();
		
	    ms = AStar.of(graph,MonedasHeuristica::heuristic,AStarType.Min);
	    
	    if (rr.isSolution(path2)) {
			System.out.println("Hay solucion voraz 2 = "+path2.getWeight());
			ms.bestValue = path2.getWeight();
			ms.optimalPath = path2;
		} else {
			ms.bestValue = 376.0;
			ms.optimalPath = null;
		}
		
	    path = ms.search();
		
	    if (path.isPresent()) {
			s = SolucionMonedas.of(path.get());
			System.out.println(s);
			System.out.println(ms.tree.keySet().size());
			SimpleDirectedGraph<MonedaVertex, MonedaEdge> g = ms.graph();
			GraphColors.toDot(g,"ficheros/MonedasAstarGraph2.gv",
					v->String.format("(%d,%d)",v.index(),v.valorRestante()),
					e->e.action().toString(),
					v->GraphColors.colorIf(Color.red,MonedaVertex.goal().test(v)),
					e->GraphColors.color(Color.black)
					);
		} else {			
			System.out.println("No hay solucion");
		}
		
	}

}
