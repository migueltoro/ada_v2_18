package us.lsi.graphs.examples;


import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.colors.GraphColors.Style;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
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
				GraphsReader.newGraph("ficheros/PI3Ej10DatosEntrada_andalucia.txt",
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::getKm);
		ShortestPathAlgorithm<Ciudad,Carretera> a = new DijkstraShortestPath<Ciudad,Carretera>(graph);
		Ciudad from = Ciudad.ofName("Sevilla");
		Ciudad to = Ciudad.ofName("Almeria");
		GraphPath<Ciudad,Carretera> gp =  a.getPath(from,to);
		System.out.println(gp);
		System.out.println(gp.getVertexList());	
		
		SimpleWeightedGraph<Ciudad,Carretera> graph2 = 
				Graphs2.subGraph(graph, 
						null, 
						e->gp.getEdgeList().contains(e),
						()->new SimpleWeightedGraph<>(Ciudad::of,Carretera::of));
		
		GraphColors.toDot(graph,"ficheros/caminoMinimoAndalucia1.gv",x->x.getNombre(),x->x.getNombre()+"--"+x.getKm());
		
		GraphColors.toDot(graph2,"ficheros/caminoMinimoAndalucia2.gv",
				x->x.getNombre(),
				x->x.getNombre()+"--"+x.getKm(),
				v->GraphColors.color(Color.black),
				e->GraphColors.style(Style.bold));
		
		
	}

}
