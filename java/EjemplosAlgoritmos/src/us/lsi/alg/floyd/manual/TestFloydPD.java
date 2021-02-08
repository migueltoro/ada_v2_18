package us.lsi.alg.floyd.manual;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.jgrapht.graph.GraphWalk;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.alg.floyd.FloydVertex;
import us.lsi.alg.floyd.TestFloyd;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.views.IntegerVertexGraphView;

public class TestFloydPD {

	public static void main(String[] args) {
Locale.setDefault(new Locale("en", "US"));
		
		SimpleWeightedGraph<Ciudad, Carretera> graph = TestFloyd.leeDatos("./ficheros/andalucia.txt");
		IntegerVertexGraphView<Ciudad, Carretera> graph2 = IntegerVertexGraphView.of(graph);
		
		System.out.println(graph);
		System.out.println(graph2);
		
		Integer origen = graph2.getIndex(Ciudad.ofName("Sevilla"));
		Integer destino = graph2.getIndex(Ciudad.ofName("Almeria"));
		
		FloydVertex initial = FloydVertex.of(graph2,origen,destino);
		
		FloydPD a = FloydPD.of(initial);
		
		GraphWalk<Integer, SimpleEdge<Integer>> tree = a.search();
		
		System.out.println(tree);
		List<Ciudad> lc = tree.getVertexList().stream()
				.map(i->graph2.getVertex(i)).collect(Collectors.toList());
		System.out.println(lc);
	}

}
