package us.lsi.alg.typ;


import java.util.Locale;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.ActionSimpleEdge;
import us.lsi.graphs.virtual.EGraph;

public class TestAStarTyP {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		TyPVertex.datos("ficheros/tareas.txt",5);
		TyPVertex e1 = TyPVertex.first();
//		TyPVertex e2 = TyPVertex.last();
		Predicate<TyPVertex> goal = v->v.getIndex()==TyPVertex.n;
		
		EGraph<TyPVertex,ActionSimpleEdge<TyPVertex,Integer>> graph = Graphs2.simpleVirtualGraphLast(e1,v->v.getMaxCarga());		
		
		
		AStar<TyPVertex, ActionSimpleEdge<TyPVertex, Integer>> ms = GraphAlg.aStarGoal(
				graph,
				goal,
				(v1,p,v2)->0.);
		
//		ms.stream().forEach(v->System.out.println(v));
		
		GraphPath<TyPVertex,ActionSimpleEdge<TyPVertex,Integer>> path = ms.search().get();
//		List<MochilaEdge> edges = path.getEdgeList();
//		System.out.println(path);
		SolucionTyP s = TyPVertex.getSolucion(path);
		System.out.println(s);
	}

}
