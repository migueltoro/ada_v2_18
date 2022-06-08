package us.lsi.alg.tsp;

import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.alg.LocalSearch;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.streams.Stream2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.jgrapht.Graph;

public class TestLocalSearchInteger {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		
		Graph<Integer,SimpleEdge<Integer>> graph = AuxiliaryTsp.generate(100);
		TravelVertexInteger.graph = graph;
		TravelVertexInteger.n = 10;
		List<Integer> camino = new ArrayList<>(graph.vertexSet().stream().toList());
		
		camino.add(camino.get(0));
		
		Collections.shuffle(camino.subList(1,camino.size()-2));
		
		TravelVertexInteger e1 = TravelVertexInteger.of(camino);
		
		EGraph<TravelVertexInteger,TravelEdgeInteger> graph2 = 
				EGraph.virtual(e1,null).vertexWeight(v->v.weight()).build();
		
		
		Double bb = 1000000000.;
		int i = 0;
		while (i< 1) {
			System.out.println("------------------ "+i);
			Collections.shuffle(camino.subList(1, camino.size() - 2));
			e1 = TravelVertexInteger.of(camino);
			graph2 = EGraph.virtual(e1,null).vertexWeight(v->v.weight()).build();
			LocalSearch<TravelVertexInteger,TravelEdgeInteger> m = LocalSearch.of(graph2, 0.1, 3);
			Optional<TravelVertexInteger> vr = Stream2.findLast(m.stream().peek(v->System.out.println(v.weight())));
			//		System.out.println(GraphPaths.of(graph,v.camino()).getWeight());
			if(vr.get().weight() < bb) bb = vr.get().weight();
			System.out.println(vr.get());
			i++;
		}
		System.out.println(bb);
	}

}
