package us.lsi.astar.mochila;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import us.lsi.astar.AStarAlgorithm;
import us.lsi.astar.AStarGraph;
import us.lsi.astar.PredicateHeuristic;
import us.lsi.graphs.Graphs2;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;




public class TestAstarMochila {

	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;		
		MochilaVertex e1 = MochilaVertex.of(78.);
		Integer n = DatosMochila.getObjetos().size();
		Predicate<MochilaVertex> goal = (MochilaVertex v)->v.index==n;
		PredicateHeuristic<MochilaVertex> predicateHeuristic = (x,p)->x.voraz(p);
		AStarGraph<MochilaVertex,MochilaEdge> graph = Graphs2.astarSimpleVirtualGraph(x->x.getEdgeWeight());
		AStarAlgorithm<MochilaVertex,MochilaEdge> a = AStarAlgorithm.of(graph,e1,goal,predicateHeuristic);
		List<MochilaEdge> edges = a.getPathEdgeList();
		System.out.println(edges);
		SolucionMochila s = MochilaVertex.getSolucion(edges);
		System.out.println(s);
	}

}
