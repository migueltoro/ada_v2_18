package us.lsi.pli.gurobi;


import java.io.IOException;
import java.util.stream.IntStream;

import org.jgrapht.Graph;

import us.lsi.flujosparalelos.Streams2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.SimpleEdge;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.math.Math2;
import us.lsi.solve.AuxGrammar;
public class ColorGraphGurobi {
	
	public static Graph<Integer, SimpleEdge<Integer>> graph;
	public static int n; //numero de vertices
	public static int m; //numero de colores maximo

	public static Graph<Integer, SimpleEdge<Integer>> graph(Integer n, Double pb) {
		Graph<Integer, SimpleEdge<Integer>> graph = Graphs2.simpleGraph();
		IntStream.range(0, n).forEach(v -> graph.addVertex(v));
		Streams2.allPairs(0,n, 0,n).filter(p -> p.second > p.first).forEach(p -> {
			if (pb < Math2.getDoubleAleatorio(0., 1.)) {
				SimpleEdge<Integer> e = SimpleEdge.of(p.first, p.second, 1.);
				graph.addEdge(p.first, p.second, e);
			}
		});
		return graph;
	}
	
	public static Integer getN() {
		return ColorGraphGurobi.n;
	}
	
	public static Integer getM() {
		return ColorGraphGurobi.m;
	}
	
	public static Boolean containsEdge(Integer i, Integer j) {
		return ColorGraphGurobi.graph.containsEdge(i,j);
	}
	
	
	public static void color_model() throws IOException {
		ColorGraphGurobi.graph = graph(30,0.3);
		ColorGraphGurobi.n = graph.vertexSet().size();
		ColorGraphGurobi.m = 10;
		System.out.println(graph);
		AuxGrammar.generate(ColorGraphGurobi.class,"models/color.lsi","ficheros/color.lp");
		GurobiSolution solution = GurobiLp.gurobi("ficheros/color.lp");
		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	public static void main(String[] args) throws IOException {
		color_model();
	}

}
