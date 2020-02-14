package us.lsi.graphs.examples;

import java.io.PrintWriter;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.IntegerComponentNameProvider;

import us.lsi.common.Files2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphcolors.GraphColors;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

/**
 * Resuelve un problema de camino mínimo
 * 
 * @author Miguel Toro
 *
 */

public class CaminoMinimo {

	public static void main(String[] args) {
		SimpleWeightedGraph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::getKm);
		ShortestPathAlgorithm<Ciudad,Carretera> a = new DijkstraShortestPath<Ciudad,Carretera>(graph);
		Ciudad from = Ciudad.ofName("Huelva");
		Ciudad to = Ciudad.ofName("Almeria");
		GraphPath<Ciudad,Carretera> gp =  a.getPath(from,to);
		System.out.println(gp);
		System.out.println(gp.getVertexList());	
		
		SimpleWeightedGraph<Ciudad,Carretera> graph2 = 
				Graphs2.subGraph(graph, 
						null, 
						e->gp.getEdgeList().contains(e),
						()->new SimpleWeightedGraph<>(Ciudad::of,Carretera::of));
		
		DOTExporter<Ciudad,Carretera> de1 = new DOTExporter<Ciudad,Carretera>(
				new IntegerComponentNameProvider<>(),
				x->x.getNombre(), 
				x->x.getNombre()+"--"+x.getKm());
		
		DOTExporter<Ciudad,Carretera> de2 = new DOTExporter<Ciudad,Carretera>(
				new IntegerComponentNameProvider<>(),
				x->x.getNombre(), 
				x->x.getNombre()+"--"+x.getKm(),
				null,
				e->GraphColors.getStyleIf("bold",e,x->true));
		
		PrintWriter f1 = Files2.getWriter("ficheros/caminoMinimoAndalucia1.gv");
		de1.exportGraph(graph, f1);
		
		PrintWriter f2 = Files2.getWriter("ficheros/caminoMinimoAndalucia2.gv");
		de2.exportGraph(graph2, f2);
	}

}
