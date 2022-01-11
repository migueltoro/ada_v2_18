package us.lsi.alg.typ;


import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.SimpleVirtualGraph;
import us.lsi.graphs.virtual.EGraph;

public class TestAStarTyP {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		TyPVertex.datos("ficheros/tareas.txt",5);
		TyPVertex e1 = TyPVertex.first();
		
		EGraph<TyPVertex,SimpleEdgeAction<TyPVertex,Integer>> graph = 
				SimpleVirtualGraph.last(e1,v->v.goal(),v->v.maxCarga());		
		
		
		AStar<TyPVertex, SimpleEdgeAction<TyPVertex, Integer>> ms = AStar.of(
				graph,
				Heuristica::heuristic,AStarType.Min);
		
//		ms.stream().forEach(v->System.out.println(v));
		
		GraphPath<TyPVertex,SimpleEdgeAction<TyPVertex,Integer>> path = ms.search().get();
//		List<MochilaEdge> edges = path.getEdgeList();
//		System.out.println(path);
		SolucionTyP s = TyPVertex.getSolucion(path);
		System.out.println(s);
		
		GraphColors.toDot(ms.graph(),"ficheros/TyPAStar.gv",
				v->String.format("(%d,%.1f)",v.index(),v.maxCarga()),
				e->e.action().toString(),
				v->GraphColors.colorIf(Color.red,v.goal()),
				e->GraphColors.colorIf(Color.red,path.getEdgeList().contains(e))
				);
	}

}
