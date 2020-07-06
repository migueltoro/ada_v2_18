package us.lsi.pli.gurobi;

import static us.lsi.pli.AuxiliaryPLI.*;

import java.io.IOException;
import java.util.Locale;
import java.util.stream.IntStream;

import org.jgrapht.Graph;

import us.lsi.common.Files2;
import us.lsi.flujosparalelos.Streams2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.SimpleEdge;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.math.Math2;
import us.lsi.pli.AuxiliaryPLI;
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
				SimpleEdge<Integer> e = SimpleEdge.of(p.first, p.second);
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
	
	public static String getConstraints(PLIType type) {
		AuxiliaryPLI.setType(type);
		Integer n = ColorGraphGurobi.n;
		Integer m = ColorGraphGurobi.m;
		StringBuilder r = new StringBuilder();
		r.append(goalMinSection(sum(listOf(0,m,k->v("y",k)))));
		r.append(constraintsSection());
		r.append(forAll("a",listOf(0,n,i->constraintEq(sum(listOf(0,m,k->v("x",i,k))),1))));
		r.append(forAll("b",listOf(0,n,0,m,(i,k)->constraintLe(sum(v("x",i,k),f(-1,"y",k)),0))));
		r.append(forAll("c",listOf(0,n,0,n,0,m,(i,j,k)->graph.containsEdge(i,k), 
							(i,j,k)->constraintLe(sum(v("x",i,k),v("x",j,k)),1))));
		r.append(binaryVarsSection(listOf(0,n,0,m,(i,k)->v("x",i,k)),listOf(0,m,k->v("y",k))));
		r.append(endSection());
		return r.toString();
	}
	
	
	public static void color_constraint() {
		ColorGraphGurobi.graph = graph(50,0.3);
		ColorGraphGurobi.n = graph.vertexSet().size();
		ColorGraphGurobi.m = 25;
		System.out.println(graph);
		String ct = ColorGraphGurobi.getConstraints(PLIType.Gurobi);
		Files2.toFile(ct,"ficheros/color_sal.lp");
		Locale.setDefault(new Locale("en", "US"));
		GurobiSolution solution = GurobiLp.gurobi("ficheros/color_sal.lp");
		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	
	public static void color_model() throws IOException {
		ColorGraphGurobi.graph = graph(50,0.3);
		ColorGraphGurobi.n = graph.vertexSet().size();
		ColorGraphGurobi.m = 25;
		System.out.println(graph);
		AuxGrammar.generate(ColorGraphGurobi.class,"models/color.lsi","ficheros/color.lp");
		GurobiSolution solution = GurobiLp.gurobi("ficheros/color.lp");
		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	public static void main(String[] args) throws IOException {
		color_model();
	}

}
