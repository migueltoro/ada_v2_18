package us.lsi.alg.typ;

import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.SimpleVirtualGraph;
import us.lsi.graphs.virtual.EGraph;

public class TestGreadyTyP {
	
	

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		TyPVertex.datos("ficheros/tareas.txt",5);
		TyPVertex e1 = TyPVertex.first();
//		TyPVertex e2 = TyPVertex.last();
		EGraph<TyPVertex,SimpleEdgeAction<TyPVertex,Integer>> graph = 
				SimpleVirtualGraph.last(e1,e->e.goal(),v->v.maxCarga());
		
		GreedyOnGraph<TyPVertex, SimpleEdgeAction<TyPVertex, Integer>> ms = 
				GreedyOnGraph.of(graph,TyPVertex::greadyEdge);	
		
		GraphPath<TyPVertex, SimpleEdgeAction<TyPVertex, Integer>> path = ms.path();
		
		System.out.println(TyPVertex.getSolucion(path));
		
		System.out.println(path.getWeight());
	}

}
