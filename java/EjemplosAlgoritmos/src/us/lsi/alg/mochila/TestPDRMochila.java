package us.lsi.alg.mochila;

import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearch;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;


public class TestPDRMochila {
	
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		MochilaVertex.capacidadInicial = 78;
		MochilaVertex e1 = MochilaVertex.initialVertex();
		MochilaVertex e2 = MochilaVertex.lastVertex();
		EGraph<MochilaVertex, MochilaEdge> graph = Graphs2.simpleVirtualGraph(e1,x->x.getEdgeWeight());	
		
		GreedySearch<MochilaVertex, MochilaEdge> rr = GraphAlg.greedy(graph,MochilaVertex::greedyEdge,e->e.equals(e2));
		Double bv = rr.weightToEnd().get();	
		
		System.out.println(bv);
		
		DynamicProgrammingReduction<MochilaVertex, MochilaEdge> ms = 
				DPR.dynamicProgrammingReductionEnd(graph,
						e2,
						MochilaHeuristic::heuristic,
						PDType.Max);
		
		ms.bestValue = bv;
		
		System.out.println(ms.search());
		System.out.println(ms.solutionsTree);
		GraphPath<MochilaVertex, MochilaEdge> s1 = ms.search().get();
		System.out.println(s1);
		SolucionMochila s = MochilaVertex.getSolucion(s1);
		System.out.println(s);
	}

}
