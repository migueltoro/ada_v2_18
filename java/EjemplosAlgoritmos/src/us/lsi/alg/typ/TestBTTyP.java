package us.lsi.alg.typ;

import java.util.Comparator;
import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearchOnGraph;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.ActionSimpleEdge;
import us.lsi.graphs.virtual.EGraph;

public class TestBTTyP {

	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		TyPVertex.datos("ficheros/tareas.txt",5);
		TyPVertex e1 = TyPVertex.first();
		TyPVertex e2 = TyPVertex.last();
		EGraph<TyPVertex,ActionSimpleEdge<TyPVertex,Integer>> graph = 
				Graphs2.simpleVirtualGraphLast(e1,v->v.maxCarga());		
			
		GreedySearchOnGraph<TyPVertex, ActionSimpleEdge<TyPVertex,Integer>> rr = 
				GraphAlg.greedy(graph,TyPVertex::greadyEdge,
						e->e.goal(),
						v->true);
		
		GraphPath<TyPVertex, ActionSimpleEdge<TyPVertex,Integer>> path = rr.search().orElse(null);
		SolucionTyP sm = TyPVertex.getSolucion(path);
		Double bv = path.getWeight();
		System.out.println(bv);
		
		BackTracking<TyPVertex, ActionSimpleEdge<TyPVertex, Integer>, SolucionTyP> ms = BT.backTrackingGoal(
						graph,
						e->e.goal(),
						Heuristica::heuristic,
						TyPVertex::getSolucion,
						TyPVertex::copy,
						BTType.Min);		
		
		ms.bestValue = bv;
		ms.solutions.add(sm);
		
		ms.search();

		System.out.println(ms.getSolutions().stream().min(Comparator.comparing(x->x.getMaxCarga())).get());
//		System.out.println(ms.getSolutions());

	}

}
