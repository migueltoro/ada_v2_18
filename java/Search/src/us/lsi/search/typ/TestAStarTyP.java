package us.lsi.search.typ;


import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.search.AStarSearch;
import us.lsi.graphs.search.GSearch;
import us.lsi.graphs.virtual.ActionSimpleEdge;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestAStarTyP {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		TyPVertex.datos("ficheros/tareas.txt",5);
		TyPVertex e1 = TyPVertex.first();
//		TyPVertex e2 = TyPVertex.last();
		
		EGraph<TyPVertex,ActionSimpleEdge<TyPVertex,Integer>> graph = SimpleVirtualGraph.last(
				e1,v->v.getMaxCarga());		
		
		
		AStarSearch<TyPVertex, ActionSimpleEdge<TyPVertex, Integer>> ms = GSearch.aStarGoal(
				graph,
				e->e.getIndex()==TyPVertex.n,
				(v1,p,v2)->0.);
		
//		ms.stream().forEach(v->System.out.println(v));
		
		GraphPath<TyPVertex,ActionSimpleEdge<TyPVertex,Integer>> path = ms.pathTo(e->e.getIndex()==TyPVertex.n);
//		List<MochilaEdge> edges = path.getEdgeList();
//		System.out.println(path);
		SolucionTyP s = TyPVertex.getSolucion(path);
		System.out.println(s);
	}

}
