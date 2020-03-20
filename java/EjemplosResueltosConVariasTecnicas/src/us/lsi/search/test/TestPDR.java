package us.lsi.search.test;

import java.util.Locale;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.search.DPRSearch;
import us.lsi.graphs.search.DynamicProgrammingReductionSearch.PDRType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;

import us.lsi.astar.mochila.MochilaEdge;
import us.lsi.astar.mochila.MochilaVertex;

public class TestPDR {
	

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;	
		MochilaVertex e1 = MochilaVertex.of(78.);
		MochilaVertex e2 = MochilaVertex.lastVertex();
		EGraph<MochilaVertex, MochilaEdge> graph = Graphs2.astarSimpleVirtualGraph(x->x.getEdgeWeight());		
		DPRSearch<MochilaVertex, MochilaEdge, SolucionMochila> ms = 
				DPRSearch.dynamicProgrammingReduction(graph,e1,e2,
				MochilaVertex::heuristic,
				TestSearchBackTracking::getSolucion,
				PDRType.Max);
		System.out.println(ms.search());
//		System.out.println(ms.solutionsTree.keySet());
//		System.out.println(ms.path(e1));
		SolucionMochila s = ms.getSolution(e1);
		System.out.println(s);
	}

}
