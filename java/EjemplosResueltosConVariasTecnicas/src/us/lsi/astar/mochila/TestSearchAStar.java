package us.lsi.astar.mochila;

import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.search.Search;
import us.lsi.mochila.datos.DatosMochila;

public class TestSearchAStar {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;		
		MochilaVertex e1 = MochilaVertex.of(78.);
		MochilaVertex e2 = MochilaVertex.lastVertex();
		Integer n = DatosMochila.getObjetos().size();
		Graph<MochilaVertex, MochilaEdge> graph = Graphs2.astarSimpleVirtualGraph(x->x.getEdgeWeight());
		Predicate<MochilaVertex> goal = (MochilaVertex v)->v.index==n;
		BiFunction<MochilaVertex,MochilaVertex,Double> heuristic = (v1,v2)->
				Search.<MochilaVertex,MochilaEdge,Double>greedy(v1,
				v->v.greedyAction(),
				(v,a)->v.neighbor(a),
				MochilaEdge::of).findWeight(v2);	
		Search<MochilaVertex,MochilaEdge> ms = Search.aStar(graph,e1,e2,heuristic);
		MochilaVertex vf = ms.find(goal);
		GraphPath<MochilaVertex,MochilaEdge> path = ms.pathFromOrigin(vf);
		List<MochilaEdge> edges = path.getEdgeList();
		System.out.println(edges);
//		SolucionMochila s = MochilaVertex.getSolucion(edges);
		System.out.println(edges.stream().mapToDouble(e->e.getEdgeWeight()).sum());

	}

}
