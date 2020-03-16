package us.lsi.astar.mochila;

import java.util.List;
import java.util.Locale;

import org.jgrapht.Graph;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.search.BackTrackingSearch;
import us.lsi.graphs.search.Search;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;

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
		Graph<MochilaVertex, MochilaEdge> graph = Graphs2.astarSimpleVirtualGraph(x->x.getEdgeWeight());		
		BackTrackingSearch<MochilaVertex, MochilaEdge,SolucionMochila> ms = Search.backTracking(graph,e1,e2,
				MochilaVertex::heuristic,TestSearchBackTracking::getSolucion,MochilaVertex::copy,BackTrackingSearch.BDType.Max);
		ms.search();
//		GraphPath<MochilaVertex,MochilaEdge> path = ms.getSolution();
//		List<MochilaEdge> edges = path.getEdgeList();
//		System.out.println(edges);
		SolucionMochila s = ms.getSolution();
		System.out.println(s);
		System.out.println(ms.getSolutions().size());
	}

}
