package us.lsi.alg.mochila;

import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearchOnGraph;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;
import us.lsi.path.EGraphPath;

public class TestBTMochilaGraph {

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
		
		SolucionMochila sm = MochilaVertex.getSolucion(path.get().getEdgeList());
		
		Double bv = path.get().getWeight();
		
		var ms = BT.backTrackingEnd(
				graph,
				e2,
				MochilaHeuristic::heuristic,
				MochilaVertex::getSolucion,
				MochilaVertex::copy,
				BTType.Max);
		
		ms.withGraph = true;
		
//		ms.bestValue = bv;
//		ms.solutions.add(sm);
		
		ms.search();
		
		SolucionMochila s = ms.getSolution().orElse(null);
		GraphPath<MochilaVertex, MochilaEdge> sp = ms.optimalPath;
		Graphs2.toDot(ms.outGraph,"ficheros/MochilaBTGraph2.gv",
				v->v.toString(),
				e->e.action().toString(),
				v->GraphColors.getColorIf(Color.red,v.equals(e2)),
				e->GraphColors.getColorIf(Color.red,sp.getEdgeList().contains(e))
				);
		System.out.println(s);
		System.out.println(ms.getSolutions().stream().max(Comparator.comparing(x->x.getValor())).get());

	}

}
