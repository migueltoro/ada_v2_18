package us.lsi.graphs.examples;

import java.io.PrintWriter;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.HamiltonianCycleAlgorithm;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.IntegerComponentNameProvider;

import us.lsi.common.Files2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphcolors.GraphColors;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class Tsp {

	public static void main(String[] args) {
		SimpleWeightedGraph<Ciudad, Carretera> graph = GraphsReader.newGraph("ficheros/andalucia.txt", Ciudad::create,
				Carretera::create, () -> new SimpleWeightedGraph<Ciudad, Carretera>(Ciudad::create, Carretera::create),
				Carretera::getKm);
		SimpleWeightedGraph<Ciudad, Carretera> graph2 = Graphs2.explicitCompleteGraph(graph, 1000.,
				() -> new SimpleWeightedGraph<Ciudad, Carretera>(Ciudad::create, Carretera::create), 
				Carretera::create,
				Carretera::getKm);
		HamiltonianCycleAlgorithm<Ciudad, Carretera> a = new HeldKarpTSP<>();
		GraphPath<Ciudad, Carretera> r = a.getTour(graph2);
		System.out.println(r.getVertexList());
		System.out.println(r.getEdgeList());
		System.out.println(r.getWeight());
		System.out.println(r.getEdgeList().stream().mapToDouble(x->x.getKm()).sum());
		System.out.println(r.getEdgeList().stream().mapToDouble(x->graph2.getEdgeWeight(x)).sum());
		ShortestPathAlgorithm<Ciudad, Carretera> dj = new DijkstraShortestPath<Ciudad, Carretera>(graph2);
		Ciudad from = Ciudad.create("Huelva");
		Ciudad to = Ciudad.create("Almeria");
		GraphPath<Ciudad, Carretera> gp = dj.getPath(from, to);
		System.out.println(gp);
		System.out.println(gp.getVertexList());
		System.out.println(gp.getWeight());
		System.out.println(gp.getEdgeList().stream().mapToDouble(x->x.getKm()).sum());
		DOTExporter<Ciudad, Carretera> de = new DOTExporter<Ciudad, Carretera>(new IntegerComponentNameProvider<>(),
				x -> x.getNombre(), x -> x.getNombre() + "--" + x.getKm(), null,
				e -> GraphColors.getStyleIf("bold", e, x -> r.getEdgeList().contains(x)));
		PrintWriter f1 = Files2.getWriter("ficheros/tspAndalucia2.gv");
		de.exportGraph(graph2, f1);
	}

}
