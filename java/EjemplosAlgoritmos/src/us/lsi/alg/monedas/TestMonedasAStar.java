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
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestMonedasAStar {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		MonedaVertex.datosIniciales("ficheros/monedas3.txt", 36);
		MonedaVertex e1 = MonedaVertex.first();
		
//		SimpleVirtualGraph.constraintG = MonedaVertex.constraint();
//		SimpleVirtualGraph.endVertexG = MonedaVertex.last();
		EGraph<MonedaVertex, MonedaEdge> graph = 
				SimpleVirtualGraph.sum(e1,MonedaVertex.goal(),x->x.weight(),MonedaVertex.constraint());		
		
		AStar<MonedaVertex, MonedaEdge> ms = 
				AStar.of(graph,MonedasHeuristica::heuristic,AStarType.Max);
		
//		ms.stream().limit(100).forEach(v->System.out.printf("%s,actions = %s\n",v,v.actions()));
		
		Optional<GraphPath<MonedaVertex, MonedaEdge>> path = ms.search();
		
		SolucionMonedas s;
		if (path.isPresent()) {
			s = SolucionMonedas.of(path.get());
			System.out.println(s);
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
		
	    ms = AStar.of(graph,MonedasHeuristica::heuristic,AStarType.Min);
		
	    Optional<GraphPath<MonedaVertex, MonedaEdge>> path2 = ms.search();
		if (path.isPresent()) {
			s = SolucionMonedas.of(path2.get());
			System.out.println(s);
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
