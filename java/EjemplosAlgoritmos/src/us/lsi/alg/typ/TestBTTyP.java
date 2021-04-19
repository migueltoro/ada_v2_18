package us.lsi.alg.typ;

import java.util.Comparator;
import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearch;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.ActionSimpleEdge;
import us.lsi.graphs.virtual.EGraph;

public class TestBTTyP {

	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		TyPVertex.datos("ficheros/tareas.txt",5);
		TyPVertex e1 = TyPVertex.first();
		TyPVertex e2 = TyPVertex.last();
		EGraph<TyPVertex,ActionSimpleEdge<TyPVertex,Integer>> graph = Graphs2.simpleVirtualGraphLast(e1,v->v.getMaxCarga());		
			
		GreedySearch<TyPVertex, ActionSimpleEdge<TyPVertex,Integer>> rr = 
				GraphAlg.greedy(graph,TyPVertex::greadyEdge,e->e.getIndex()==TyPVertex.n);
		GraphPath<TyPVertex, ActionSimpleEdge<TyPVertex,Integer>> path = rr.search().orElse(null);
		SolucionTyP sm = TyPVertex.getSolucion(path);
		Double bv = path.getWeight();
		
		BackTracking<TyPVertex, ActionSimpleEdge<TyPVertex, Integer>, SolucionTyP> ms = BT.backTracking(
						graph,
						e->e.getIndex()==TyPVertex.n,
						e2,
						Heuristica::heuristic,
						TyPVertex::getSolucion,
						TyPVertex::copy,
						BTType.Min);		
		
//		ms.bestValue = bv+4;
//		ms.solutions.add(sm);
		
		ms.search();

		System.out.println(ms.getSolutions().stream().min(Comparator.comparing(x->x.getMaxCarga())).get());
//		System.out.println(ms.getSolutions());

	}

}
