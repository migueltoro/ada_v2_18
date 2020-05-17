package us.lsi.search.typ;

import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.search.GSearch;
import us.lsi.graphs.search.GreedySearch;
import us.lsi.graphs.virtual.ActionSimpleEdge;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestGreadyTyP {
	
	

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		TyPVertex.datos("ficheros/tareas.txt",5);
		TyPVertex e1 = TyPVertex.first();
//		TyPVertex e2 = TyPVertex.last();
		EGraph<TyPVertex,ActionSimpleEdge<TyPVertex,Integer>> graph = 
				SimpleVirtualGraph.last(e1,
						v->v.getMaxCarga());
		
		GreedySearch<TyPVertex, ActionSimpleEdge<TyPVertex, Integer>> ms = 
				GSearch.greedy(graph,
						TyPVertex::greadyEdge,
						v->v.getIndex() == TyPVertex.n);	
		
		var ms1 = ms.copy();
		var ms2 = ms.copy();
		
		GraphPath<TyPVertex,ActionSimpleEdge<TyPVertex,Integer>> path = ms.pathToEnd();
		
		System.out.println(TyPVertex.getSolucion(path));
		
		System.out.println(ms1.findEnd());
		System.out.println(ms2.weightToEnd());
	}

}
