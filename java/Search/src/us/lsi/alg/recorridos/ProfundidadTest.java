package us.lsi.alg.recorridos;

import java.util.Optional;

import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.alg.BreadthSearch;
import us.lsi.graphs.alg.DephtPostSearch;
import us.lsi.graphs.alg.DephtSearch;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.EGraph;

public class ProfundidadTest {
	
public static void main(String[] args) {
		
		SimpleWeightedGraph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::getKm);
		
		graph.addVertex(Ciudad.ofName("Londres"));
		
		System.out.println(graph);
		
		EGraph<Ciudad,Carretera> g = Graphs2.eGraph(graph,Ciudad.ofName("Sevilla"));
		
		DephtSearch<Ciudad, Carretera> ra = GraphAlg.depth(g,Ciudad.ofName("Sevilla"));
		
//		GraphPath<Ciudad, Carretera> carreteras = ra.pathTo(Ciudad.ofName("Almeria")).get();
		
		ra.stream().forEach(c->System.out.println(c));;
		System.out.println("_________");
		DephtPostSearch<Ciudad, Carretera> ra2 = GraphAlg.depthPost(g,Ciudad.ofName("Sevilla"));
		ra2.stream().forEach(c->System.out.println(c));;

	}
}
