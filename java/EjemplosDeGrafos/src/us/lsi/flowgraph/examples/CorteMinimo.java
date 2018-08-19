package us.lsi.flowgraph.examples;


import java.util.Set;


import org.jgrapht.alg.StoerWagnerMinimumCut;
import org.jgrapht.alg.flow.EdmondsKarpMFImpl;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.GraphsReader;


/**
 * Resuelve varios problemas de corte mínimo
 * 
 * @author Miguel Toro
 *
 */

public class CorteMinimo {

	public static SimpleWeightedGraph<Ciudad,Carretera> creaFichero(String fileIn){
		
		SimpleWeightedGraph<Ciudad,Carretera> graph =  
			GraphsReader.newGraph("ficheros/andalucia.txt",
				Ciudad::create, 
				Carretera::create,
				()->new SimpleWeightedGraph<>(null,null),
				Carretera::getKm);
	
		
		return graph;
	}
	
	
	public static void main(String[] args) {
		
		var graph = CorteMinimo.creaFichero("ficheros/andalucia.txt");
		StoerWagnerMinimumCut<Ciudad,Carretera> a = new StoerWagnerMinimumCut<Ciudad,Carretera>(graph);
		Set<Ciudad> source = a.minCut();
		System.out.println(source);
		EdmondsKarpMFImpl<Ciudad,Carretera> a2 = new EdmondsKarpMFImpl<Ciudad,Carretera>(graph);
		Ciudad from = Ciudad.create("Antequera");
		Ciudad to = Ciudad.create("Almeria");
		Double r = a2.calculateMinCut(from, to);
		Set<Ciudad> source2 = a2.getSourcePartition();
		Set<Ciudad> target2 = a2.getSinkPartition();
		Set<Carretera> edges = a2.getCutEdges();
		System.out.println(r+"\n"+source2+"\n"+target2+"\n"+edges);		
	}

}
