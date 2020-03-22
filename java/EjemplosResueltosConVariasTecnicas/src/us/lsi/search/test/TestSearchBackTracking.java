package us.lsi.search.test;

import java.util.List;
import java.util.Locale;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.search.BTSearch;
import us.lsi.graphs.search.BackTrackingSearch.BTType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;

import us.lsi.astar.mochila.MochilaEdge;
import us.lsi.astar.mochila.MochilaVertex;


public class TestSearchBackTracking {
	
	public static SolucionMochila getSolucion(List<MochilaEdge> edges){
		return MochilaVertex.getSolucion(edges);
	}

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;	
		MochilaVertex e1 = MochilaVertex.of(78.);
		MochilaVertex e2 = MochilaVertex.lastVertex();
		EGraph<MochilaVertex, MochilaEdge> graph = Graphs2.astarSimpleVirtualGraph(x->x.getEdgeWeight());		
		BTSearch<MochilaVertex, MochilaEdge, SolucionMochila> ms = BTSearch.backTracking(graph,e1,e2,
				MochilaVertex::heuristic,TestSearchBackTracking::getSolucion,MochilaVertex::copy,BTType.Max);
		ms.search();
//		GraphPath<MochilaVertex,MochilaEdge> path = ms.getSolution();
//		List<MochilaEdge> edges = path.getEdgeList();
//		System.out.println(edges);
		SolucionMochila s = ms.getSolution();
		System.out.println(s);
		System.out.println(ms.getSolutions().size());
	}

}
