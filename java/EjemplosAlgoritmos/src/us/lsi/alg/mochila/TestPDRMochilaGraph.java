package us.lsi.alg.mochila;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import static us.lsi.colors.GraphColors.*;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearchOnGraph;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.path.EGraphPath;

public class TestPDRMochilaGraph {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		MochilaVertex.capacidadInicial = 78;
		MochilaVertex e1 = MochilaVertex.initialVertex();
		MochilaVertex e2 = MochilaVertex.lastVertex();
		EGraph<MochilaVertex, MochilaEdge> graph = 
				Graphs2.simpleVirtualGraphSum(e1,MochilaVertex.goal(),e2,MochilaVertex.constraint(),x->x.weight());	
		
		GreedySearchOnGraph<MochilaVertex, MochilaEdge> rr = 
				GraphAlg.greedy(graph,MochilaVertex::greedyEdge);
		Optional<EGraphPath<MochilaVertex, MochilaEdge>> path = rr.search();	
		Double bv = path.get().getWeight();
		
		System.out.println("1 = "+bv);
		
		DynamicProgrammingReduction<MochilaVertex, MochilaEdge> ms = 
				DPR.dynamicProgrammingReduction(graph,
						MochilaHeuristic::heuristic,
						PDType.Max);
		
//		ms.bestValue = 610.;
//		ms.solutionPath = n;
		
//		Optional<GraphPath<MochilaVertex, MochilaEdge>>  sp = ms.search();
//		GraphPath<MochilaVertex, MochilaEdge> s1 = sp.get();
//		System.out.println(s1);
//		SolucionMochila s = MochilaVertex.getSolucion(s1);
//		System.out.println(s);
		ms.withGraph = true;
		ms.search();
		
		Predicate<MochilaVertex> pv = v->ms.optPath.get().getVertexList().contains(v);
		Predicate<MochilaEdge> pe= e->ms.optPath.get().getEdgeList().contains(e);
		
		GraphColors.toDot(ms.outGraph,"ficheros/MochilaPDRGraph.gv",
				v->String.format("(%d,%d)",v.index(),v.capacidadRestante()),
				e->e.action().toString(),
				v->all(colorIf(Color.red,pv.test(v)),styleIf(Style.bold,pv.test(v))),
				e->all(colorIf(Color.red,pe.test(e)),styleIf(Style.bold,pe.test(e)))
				);
	}

}
