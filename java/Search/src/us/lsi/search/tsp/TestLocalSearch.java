package us.lsi.search.tsp;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm.SpanningTree;

import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.DephtSearch;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.views.SubGraphView;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.GraphPaths;

public class TestLocalSearch {
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		
		Graph<Ciudad,Carretera> graph1 = AuxiliaryTsp.leeGraph("ficheros/andalucia.txt");
		Graph<Ciudad,Carretera> graph2 = AuxiliaryTsp.completeGraph(graph1);
		SpanningTree<Carretera> tree = AuxiliaryTsp.spanningTree(graph2);
		Graph<Ciudad,Carretera> graph3 = SubGraphView.of(graph2,
				v->graph1.vertexSet().contains(v),
				e->tree.getEdges().contains(e));
//		System.out.println(graph3);
		
		DephtSearch<Ciudad,Carretera> ms = GraphAlg.depth(graph3,Ciudad.ofName("Sevilla"));
		//lista de vertices recorridos en preorden
		List<Ciudad> camino = ms.stream().collect(Collectors.toList());
		camino.add(Ciudad.ofName("Sevilla"));
//		System.out.println(camino);	
//		GraphPath<Ciudad,Carretera> path = GraphPaths.of(graph2,camino);
		
		TravelVertex e1 = TravelVertex.of(graph2, camino);
		System.out.println(e1);
		EGraph<TravelVertex,TravelEdge> graph = Graphs2.simpleVirtualGraphLast(e1,v->v.weight);
		
		GraphAlg<TravelVertex,TravelEdge> ml = GraphAlg.local(graph,e->e.getEdgeWeight()== 0.);
		ml.stream().forEach(v->{System.out.println(GraphPaths.of(graph2,v.camino).getEdgeList());
		System.out.println(v.camino);});	
	}

}
