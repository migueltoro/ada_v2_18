package us.lsi.alg.mochila;


import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;
import us.lsi.mochila.datos.DatosMochila;


public class TestGreadyMochila {
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		MochilaVertex.capacidadInicial = 78;
		MochilaVertex v1 = MochilaVertex.initialVertex();
		MochilaVertex v2 = MochilaVertex.lastVertex();
//		Predicate<MochilaVertex> goal = v->v.equals(v2);
//		System.out.println(e1);
//		System.out.println(e2);			
		Double r2 = MochilaHeuristic.heuristic(v1,MochilaVertex.goal(),v2);
		System.out.println("1 "+r2);
		
		EGraph<MochilaVertex,MochilaEdge> graph = 
				SimpleVirtualGraph.sum(v1,MochilaVertex.goal(),x->x.weight(),v2);
		
		GreedyOnGraph<MochilaVertex, MochilaEdge> gs = GreedyOnGraph.of(graph,MochilaVertex::greedyEdge);
		
		GraphPath<MochilaVertex, MochilaEdge> gp = gs.path();
		
		System.out.println(gp.getWeight());
		
//		Optional<GraphPath<MochilaVertex, MochilaEdge>> r = gs.path();
//		System.out.println("2 "+r.get().getWeight());
//		System.out.println(r.getWeight());
//		Double r3 = MochilaHeuristic.voraz(e1, e->e.equals(e2),e2);
//		System.out.println(r3);
//		GraphPath<MochilaVertex, MochilaEdge> r4 = MochilaHeuristic.greadyPath(e1,e->e.equals(e2));
//		System.out.println(r4);
//		System.out.println(r4.getWeight());
//		EGraph<MochilaVertex,MochilaEdge> graph = Graphs2.simpleVirtualGraph(e1);
//		GreedySearch<MochilaVertex, MochilaEdge> rr = GraphAlg.greedy(graph,
//				MochilaVertex::greedyEdgeHeuristic,
//				e->e.equals(e2));
//		System.out.println(rr.weightToEnd());

	}

}
