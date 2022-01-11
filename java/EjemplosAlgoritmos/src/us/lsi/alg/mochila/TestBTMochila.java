package us.lsi.alg.mochila;

import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;



public class TestBTMochila {


	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		MochilaVertex.capacidadInicial = 78;
		MochilaVertex e1 = MochilaVertex.initialVertex();
		MochilaVertex e2 = MochilaVertex.lastVertex();
		
		SimpleVirtualGraph.endVertexG = e2;
		EGraph<MochilaVertex, MochilaEdge> graph = 
				SimpleVirtualGraph.sum(e1,MochilaVertex.goal(),x->x.weight());		
		
		GreedyOnGraph<MochilaVertex, MochilaEdge> rr = GreedyOnGraph.of(graph,MochilaVertex::greedyEdge);
		
		GraphPath<MochilaVertex, MochilaEdge> path = rr.path();
		
		BackTracking<MochilaVertex, MochilaEdge,SolucionMochila> ms = BackTracking.of(
				graph,
				MochilaHeuristic::heuristic,
				MochilaVertex::getSolucion,
				BTType.Max);		
		
		ms.withGraph = true;
		ms.bestValue = path.getWeight();
		ms.optimalPath = path;
		
		ms.search();
		SolucionMochila s = MochilaVertex.getSolucion(ms.optimalPath().get());
		System.out.println(s);
			
		GraphPath<MochilaVertex, MochilaEdge> sp = ms.optimalPath().get();
		GraphColors.toDot(ms.graph(),"ficheros/MochilaBTGraph2.gv",
				v->String.format("(%d,%d)",v.index(),v.capacidadRestante()),
				e->e.action().toString(),
				v->GraphColors.colorIf(Color.red,v.equals(e2)),
				e->GraphColors.colorIf(Color.red,sp.getEdgeList().contains(e))
				);
	}

}
