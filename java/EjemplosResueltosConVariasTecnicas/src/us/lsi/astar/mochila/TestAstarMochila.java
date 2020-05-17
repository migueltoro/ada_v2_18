package us.lsi.astar.mochila;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import us.lsi.astar.AStarAlgorithm;
import us.lsi.common.TriFunction;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.virtual.SimpleVirtualGraph;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;


public class TestAstarMochila {

	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila3.txt");
		DatosMochila.capacidadInicial = 8;		
		MochilaVertex e1 = MochilaVertex.of(8.);
		MochilaVertex e2 = MochilaVertex.lastVertex();
		TriFunction<MochilaVertex,Predicate<MochilaVertex>,MochilaVertex,Double> heuristic = 
				(v1,p,v2)->-MochilaVertex.voraz(v1,v2);	
		
		SimpleVirtualGraph<MochilaVertex, MochilaEdge> graph = Graphs2.sum(e1,e->-e.getEdgeWeight());
		
		AStarAlgorithm<MochilaVertex,MochilaEdge> a = AStarAlgorithm.of(graph,e1,null,e2,heuristic);
		List<MochilaEdge> edges = a.getPathEdgeList();
		System.out.println(edges);
		SolucionMochila s = MochilaVertex.getSolucion(edges);
		System.out.println(s);
	}

}
