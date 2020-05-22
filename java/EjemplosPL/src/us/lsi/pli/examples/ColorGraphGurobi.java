package us.lsi.pli.examples;

import static us.lsi.gurobi.AuxiliaryGurobi.*;

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
public class ColorGraphGurobi {
	
	public static Graph<Integer, SimpleEdge<Integer>> graph;
	public static Integer n; //numero de vertices
	public static Integer m; //numero de colores maximo

	public static Graph<Integer, SimpleEdge<Integer>> graph(Integer n, Double pb) {
		Graph<Integer, SimpleEdge<Integer>> graph = Graphs2.simpleGraph();
		IntStream.range(0, n).forEach(v -> graph.addVertex(v));
		Streams2.allPairs(n, n).filter(p -> p.second > p.first).forEach(p -> {
			if (pb < Math2.getDoubleAleatorio(0., 1.)) {
				SimpleEdge<Integer> e = SimpleEdge.of(p.first, p.second);
				graph.addEdge(p.first, p.second, e);
			}
		});
		return graph;
	}
	
	public static String getConstraints() {
		Integer n = ColorGraphGurobi.n;
		Integer m = ColorGraphGurobi.m;
		StringBuilder r = new StringBuilder();
		r.append(goalMin(sum_1_v(m,"y")));
		r.append("\nSubject To\n\n");
		r.append(forAll_1(n,i->constraintEq(sum_2_2_v(m,i,"x"), " 1",i,"a")));
		r.append(forAll_2(n,m,(i,k)->constraintLe(
									String.format("%s - %s",varId_2("x",i,k),varId_1("y",k)),
									" 0",i*n+k,"b")));
		r.append(forAll_3(n,n,m,(i,j,k)->graph.containsEdge(i,k), 
							(i,j,k)->constraintLe(
									String.format("%s + %s",varId_2("x",i,k),varId_2("x",j,k)),
									" 1",i*n*m+j*m+k,"c")));
		r.append("\nBinary\n\n");
		r.append(vars_2(n,m,"x"));
		r.append(vars_1(m,"y"));
		return r.toString();
	}
	
	
	
	public static void main(String[] args) {
		ColorGraphGurobi.graph = graph(100,0.3);
		ColorGraphGurobi.n = graph.vertexSet().size();
		ColorGraphGurobi.m = 25;
		System.out.println(graph);
		String ct = ColorGraphGurobi.getConstraints();
		Files2.toFile(ct,"ficheros/color_sal.lp");
		Locale.setDefault(new Locale("en", "US"));
		GurobiSolution solution = GurobiLp.gurobi("ficheros/color_sal.lp");
		System.out.println(solution.toString((s,d)->d>0.));
	}

}
