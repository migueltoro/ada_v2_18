package us.lsi.search.test;

import java.util.List;
import java.util.Locale;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.search.Search;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;

import us.lsi.astar.mochila.MochilaEdge;
import us.lsi.astar.mochila.MochilaVertex;

public class TestSearchAStar {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;	
		MochilaVertex e1 = MochilaVertex.of(78.);
		MochilaVertex e2 = MochilaVertex.lastVertex();
		Graph<MochilaVertex, MochilaEdge> graph = Graphs2.astarSimpleVirtualGraph(x->-x.getEdgeWeight());		
		Search<MochilaVertex,MochilaEdge> ms = Search.aStar(graph,e1,e2,MochilaVertex::heuristic);
		MochilaVertex vf = ms.find(e2);
		GraphPath<MochilaVertex,MochilaEdge> path = ms.pathFromOrigin(vf);
		List<MochilaEdge> edges = path.getEdgeList();
		System.out.println(edges);
		SolucionMochila s = MochilaVertex.getSolucion(edges);
		System.out.println(s);
	}

}
