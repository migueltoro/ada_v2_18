package us.lsi.graphs.examples;

import java.io.PrintWriter;
import java.util.Map;

import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.IntegerComponentNameProvider;

import us.lsi.common.Files2;
import us.lsi.common.Maps2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphcolors.GraphColors;
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
						Ciudad::create, 
						Carretera::create,
						()->new SimpleWeightedGraph<Ciudad,Carretera>(
								Ciudad::create,Carretera::create),
						Carretera::getKm);
		
		SpanningTreeAlgorithm<Carretera> ast = new KruskalMinimumSpanningTree<>(graph);
		SpanningTree<Carretera> r = ast.getSpanningTree();
		
		System.out.println(r);
		
		SimpleWeightedGraph<Ciudad, Carretera> subGraphEdges = Graphs2.subGraph(graph, 
				null,
				x->r.getEdges().contains(x), 
				()->new SimpleWeightedGraph<Ciudad, Carretera>(
						Ciudad::create,
						Carretera::create));	
		
		
		Map<Ciudad,Double> habitantes = Maps2.newHashMap(x->1/x.getHabitantes());
		
		VertexCoverAlgorithm<Ciudad> vc = new RecursiveExactVCImpl<>(graph,habitantes);
		
		VertexCover<Ciudad> r2 = vc.getVertexCover();
		
		
		ComponentNameProvider<Ciudad> vertexIDProvider = new IntegerComponentNameProvider<>();
		
		
		
		DOTExporter<Ciudad,Carretera> de = new DOTExporter<Ciudad,Carretera>(
				vertexIDProvider,
				x->x.getNombre(), 
				x->x.getNombre(),
				v->GraphColors.getFilledColor(r2.contains(v)?1:2),	
				e->GraphColors.getStyleIf("bold", e, x->r.getEdges().contains(x)));
				
		
		PrintWriter f = Files2.getWriter("ficheros/recubrimientoAndalucia1.gv");
		de.exportGraph(graph, f);
		
		DOTExporter<Ciudad,Carretera> de2 = new DOTExporter<Ciudad,Carretera>(
				vertexIDProvider,
				x->x.getNombre(), 
				x->x.getNombre());
		
		PrintWriter f2 = Files2.getWriter("ficheros/recubrimientoAndalucia2.gv");
		de2.exportGraph(subGraphEdges, f2);
	}

	
	
}
