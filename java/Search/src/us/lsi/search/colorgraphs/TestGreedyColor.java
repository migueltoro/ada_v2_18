package us.lsi.search.colorgraphs;

import java.util.Locale;

import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.Graph;

import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.views.IntegerVertexGraphView;

public class TestGreedyColor {
	
	public static SimpleWeightedGraph<Ciudad, Carretera> leeGrafo(String fichero) {
		SimpleWeightedGraph<Ciudad, Carretera> graph = GraphsReader.newGraph(fichero, 
				Ciudad::ofFormat, 
				Carretera::ofFormat,
				Graphs2::simpleWeightedGraph, 
				Carretera::getKm);
		return graph;
	}

	
	public static void main(String[] args) {
		
		Locale.setDefault(new Locale("en", "US"));
		
		SimpleWeightedGraph<Ciudad, Carretera> graph = leeGrafo("./ficheros/andalucia.txt");
		
		System.out.println(graph);
		
		Graph<Integer,SimpleEdge<Integer>> g2 = IntegerVertexGraphView.of(graph);
		
		Integer n = g2.vertexSet().size();
		ColorVertex.data(9, g2);	
		ColorVertex v1 = ColorVertex.first();
		
		Integer nc = ColorHeuristic.gredyPath(v1,v->v.index==n);
		
		System.out.println(nc);
	}

}
