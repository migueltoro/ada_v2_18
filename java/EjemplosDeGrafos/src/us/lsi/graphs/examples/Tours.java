package us.lsi.graphs.examples;

import java.util.Set;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.common.Set2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.tour.ShortestTour;

public class Tours {

	public static void main(String[] args) {
		SimpleWeightedGraph<Ciudad, Carretera> graph = GraphsReader.newGraph("ficheros/andalucia.txt", 
				Ciudad::ofFormat,
				Carretera::ofFormat, 
				Graphs2::simpleWeightedGraph,
				Carretera::getKm);
		
		ShortestTour<Ciudad, Carretera, SimpleWeightedGraph<Ciudad, Carretera>> a = 
				ShortestTour.of(
						graph, 
						Graphs2::simpleWeightedGraph, 
						Carretera::ofWeight);
		Set<Ciudad> vertices = Set2.of(Ciudad.ofName("Jaen"));
		
		GraphPath<Ciudad, Carretera> r = a.getTour(Ciudad.ofName("Sevilla"),Ciudad.ofName("Almeria"),vertices);
		System.out.println(r.getVertexList());
		System.out.println(r.getEdgeList());
		
		Graphs2.<Ciudad,Carretera>toDot(graph,"ficheros/andaluciaSpanningTree.gv",
				x->String.format("%s",x.getNombre()),
				x->String.format("%.sf",x.getKm()),
				v->GraphColors.getColor(Color.black),
				e->GraphColors.getStyleIf(Style.bold,r.getEdgeList().contains(e)));
		
	}

}
