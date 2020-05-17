package us.lsi.graphs.examples;

import java.io.PrintWriter;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.HamiltonianCycleAlgorithm;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.IntegerComponentNameProvider;

import us.lsi.colors.GraphColors;
import us.lsi.common.Files2;
import us.lsi.common.Sets2;
import us.lsi.common.Strings2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.views.CompleteGraphView;
import us.lsi.graphs.views.SubGraphView;

public class SubGraphsAndViews {

	
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
		PrintWriter f1 = Files2.getWriter("ficheros/andalucia1.gv");
		de.exportGraph(graph, f1);
		Graph<Ciudad, Carretera> graph2 = CompleteGraphView.of(graph,
				Carretera::ofWeight,
				Double.valueOf(1000.),
				Carretera::getSource,
				Carretera::getTarget);
		HamiltonianCycleAlgorithm<Ciudad, Carretera> a = new HeldKarpTSP<>();
		GraphPath<Ciudad, Carretera> r = a.getTour(graph2);
		DOTExporter<Ciudad, Carretera> de2 = new DOTExporter<Ciudad, Carretera>(
				new IntegerComponentNameProvider<>(),
				x -> x.getNombre(), 
				x -> String.format("%.2f",x.getKm()), 
				null,
				e -> GraphColors.getStyleIf("bold", e, x -> r.getEdgeList().contains(x)));		
		PrintWriter f2 = Files2.getWriter("ficheros/tspCompleteAndalucia.gv");
		de2.exportGraph(graph2, f2);
		Strings2.toConsole(r.getEdgeList(), "Camino");
		Graph<Ciudad, Carretera> graph3 = SubGraphView.of(graph,
				Sets2.newSet(Ciudad.ofName("Sevilla"),Ciudad.ofName("Cadiz"),Ciudad.ofName("Huelva"),Ciudad.ofName("Almeria")));
		PrintWriter f3 = Files2.getWriter("ficheros/subGrafoAndalucia.gv");
		de.exportGraph(graph3, f3);
		Graph<Ciudad, Carretera> graph4 = SubGraphView.of(graph,v->v.getNombre().contains("e"),e->e.getKm()<100);
		Strings2.toConsole(graph4.edgeSet(), "Carreteras");
		Graph<Ciudad, Carretera> graph5 = 
				Graphs2.subGraph(graph,v->v.getNombre().contains("e"),e->e.getKm()<100,Graphs2::simpleWeightedGraph);
		Strings2.toConsole(graph5.edgeSet(), "Carreteras");
		Graph<Ciudad, Carretera> graph6 = 
				Graphs2.subGraphOfEdges(graph,e->e.getKm()<100,Graphs2::simpleWeightedGraph);
		Strings2.toConsole(graph6.edgeSet(), "Carreteras");
		Graph<Ciudad, Carretera> graph7 = 
				Graphs2.subGraphOfVertices(graph,v->v.getNombre().contains("e"),Graphs2::simpleWeightedGraph);
		Strings2.toConsole(graph7.edgeSet(), "Carreteras");
		Graph<Ciudad, Carretera> graph8 = 
				Graphs2.toDirectedWeightedGraphFlow(graph,
						Carretera::reverse,
						Set.of(Ciudad.ofName("Sevilla")), 
						Set.of(Ciudad.ofName("Almeria")));
		Strings2.toConsole(graph8.edgeSet(), "Carreteras");
	}

}
