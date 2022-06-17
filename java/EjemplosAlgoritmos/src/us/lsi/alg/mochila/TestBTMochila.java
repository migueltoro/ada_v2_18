package us.lsi.alg.mochila;

import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;
import us.lsi.path.EGraphPath.PathType;



public class TestBTMochila {


	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		MochilaVertex.capacidadInicial = 101;
		MochilaVertex e1 = MochilaVertex.initialVertex();
		
		EGraph<MochilaVertex, MochilaEdge> graph = 
				EGraph.virtual(e1,MochilaVertex.goal(), PathType.Sum, Type.Max)
				.greedyEdge(MochilaVertex::greedyEdge)
				.heuristic(MochilaHeuristic::heuristic)
				.build();	
		
		GreedyOnGraph<MochilaVertex, MochilaEdge> rr = GreedyOnGraph.of(graph);
		
		GraphPath<MochilaVertex, MochilaEdge> path = rr.path();
		
//		SolucionMochila s0 = MochilaVertex.getSolucion(path);
//		
//		System.out.println(s0);
		System.out.println(path.getEdgeList().stream().map(e->e.action()).toList());
		
		BackTracking<MochilaVertex, MochilaEdge,SolucionMochila> ms = BackTracking.of(
				graph,
				MochilaVertex::getSolucion,
				path.getWeight(),path,true);		
		
		Optional<SolucionMochila> s = ms.search();
//		SolucionMochila s = MochilaVertex.getSolucion(ms.optimalPath().get());
		System.out.println(s);
		System.out.println(ms.optimalPath().get().getEdgeList().stream().map(e->e.action()).toList());
			
		GraphPath<MochilaVertex, MochilaEdge> sp = ms.optimalPath().get();
		GraphColors.toDot(ms.graph(),"ficheros/MochilaBTGraph2.gv",
				v->String.format("(%d,%d)",v.index(),v.capacidadRestante()),
				e->e.action().toString(),
				v->GraphColors.colorIf(Color.red,MochilaVertex.goal().test(v)),
				e->GraphColors.colorIf(Color.red,sp.getEdgeList().contains(e))
				);
	}

}
