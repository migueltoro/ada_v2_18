package us.lsi.alg.recorridos;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.alg.BreadthSearch;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.EGraph;

public class AnchuraTest {

	public static void main(String[] args) {
		
		SimpleWeightedGraph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::getKm);
		
		graph.addVertex(Ciudad.ofName("Londres"));
		
		System.out.println(graph);
		System.out.println(graph.edgeSet());
		
		EGraph<Ciudad,Carretera> g = Graphs2.eGraph(graph,Ciudad.ofName("Sevilla"));
		
		BreadthSearch<Ciudad, Carretera> ra = GraphAlg.breadth(g,Ciudad.ofName("Sevilla"));
		
//		GraphPath<Ciudad, Carretera> carreteras = ra.pathTo(Ciudad.ofName("Almeria")).get();
		
		Optional<Ciudad> ciudad = ra.stream().filter(v->v.equals(Ciudad.ofName("Londres"))).findFirst();
		
		System.out.println(ciudad.isPresent());

	}
}
