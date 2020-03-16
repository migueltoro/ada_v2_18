package us.lsi.astar.mochila;

import java.util.Locale;

import org.jgrapht.Graph;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.search.DynamicProgrammingSearch;
import us.lsi.graphs.search.DynamicProgrammingSearch.PDType;
import us.lsi.graphs.search.Search;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;

public class TestPD {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;	
		MochilaVertex e1 = MochilaVertex.of(78.);
		MochilaVertex e2 = MochilaVertex.lastVertex();
		Graph<MochilaVertex, MochilaEdge> graph = Graphs2.astarSimpleVirtualGraph(x->x.getEdgeWeight());		
		DynamicProgrammingSearch<MochilaVertex, MochilaEdge, SolucionMochila> ms = 
				Search.dynamicProgramming(graph,e1,e2,
				MochilaVertex::heuristic,
				TestSearchBackTracking::getSolucion,
				PDType.Max);
		ms.search();
		System.out.println(ms.solutions.get(e1));
		SolucionMochila s = ms.getSolution(e1);
		System.out.println(s);
	}

}
