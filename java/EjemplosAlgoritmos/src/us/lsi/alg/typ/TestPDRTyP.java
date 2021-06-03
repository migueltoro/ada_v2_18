package us.lsi.alg.typ;

import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearchOnGraph;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.virtual.ActionSimpleEdge;
import us.lsi.graphs.virtual.EGraph;

public class TestPDRTyP {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		TyPVertex.datos("ficheros/tareas.txt",5);
		TyPVertex e1 = TyPVertex.first();
		TyPVertex e2 = TyPVertex.last();
		
		EGraph<TyPVertex,ActionSimpleEdge<TyPVertex,Integer>> graph = 
				Graphs2.simpleVirtualGraphLast(e1,e->e.goal(),
						e2,
						v->true,v->v.maxCarga());	
		
		GreedySearchOnGraph<TyPVertex, ActionSimpleEdge<TyPVertex, Integer>> rr = 
				GraphAlg.greedy(graph,
				TyPVertex::greadyEdge);
		
		GraphPath<TyPVertex, ActionSimpleEdge<TyPVertex, Integer>> path = rr.search().orElse(null);
		Double bv = path.getWeight();
		
		DynamicProgrammingReduction<TyPVertex,ActionSimpleEdge<TyPVertex,Integer>> ms = 
				DPR.dynamicProgrammingReduction(graph,
						
						Heuristica::heuristic,
						PDType.Min);
		
		
//		ms.bestValue = bv;
//		ms.solutionPath = path;
		ms.search();
//		System.out.println(ms.search());
//		System.out.println(ms.solutionsTree);
		GraphPath<TyPVertex,ActionSimpleEdge<TyPVertex,Integer>> s1 = ms.search().get();
//		System.out.println(s1);
		SolucionTyP s = TyPVertex.getSolucion(s1);
		System.out.println(s);
		
	}

}
