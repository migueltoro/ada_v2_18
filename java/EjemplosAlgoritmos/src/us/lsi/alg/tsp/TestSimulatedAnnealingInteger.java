package us.lsi.alg.tsp;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.alg.SimulatedAnnealingSearch;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;
import us.lsi.path.GraphPath2;
import us.lsi.streams.Stream2;

public class TestSimulatedAnnealingInteger {
	
	
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		
		SimulatedAnnealingSearch.numIntentos = 1;
		SimulatedAnnealingSearch.numPorIntento = 1000;
		SimulatedAnnealingSearch.numMismaTemperatura = 1;
		SimulatedAnnealingSearch.temperaturaInicial = 100000;
		SimulatedAnnealingSearch.alfa = 0.95;
		SimulatedAnnealingSearch.stop = v->false;
		
		Graph<Integer,SimpleEdge<Integer>> graph = AuxiliaryTsp.generate(50);
		
		TravelVertexInteger.graph = graph;
		
		System.out.println(graph);
		System.out.println(graph.edgeSet());
		List<Integer> camino = graph.vertexSet().stream().collect(Collectors.toList());
		camino.add(0);
		Collections.shuffle(camino.subList(1,camino.size()-2));
//		System.out.println(graph.getEdgeWeight(graph.getEdge(0,1)));
		GraphPath<Integer,SimpleEdge<Integer>> path = GraphPath2.ofVertices(graph,camino);
		System.out.println(path.getWeight());
		TravelVertexInteger e1 = TravelVertexInteger.of(camino);
		System.out.println(e1);
		
		EGraph<TravelVertexInteger,TravelEdgeInteger> graph2 = 
				SimpleVirtualGraph.last(e1,null,v->v.weight());	
		SimulatedAnnealingSearch<TravelVertexInteger, TravelEdgeInteger> m = 
				SimulatedAnnealingSearch.simulatedAnnealing(graph2,e1,e->e.weight());
		
//		m.search();
		Optional<TravelVertexInteger> vr = 
				Stream2.findLast(m.stream().peek(v->System.out.println(String.format("%d === %.2f",m.i,v.weight()))));
//		System.out.println(GraphPaths.of(graph,v.camino()).getWeight());
		System.out.println(m.bestWeight);
		System.out.println(m.bestVertex);
	}

}
