package us.lsi.alg.mochila;


import java.util.Locale;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.SimpleDirectedGraph;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;

public class TestAStarMochila {

	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		Integer n = DatosMochila.numeroDeObjetos;
//		MochilaVertex.capacidadInicial = 2457;
		MochilaVertex.capacidadInicial = 78;
		System.out.println(Double.MAX_VALUE);
		MochilaVertex e1 = MochilaVertex.initialVertex();
		
		EGraph<MochilaVertex, MochilaEdge> graph = 
			SimpleVirtualGraph.sum(e1,MochilaVertex.goal(),v->v.weight());	
		
		GreedyOnGraph<MochilaVertex, MochilaEdge> rr = GreedyOnGraph.of(graph,MochilaVertex::greedyEdge);
		
		GraphPath<MochilaVertex, MochilaEdge> gp = rr.path();
		
		System.out.println(gp.getWeight());
	
		AStar<MochilaVertex, MochilaEdge> ms = 
				AStar.of(graph,MochilaHeuristic::heuristic,AStarType.Max);
		
		ms.bestValue = gp.getWeight();
		ms.optimalPath = gp;
		
		GraphPath<MochilaVertex, MochilaEdge> path = ms.search().get();
		SolucionMochila s = MochilaVertex.getSolucion(path);
		System.out.println(s);
		SimpleDirectedGraph<MochilaVertex, MochilaEdge> r = ms.graph();
		System.out.println(ms.tree.keySet().size());
		
		GraphColors.toDot(r,"ficheros/MochilaAstarGraph.gv",
				v->String.format("((%d,%d)",v.index(),v.capacidadRestante()),
				e->e.action().toString(),
				v->GraphColors.colorIf(Color.red,path.getVertexList().contains(v)),
				e->GraphColors.colorIf(Color.red,path.getEdgeList().contains(e))
				);
	}

}
