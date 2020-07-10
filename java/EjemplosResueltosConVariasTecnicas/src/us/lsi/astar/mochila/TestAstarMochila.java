package us.lsi.astar.mochila;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import us.lsi.common.TriFunction;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GSearch;
import us.lsi.graphs.virtual.SimpleVirtualGraph;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;


public class TestAstarMochila {

	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;		
		MochilaVertex e1 = MochilaVertex.of(0,78.);
		MochilaVertex e2 = MochilaVertex.lastVertex();
		TriFunction<MochilaVertex,Predicate<MochilaVertex>,MochilaVertex,Double> heuristic = 
				(v1,p,v2)->-MochilaVertex.voraz(v1,v2);	
		
		SimpleVirtualGraph<MochilaVertex, MochilaEdge> graph = Graphs2.simpleVirtualGraph(e1,e->-e.getEdgeWeight());
		
		AStar<MochilaVertex, MochilaEdge> a = GSearch.aStarEnd(graph,e2,heuristic);
		
		List<MochilaEdge> edges = a.pathToEnd().getEdgeList();
		System.out.println(edges);
		SolucionMochila s = MochilaVertex.getSolucion(edges);
		System.out.println(s);
	}

}
