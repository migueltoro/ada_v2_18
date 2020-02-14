package us.lsi.graphs.examples;

import java.io.PrintWriter;
import java.util.Set;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.IntegerComponentNameProvider;

import us.lsi.common.Files2;
import us.lsi.common.Sets2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphcolors.GraphColors;
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
		DOTExporter<Ciudad, Carretera> de = new DOTExporter<Ciudad, Carretera>(new IntegerComponentNameProvider<>(),
				x -> x.getNombre(), 
				x -> String.format("%.2f",x.getKm())
				);		
		PrintWriter f1 = Files2.getWriter("ficheros/minimimumCompleteAndalucia1.gv");
		de.exportGraph(graph, f1);
		
		ShortestTour<Ciudad, Carretera, SimpleWeightedGraph<Ciudad, Carretera>> a = 
				ShortestTour.of(
						graph, 
						Graphs2::simpleWeightedGraph, 
						Carretera::ofWeight);
		Set<Ciudad> vertices = Sets2.newSet(Ciudad.ofName("Jaen"));
		GraphPath<Ciudad, Carretera> r = a.getTour(Ciudad.ofName("Sevilla"),Ciudad.ofName("Almeria"),vertices);
		System.out.println(r.getVertexList());
		System.out.println(r.getEdgeList());
		DOTExporter<Ciudad, Carretera> de2 = new DOTExporter<Ciudad, Carretera>(new IntegerComponentNameProvider<>(),
				x -> x.getNombre(), 
				x -> String.format("%.2f",x.getKm()),
				null,
				e -> GraphColors.getStyleIf("bold", e, x -> r.getEdgeList().contains(x))
				);	
		PrintWriter f2 = Files2.getWriter("ficheros/minimimumCompleteAndalucia2.gv");
		de2.exportGraph(graph, f2);
	}

}
