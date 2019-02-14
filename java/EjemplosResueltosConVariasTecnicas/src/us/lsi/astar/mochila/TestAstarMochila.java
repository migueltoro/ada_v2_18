package us.lsi.astar.mochila;

import java.util.List;
import java.util.function.Predicate;

import us.lsi.astar.AStarAlgorithm;
import us.lsi.astar.AStarGraph;
import us.lsi.astar.AStarSimpleVirtualGraph;
import us.lsi.astar.PredicateHeuristic;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;



public class TestAstarMochila {
	
	
	
	public static void main(String[] args) {
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;		
		MochilaVertex e1 = MochilaVertex.of(78);
		Integer n = DatosMochila.getObjetos().size();
		Predicate<MochilaVertex> goal = (MochilaVertex v)->v.index==n;
		PredicateHeuristic<MochilaVertex> predicateHeuristic = (x,p)->x.voraz(p);
		AStarGraph<MochilaVertex,MochilaEdge> graph = AStarSimpleVirtualGraph.of(x->x.getEdgeWeight());
		AStarAlgorithm<MochilaVertex,MochilaEdge> a = AStarAlgorithm.of(graph,e1,goal,predicateHeuristic);
		List<MochilaEdge> vertices = a.getPath().getEdgeList();
		SolucionMochila s = MochilaVertex.getSolucion(vertices);
		System.out.println(s);
	}

}
