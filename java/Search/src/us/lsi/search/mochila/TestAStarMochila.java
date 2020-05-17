package us.lsi.search.mochila;

import java.util.List;
import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.search.GSearch;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;

public class TestAStarMochila {

	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
//		Integer n = DatosMochila.numeroDeObjetos;
		DatosMochila.capacidadInicial = 78;	
		MochilaVertex e1 = MochilaVertex.of(78.);
		MochilaVertex e2 = MochilaVertex.lastVertex();
		EGraph<MochilaVertex, MochilaEdge> graph = SimpleVirtualGraph.sum(e1,x->-x.getEdgeWeight());		
		
		GSearch<MochilaVertex,MochilaEdge> ms = GSearch.aStarEnd(
				graph,
				e2,
				MochilaHeuristic::voraz);
		
		GraphPath<MochilaVertex,MochilaEdge> path = ms.pathTo(e2);
		List<MochilaEdge> edges = path.getEdgeList();
		System.out.println(edges);
		SolucionMochila s = MochilaVertex.getSolucion(edges);
		System.out.println(s);
	}

}
