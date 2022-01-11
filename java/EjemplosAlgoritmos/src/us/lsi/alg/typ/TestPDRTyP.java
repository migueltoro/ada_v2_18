package us.lsi.alg.typ;

import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.SimpleVirtualGraph;
import us.lsi.graphs.virtual.EGraph;

public class TestPDRTyP {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		TyPVertex.datos("ficheros/tareas.txt",5);
		TyPVertex e1 = TyPVertex.first();
		
		EGraph<TyPVertex,SimpleEdgeAction<TyPVertex,Integer>> graph = 
				SimpleVirtualGraph.last(e1,e->e.goal(),v->v.maxCarga());	
		
		GreedyOnGraph<TyPVertex, SimpleEdgeAction<TyPVertex, Integer>> rr = 
				GreedyOnGraph.of(graph,
				TyPVertex::greadyEdge);
		
		GraphPath<TyPVertex, SimpleEdgeAction<TyPVertex, Integer>> path = rr.path();
		Double bv = path.getWeight();
		
		DynamicProgrammingReduction<TyPVertex,SimpleEdgeAction<TyPVertex,Integer>> ms = 
				DynamicProgrammingReduction.of(graph,					
						Heuristica::heuristic,
						PDType.Min);
		
		
		ms.bestValue = bv;
		ms.optimalPath = path;
		ms.search();
//		System.out.println(ms.search());
//		System.out.println(ms.solutionsTree);
		GraphPath<TyPVertex,SimpleEdgeAction<TyPVertex,Integer>> s1 = ms.search().get();
//		System.out.println(s1);
		SolucionTyP s = TyPVertex.getSolucion(s1);
		System.out.println(s);
		
	}

}
