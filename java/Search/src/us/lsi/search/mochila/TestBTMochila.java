package us.lsi.search.mochila;


import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.search.BTSearch;
import us.lsi.graphs.search.GSearch;
import us.lsi.graphs.search.GreedySearch;
import us.lsi.graphs.search.BackTracking.BTType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;



public class TestBTMochila {


	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;	
		MochilaVertex e1 = MochilaVertex.of(78.);
		MochilaVertex e2 = MochilaVertex.lastVertex();
		EGraph<MochilaVertex, MochilaEdge> graph = Graphs2.sum(e1,x->x.getEdgeWeight());		
		
		GreedySearch<MochilaVertex, MochilaEdge> rr = GSearch.greedy(graph,MochilaVertex::greadyEdge,e->e.equals(e2));
		GraphPath<MochilaVertex, MochilaEdge> path = rr.pathToEnd();
		SolucionMochila sm = MochilaVertex.getSolucion(path);
		Double bv = path.getWeight();
		
		var ms = BTSearch.backTrackingEnd(
				graph,
				e2,
				MochilaHeuristic::heuristic,
				MochilaVertex::getSolucion,
				MochilaVertex::copy,BTType.Max);		
		
		ms.bestValue = bv;
		ms.solutions.add(sm);
		
		ms.search();
		SolucionMochila s = ms.getSolution();
		System.out.println(s);
		System.out.println(ms.getSolutions());
	}

}
