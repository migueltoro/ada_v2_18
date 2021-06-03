package us.lsi.alg.typ;


import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.ActionSimpleEdge;
import us.lsi.graphs.virtual.EGraph;

public class TestAStarTyP {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		TyPVertex.datos("ficheros/tareas.txt",5);
		TyPVertex e1 = TyPVertex.first();
		TyPVertex e2 = TyPVertex.last();
		
		EGraph<TyPVertex,ActionSimpleEdge<TyPVertex,Integer>> graph = 
				Graphs2.simpleVirtualGraphLast(e1,v->v.goal(),
						e2,v->true,v->v.maxCarga());		
		
		
		AStar<TyPVertex, ActionSimpleEdge<TyPVertex, Integer>> ms = GraphAlg.aStar(
				graph,
				Heuristica::heuristic);
		
//		ms.stream().forEach(v->System.out.println(v));
		
		GraphPath<TyPVertex,ActionSimpleEdge<TyPVertex,Integer>> path = ms.search().orElse(null);
//		List<MochilaEdge> edges = path.getEdgeList();
//		System.out.println(path);
		SolucionTyP s = TyPVertex.getSolucion(path);
		System.out.println(s);
	}

}
