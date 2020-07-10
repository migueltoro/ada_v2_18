package us.lsi.search.typ;

import java.util.Locale;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearch;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.virtual.ActionSimpleEdge;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;

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
		
		Double bv = rr.weightToEnd();
		
		DynamicProgrammingReduction<TyPVertex,ActionSimpleEdge<TyPVertex,Integer>,SolucionTyP> ms = 
				DPR.dynamicProgrammingReduction(graph,
						e->e.getIndex()==TyPVertex.n,
						e2,
						(v1,p,v2)->0.,
						TyPVertex::getSolucion,
						PDType.Min);
		ms.bestValue = bv;
		ms.search();
//		System.out.println(ms.search());
//		System.out.println(ms.solutionsTree);
		EGraphPath<TyPVertex,ActionSimpleEdge<TyPVertex,Integer>> s1 = ms.pathFrom(e1);
//		System.out.println(s1);
		SolucionTyP s = ms.getSolution(s1);
		System.out.println(s);
		
	}

}
