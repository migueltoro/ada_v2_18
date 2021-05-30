package us.lsi.alg.mochila;

import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearchOnGraph;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;
import us.lsi.path.EGraphPath;


public class TestPDRMochila {
	
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		MochilaVertex.capacidadInicial = 78;
		MochilaVertex e1 = MochilaVertex.initialVertex();
		MochilaVertex e2 = MochilaVertex.lastVertex();
		EGraph<MochilaVertex, MochilaEdge> graph = Graphs2.simpleVirtualGraphSum(e1,x->x.weight());	
		
		GreedySearchOnGraph<MochilaVertex, MochilaEdge> rr = 
				GraphAlg.greedy(graph,MochilaVertex::greedyEdge,e->e.equals(e2), v->true);
		Optional<EGraphPath<MochilaVertex, MochilaEdge>> path = rr.search();	
		Double bv = path.get().getWeight();
		
		System.out.println("1 = "+bv);
		
		DynamicProgrammingReduction<MochilaVertex, MochilaEdge> ms = 
				DPR.dynamicProgrammingReduction(graph,
						v->v.goal(),
						e2,
						MochilaHeuristic::heuristic,
						PDType.Max);
		
		ms.bestValue = bv;
		ms.solutionPath = path.get();
		
		Optional<GraphPath<MochilaVertex, MochilaEdge>>  sp = ms.search();
		GraphPath<MochilaVertex, MochilaEdge> s1 = sp.get();
		System.out.println(s1);
		SolucionMochila s = MochilaVertex.getSolucion(s1);
		System.out.println(s);
	}

}
