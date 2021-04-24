package us.lsi.alg.colorgraphs;


import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.common.Lists2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.views.IntegerVertexGraphView;
import us.lsi.graphs.virtual.EGraph;

public class TestColorAstar {

	
	public static SimpleWeightedGraph<Ciudad, Carretera> leeGrafo(String fichero) {
		SimpleWeightedGraph<Ciudad, Carretera> graph = GraphsReader.newGraph(fichero, 
				Ciudad::ofFormat, 
				Carretera::ofFormat,
				Graphs2::simpleWeightedGraph, 
				Carretera::getKm);
		return graph;
	}

	public static void main(String[] args) {

		SimpleWeightedGraph<Ciudad, Carretera> g0 = leeGrafo("./ficheros/andalucia.txt");		
//		System.out.println(g0);		
		Graph<Integer,SimpleEdge<Integer>> g2 = IntegerVertexGraphView.of(g0);	
		Integer n = g2.vertexSet().size();
		ColorVertex.data(9, g2);	
		ColorVertex e1 = ColorVertex.first();
		Predicate<ColorVertex> goal  = e->e.index() == n;
		Integer m = ColorHeuristic.gredyPath(e1, goal);
		ColorVertex.data(m, g2);
		
		EGraph<ColorVertex, ColorEdge> graph = Graphs2.simpleVirtualGraphLast(e1,v->v.nc().doubleValue());		
		
		AStar<ColorVertex, ColorEdge> ms = GraphAlg.aStarGoal(
				graph,
				goal,
				ColorHeuristic::heuristic);
		
		GraphPath<ColorVertex, ColorEdge> path = ms.search().orElse(null);
		ColorVertex lv = Lists2.last(path.getVertexList());
		System.out.println("Numero de Colores = "+lv.nc());
		System.out.println(lv.cav());

	}

}
