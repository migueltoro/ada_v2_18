package us.lsi.search.typ;

import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.GSearch;
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
				GSearch.greedy(graph,TyPVertex::greadyEdge,e->e.getIndex()==TyPVertex.n);
		GraphPath<TyPVertex, ActionSimpleEdge<TyPVertex,Integer>> path = rr.pathToEnd();
		SolucionTyP sm = TyPVertex.getSolucion(path);
		Double bv = path.getWeight();
		
		var ms = 
				BT.backTracking(
						graph,
						e->e.getIndex()==TyPVertex.n,
						e2,
						(v1,p,v2)->0.,
						TyPVertex::getSolucion,
						TyPVertex::copy,
						BTType.Min);		
		
		ms.bestValue = bv;
		ms.solutions.add(sm);
		
		ms.search();
		
		SolucionTyP s = ms.getSolution();
		System.out.println(s);
		System.out.println(ms.getSolutions().size());

	}

}
