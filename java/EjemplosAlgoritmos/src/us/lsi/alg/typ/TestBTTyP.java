package us.lsi.alg.typ;

import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.SimpleVirtualGraph;
import us.lsi.graphs.virtual.EGraph;

public class TestBTTyP {

	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		TyPVertex.datos("ficheros/tareas.txt",5);
		TyPVertex e1 = TyPVertex.first();
		
		EGraph<TyPVertex,SimpleEdgeAction<TyPVertex,Integer>> graph = 
				SimpleVirtualGraph.last(e1,e->e.goal(),v->v.maxCarga());		
			
		GreedyOnGraph<TyPVertex, SimpleEdgeAction<TyPVertex,Integer>> rr = 
				GreedyOnGraph.of(graph,TyPVertex::greadyEdge);
		
		GraphPath<TyPVertex, SimpleEdgeAction<TyPVertex, Integer>> path = rr.path();
		Double bv = path.getWeight();
		System.out.println(bv);
		
		BackTracking<TyPVertex, SimpleEdgeAction<TyPVertex, Integer>, SolucionTyP> ms = BackTracking.of(
						graph,				
						Heuristica::heuristic,
						TyPVertex::getSolucion,
						BTType.Min);		
		
		ms.bestValue = bv;
		ms.optimalPath = path;
		ms.withGraph = true;
		ms.search();

		System.out.println(ms.getSolution().get());
		System.out.println(ms.getSolutions().size());
		
		GraphPath<TyPVertex, SimpleEdgeAction<TyPVertex, Integer>> sp = ms.optimalPath().get();
		GraphColors.toDot(ms.graph(),"ficheros/TyPBT.gv",
				v->String.format("(%d,%.1f)",v.index(),v.maxCarga()),
				e->e.action().toString(),
				v->GraphColors.colorIf(Color.red,v.goal()),
				e->GraphColors.colorIf(Color.red,sp.getEdgeList().contains(e))
				);

	}

}
