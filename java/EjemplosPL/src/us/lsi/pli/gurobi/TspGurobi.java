package us.lsi.pli.gurobi;

import static us.lsi.pli.AuxiliaryPLI.*;

import java.util.Comparator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.Graph;

import us.lsi.common.Files2;
import us.lsi.flujosparalelos.Streams2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.SimpleEdge;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.math.Math2;

public class TspGurobi {
	
	public static Graph<Integer, SimpleEdge<Integer>> graph;
	public static Integer n; //numero de vertices
	public static Integer m; //numero de colores maximo

	public static Graph<Integer, SimpleEdge<Integer>> graph(Integer n, Double pb) {
		Locale.setDefault(new Locale("en", "US"));
		Graph<Integer, SimpleEdge<Integer>> graph = Graphs2.simpleWeightedGraph();
		IntStream.range(0, n).forEach(v -> graph.addVertex(v));
		Streams2.allPairs(n, n).filter(p -> p.second > p.first).forEach(p -> {
			if (Math2.getDoubleAleatorio(0., 1.) < pb) {
				Double w = Math2.getDoubleAleatorio(0., 100.);
				SimpleEdge<Integer> e1 = SimpleEdge.of(p.first, p.second,w);
				graph.addEdge(p.first, p.second, e1);
				graph.setEdgeWeight(e1,w);
			}
		});
		return graph;
	}
	
	public static String getConstraints() {
		Integer n = TspGurobi.n;
		Graph<Integer, SimpleEdge<Integer>> g = TspGurobi.graph;
		StringBuilder r = new StringBuilder();		
		r.append(min);
		r.append(goal(sum_2(n,n,"x",
				(i, j)-> j != i && g.containsEdge(i,j),
			    (i, j)->{var e = g.getEdge(i,j); return " "+g.getEdgeWeight(e);})));		
		r.append(constraintsSection);
		r.append(forAll_1(n,"a",
				j->constraintEq(
						sum_2_1_p(n,j,"x",(i,k) -> k != i && g.containsEdge(i,k)), 
						1)));
		r.append(forAll_1(n,"b",
				i->constraintEq(
						sum_2_2_p(n,i,"x",(k,j) -> j != i && g.containsEdge(i,j)), 
						1)));
		r.append(forAll_2(0,n,1,n,"c",
				(i, j)-> j != i && g.containsEdge(i,j),
				(i,j)->constraintVarIndicator(var_2("x",i,j),
				                             constraintGe(
				                            		 String.format("%s - %s",var_1("y",j),var_1("y",i)), 
				                            		 1))));
		r.append(constraint("d",constraintEq(var_1("y",0),0)));
		r.append(boundsSection);
		r.append(forAll_1_bound(n,
				(Function<Integer,String>) i->boundLe(var_1("y",i),(n-1))));
		r.append(binaryVars);
		r.append(vars_2_p(n,n,"x",(i, j)-> j != i && g.containsEdge(i,j)));
		r.append(intVars);
		r.append(vars_1(n,"y"));
		r.append(lastEnd);
		return r.toString();
	}
	
	public static String getConstraints2() {
		Integer n = TspGurobi.n;
		Graph<Integer, SimpleEdge<Integer>> g = TspGurobi.graph;
		StringBuilder r = new StringBuilder();		
		r.append(min);
		r.append(goal(sum_2(n,n,"x",
				(i, j)-> j != i && g.containsEdge(i,j),
			    (i, j)->{var e = g.getEdge(i,j); return " "+g.getEdgeWeight(e);})));		
		r.append(constraintsSection);
		r.append(forAll_1(n,"a",
				j->constraintEq(
						sum_2_1_p(n,j,"x",(i,k) -> k != i && g.containsEdge(i,k)), 
						1)));
		r.append(forAll_1(n,"b",
				i->constraintEq(
						sum_2_2_p(n,i,"x",(k,j) -> j != i && g.containsEdge(i,j)), 
						1)));
		r.append(forAll_2(0,n,1,n,"c",
				(i, j)-> j != i && g.containsEdge(i,j),
				(i,j)->constraintLe(String.format("%s - %s + %d %s",var_1("y",i),var_1("y",j),n,var_2("x",i,j)), 
				                            		 (n-1))));
		r.append(constraint("d",constraintEq(var_1("y",0),0)));
		r.append(boundsSection);
		r.append(forAll_1_bound(n,
				(Function<Integer,String>) i->boundLe(var_1("y",i),(n-1))));
		r.append(binaryVars);
		r.append(vars_2_p(n,n,"x",(i, j)-> j != i && g.containsEdge(i,j)));
		r.append(intVars);
		r.append(vars_1(n,"y"));
		r.append(lastEnd);
		return r.toString();
	}
	
	
	
	public static void main(String[] args) {
		TspGurobi.graph = graph(100,1.0);
		TspGurobi.n = TspGurobi.graph.vertexSet().size();
		System.out.println(graph);
		String ct = TspGurobi.getConstraints2();
		Files2.toFile(ct,"ficheros/tsp_sal.lp");
		Locale.setDefault(new Locale("en", "US"));
		GurobiSolution solution = GurobiLp.gurobi("ficheros/tsp_sal.lp");
		System.out.println(solution.toString((s,d)->s.startsWith("x") &&  d > 0.));
		System.out.println("_________________");
		System.out.println(solution.values.entrySet().stream()
				.filter(e->e.getKey().startsWith("y"))
				.sorted(Comparator.comparing(Entry::getValue))
				.map(Entry::getKey)
				.collect(Collectors.joining("\n")));
	}

}
