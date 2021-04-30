package us.lsi.graphs.examples;

import java.util.Map;

import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.common.Map2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;


import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm.SpanningTree;
import org.jgrapht.alg.interfaces.VertexCoverAlgorithm;
import org.jgrapht.alg.interfaces.VertexCoverAlgorithm.VertexCover;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.alg.vertexcover.RecursiveExactVCImpl;

/**
 * Calcula varios recubrimiento de un grafo
 * 
 * @author Miguel Toro
 *
 */
public class RecubrimientoMinimo {
	
	public static void main(String[] args) {
		
		SimpleWeightedGraph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::getKm);
		
		SpanningTreeAlgorithm<Carretera> ast = new KruskalMinimumSpanningTree<>(graph);
		SpanningTree<Carretera> r = ast.getSpanningTree();
		
		System.out.println(r);	
		
		Map<Ciudad,Double> habitantes = Map2.newHashMap(x->1/x.getHabitantes());
		
		VertexCoverAlgorithm<Ciudad> vc = new RecursiveExactVCImpl<>(graph,habitantes);
		
		VertexCover<Ciudad> r2 = vc.getVertexCover();
		
		
		Graphs2.<Ciudad,Carretera>toDot(graph,"ficheros/andaluciaSpanningTree.gv",
				x->String.format("%s",x.getNombre()),
				x->String.format("%.sf",x.getKm()),
				v->GraphColors.getColor(Color.black),
				e->GraphColors.getStyleIf(Style.bold,r.getEdges().contains(e)));
		
		Graphs2.<Ciudad,Carretera>toDot(graph,"ficheros/andaluciaVertexCover.gv",
				x->String.format("%s",x.getNombre()),
				x->String.format("%.sf",x.getKm()),
				v->GraphColors.getColorIf(Color.green,Color.blue,r2.contains(v)),
				e->GraphColors.getStyle(Style.solid));
		
	}

	
	
}
