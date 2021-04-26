package us.lsi.pli.gurobi;



import java.io.IOException;
import java.util.Locale;
import java.util.stream.IntStream;

import org.jgrapht.Graph;

import us.lsi.flujosparalelos.Streams2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.SimpleEdge;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.math.Math2;
import us.lsi.solve.AuxGrammar;

public class TspGurobi {
	
	public static Graph<Integer, SimpleEdge<Integer>> graph;
	public static int n; //numero de vertices
 

	public static Graph<Integer, SimpleEdge<Integer>> graph(Integer n, Double pb) {
		Locale.setDefault(new Locale("en", "US"));
		Graph<Integer, SimpleEdge<Integer>> graph = Graphs2.simpleWeightedGraph();
		IntStream.range(0, n).forEach(v -> graph.addVertex(v));
		Streams2.allPairs(0,n, 0,n).filter(p -> p.second() > p.first()).forEach(p -> {
			if (Math2.getDoubleAleatorio(0., 1.) < pb) {
				Double w = Math2.getDoubleAleatorio(0., 100.);
				SimpleEdge<Integer> e1 = SimpleEdge.of(p.first(), p.second(),w);
				graph.addEdge(p.first(), p.second(), e1);
				graph.setEdgeWeight(e1,w);
			}
		});
		return graph;
	}
	
	public static Double getEdgeWeight(Integer i, Integer j, Graph<Integer, SimpleEdge<Integer>> g) {
		SimpleEdge<Integer> e = g.getEdge(i,j); 
		return g.getEdgeWeight(e);
	}
	
	public static Double getEdgeWeight(Integer i, Integer j) {
		SimpleEdge<Integer> e = TspGurobi.graph.getEdge(i,j); 
		return TspGurobi.graph.getEdgeWeight(e);
	}
	
	public static Boolean containsEdge(Integer i, Integer j) {
		return TspGurobi.graph.containsEdge(i,j);
	}
	
	public static Integer getN() {
		return TspGurobi.n;
	}
	
	
	public static void tsp_model_1() throws IOException {
		TspGurobi.graph = graph(200,0.6);
		TspGurobi.n = TspGurobi.graph.vertexSet().size();
		System.out.println(TspGurobi.graph);
		AuxGrammar.generate(TspGurobi.class,"models/tsp_1.lsi","ficheros/tsp_1.lp");
		GurobiSolution solution = GurobiLp.gurobi("ficheros/tsp_1.lp");
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	
	public static void main(String[] args) throws IOException {
		tsp_model_1();
	}

}
