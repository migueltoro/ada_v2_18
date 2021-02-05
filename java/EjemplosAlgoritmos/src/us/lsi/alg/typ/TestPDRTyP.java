package us.lsi.alg.typ;

import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearch;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.virtual.ActionSimpleEdge;
import us.lsi.graphs.virtual.EGraph;

public class TestPDRTyP {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		TyPVertex.datos("ficheros/tareas.txt",5);
		TyPVertex e1 = TyPVertex.first();
		TyPVertex e2 = TyPVertex.last();
		
		EGraph<TyPVertex,ActionSimpleEdge<TyPVertex,Integer>> graph = Graphs2.simpleVirtualGraphLast(e1,v->v.getMaxCarga());	
		
		GreedySearch<TyPVertex, ActionSimpleEdge<TyPVertex, Integer>> rr = 
				GraphAlg.greedy(graph,
				TyPVertex::greadyEdge,
				v->v.getIndex() == TyPVertex.n);
		
		Double bv = rr.weightToEnd().get();
		
		DynamicProgrammingReduction<TyPVertex,ActionSimpleEdge<TyPVertex,Integer>> ms = 
				DPR.dynamicProgrammingReduction(graph,
						e->e.getIndex()==TyPVertex.n,
						e2,
						(v1,p,v2)->0.,
						PDType.Min);
		ms.bestValue = bv;
		ms.search();
//		System.out.println(ms.search());
//		System.out.println(ms.solutionsTree);
		GraphPath<TyPVertex,ActionSimpleEdge<TyPVertex,Integer>> s1 = ms.search().get();
//		System.out.println(s1);
		SolucionTyP s = TyPVertex.getSolucion(s1);
		System.out.println(s);
		
	}

}
