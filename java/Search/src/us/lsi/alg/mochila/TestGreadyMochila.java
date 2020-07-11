package us.lsi.alg.mochila;


import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearch;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.mochila.datos.DatosMochila;


public class TestGreadyMochila {
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;		
		MochilaVertex e1 = MochilaVertex.of(78.);
		MochilaVertex e2 = MochilaVertex.lastVertex();
//		System.out.println(e1);
//		System.out.println(e2);			
		Double r2 = MochilaHeuristic.heuristic(e1,e->e.equals(e2),e2);
		System.out.println(r2);
		GraphPath<MochilaVertex, MochilaEdge> r = MochilaHeuristic.greadyPath(e1,e->e.equals(e2));
		System.out.println(r);
//		System.out.println(r.getWeight());
//		Double r3 = MochilaHeuristic.voraz(e1, e->e.equals(e2),e2);
//		System.out.println(r3);
//		GraphPath<MochilaVertex, MochilaEdge> r4 = MochilaHeuristic.greadyPath(e1,e->e.equals(e2));
//		System.out.println(r4);
//		System.out.println(r4.getWeight());
		EGraph<MochilaVertex,MochilaEdge> graph = Graphs2.simpleVirtualGraph(e1);
		GreedySearch<MochilaVertex, MochilaEdge> rr = GraphAlg.greedy(graph,MochilaVertex::greadyEdgeHeuristic,e->e.equals(e2));
		System.out.println(rr.weightToEnd());

	}

}
