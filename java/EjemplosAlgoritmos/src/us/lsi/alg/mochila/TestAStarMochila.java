package us.lsi.alg.mochila;

import java.util.List;
import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;

public class TestAStarMochila {

	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
//		Integer n = DatosMochila.numeroDeObjetos;
//		DatosMochila.capacidadInicial = 78;	
		MochilaVertex.capacidadInicial = 78;
		MochilaVertex e1 = MochilaVertex.initialVertex();
		MochilaVertex e2 = MochilaVertex.lastVertex();
		EGraph<MochilaVertex, MochilaEdge> graph = 
			Graphs2.simpleVirtualGraphSum(e1,MochilaVertex.goal(),e2,v->true,v->-v.weight());		
		
		AStar<MochilaVertex, MochilaEdge> ms = 
				GraphAlg.aStar(graph,MochilaHeuristic::heuristic_negate);
		
		GraphPath<MochilaVertex,MochilaEdge> path = ms.search().orElse(null);
		List<MochilaEdge> edges = path.getEdgeList();
		System.out.println(edges);
		SolucionMochila s = MochilaVertex.getSolucion(edges);
		System.out.println(s);
	}

}
