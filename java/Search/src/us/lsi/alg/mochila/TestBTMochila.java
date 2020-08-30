package us.lsi.alg.mochila;


import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearch;
import us.lsi.graphs.alg.BackTracking.BTType;
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
		EGraph<MochilaVertex, MochilaEdge> graph = Graphs2.simpleVirtualGraph(e1,x->x.getEdgeWeight());		
		
		GreedySearch<MochilaVertex, MochilaEdge> rr = GraphAlg.greedy(graph,MochilaVertex::greedyEdge,e->e.equals(e2));
		GraphPath<MochilaVertex, MochilaEdge> path = rr.search();
		
		SolucionMochila sm = MochilaVertex.getSolucion(path.getEdgeList());
		
		Double bv = rr.weight().get();
		
		var ms = BT.backTrackingEnd(
				graph,
				e2,
				MochilaHeuristic::heuristic,
				MochilaVertex::getSolucion,
				MochilaVertex::copy,
				BTType.Max);		
		
		ms.bestValue = bv;
		ms.solutions.add(sm);
		
		ms.search();
		SolucionMochila s = ms.getSolution();
		System.out.println(s);
		System.out.println(ms.getSolutions());
	}

}
