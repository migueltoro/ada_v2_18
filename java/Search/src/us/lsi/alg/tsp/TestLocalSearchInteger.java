package us.lsi.alg.tsp;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.GraphPaths;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

public class TestLocalSearchInteger {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		
		Graph<Integer,SimpleEdge<Integer>> graph = AuxiliaryTsp.generate(50);
		System.out.println(graph);
		System.out.println(graph.edgeSet());
		List<Integer> camino = graph.vertexSet().stream().collect(Collectors.toList());
		camino.add(0);
//		System.out.println(graph.getEdgeWeight(graph.getEdge(0,1)));
		GraphPath<Integer,SimpleEdge<Integer>> path = GraphPaths.of(graph,camino);
		System.out.println(path.getWeight());
		TravelVertexInteger e1 = TravelVertexInteger.of(graph, camino);
		System.out.println(e1);
		
		EGraph<TravelVertexInteger,TravelEdgeInteger> graph2 = Graphs2.simpleVirtualGraphLast(e1,v->v.getWeight());
		
		GraphAlg<TravelVertexInteger,TravelEdgeInteger> ml = GraphAlg.local(graph2,e->e.getEdgeWeight()== 0.);
//		ml.stream().forEach(v->{System.out.println(GraphPaths.of(graph,v.camino).getWeight());
//		System.out.println(v.camino);});	
		TravelVertexInteger v = ml.findEnd().get();
		System.out.println(GraphPaths.of(graph,v.camino).getWeight());
		System.out.println(v.camino);
	}

}
