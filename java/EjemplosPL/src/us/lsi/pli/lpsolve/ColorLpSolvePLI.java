package us.lsi.pli.lpsolve;

import static us.lsi.pli.AuxiliaryPLI.*;

import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.Graph;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.SimpleEdge;
import us.lsi.lpsolve.AlgoritmoLpSolve;
import us.lsi.lpsolve.SolutionLpSolve;
import us.lsi.math.Math2;
import us.lsi.pli.AuxiliaryPLI;
import us.lsi.common.Files2;
import us.lsi.flujosparalelos.Streams2;

public class ColorLpSolvePLI {

	
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
		Integer n = ColorLpSolvePLI.n;
		Integer m = ColorLpSolvePLI.m;
		StringBuilder r = new StringBuilder();
		r.append(min);
		r.append(goal(sum_1_v(m,"y")));
		r.append(constraintsSection);
		r.append(forAll_1(n,"a",i->constraintEq(sum_2_2_v(m,i,"x"), " 1")));
		r.append(forAll_2(n,m,"b",(i,k)->constraintLe(
									sum(var_2("x",i,k),factor_1("y",k,v->"-1")),
									" 0")));
		r.append(forAll_3(n,n,m,"c",(i,j,k)->graph.containsEdge(i,k), 
							(i,j,k)->constraintLe(
									sum(var_2("x",i,k),var_2("x",j,k)),
									" 1")));
		r.append(binaryVars);
		r.append(vars_2(n,m,"x"));
		r.append(binaryVars);
		r.append(vars_1(m,"y"));
		r.append(lastEnd);
		return r.toString();
	}
	
	public static void main(String[] args) {
		AuxiliaryPLI.setType(AuxiliaryPLI.TipoPLI.LPSolve);;
		ColorLpSolvePLI.graph = graph(20,0.3);
		ColorLpSolvePLI.n = graph.vertexSet().size();
		ColorLpSolvePLI.m = 10;
		System.out.println(graph);
		String ct = ColorLpSolvePLI.getConstraints();
		Files2.toFile(ct,"ficheros/color_2_sal.lp");
		Locale.setDefault(new Locale("en", "US"));
		SolutionLpSolve s = AlgoritmoLpSolve.getSolutionFromFile("ficheros/color_2_sal.lp");
		System.out.println("-------------------");
		System.out.println("________");
		System.out.println(s.getGoal());
		System.out.println("________");
		System.out.println("________");
		for (int i = 0; i < s.getNumVar(); i++) {
			if (s.getSolution(i) == 1.0)
				System.out.println(s.getName(i));
		}

	}

	static String vecinosConColoresDistintos(Integer ids, Integer idt, int n) {
		return IntStream.range(0, n).boxed().map(k -> String.format("x_%d_%d + x_%d_%d<=1;\n", ids, k, idt, k))
				.collect(Collectors.joining("", "", "\n"));
	}

}
