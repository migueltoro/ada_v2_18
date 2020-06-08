package us.lsi.pli.gurobi;

import static us.lsi.pli.AuxiliaryPLI.*;

import java.util.Comparator;
import java.util.Locale;
import java.util.Map.Entry;
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
	public static int n; //numero de vertices
	public static int m; //numero de colores maximo

	public static Graph<Integer, SimpleEdge<Integer>> graph(Integer n, Double pb) {
		Locale.setDefault(new Locale("en", "US"));
		Graph<Integer, SimpleEdge<Integer>> graph = Graphs2.simpleWeightedGraph();
		IntStream.range(0, n).forEach(v -> graph.addVertex(v));
		Streams2.allPairs(0,n, 0,n).filter(p -> p.second > p.first).forEach(p -> {
			if (Math2.getDoubleAleatorio(0., 1.) < pb) {
				Double w = Math2.getDoubleAleatorio(0., 100.);
				SimpleEdge<Integer> e1 = SimpleEdge.of(p.first, p.second,w);
				graph.addEdge(p.first, p.second, e1);
				graph.setEdgeWeight(e1,w);
			}
		});
		return graph;
	}
	
	public static Double getEdgeWeight(Integer i, Integer j, Graph<Integer, SimpleEdge<Integer>> g) {
		SimpleEdge<Integer> e = g.getEdge(i,j); 
		return g.getEdgeWeight(e);
	}
	
	public static String getConstraints() {
		Integer n = TspGurobi.n;
		Graph<Integer, SimpleEdge<Integer>> g = TspGurobi.graph;
		StringBuilder r = new StringBuilder();		
		r.append(goalMinSection(sum(listOf(0,n,0,n,
				(i, j)-> j != i && g.containsEdge(i,j),
			    (i, j)->f(getEdgeWeight(i,j,g),"x",i,j)))));		
		r.append(constraintsSection());
		r.append(forAll("a",listOf(0,n,
				j->constraintEq(
						sum(listOf(0,n,i -> j != i && g.containsEdge(i,j),i->v("x",i,j))), 
						1))));
		r.append(forAll("b",listOf(0,n,
				i->constraintEq(
						sum(listOf(0,n,j -> i != j && g.containsEdge(i,j),j->v("x",i,j))), 
						1))));
		r.append(forAll("c",listOf(0,n,1,n,
				(i, j)-> j != i && g.containsEdge(i,j),
				(i,j)->constraintIndicator(v("x",i,j),
				                             constraintGe(
				                            		 String.format("%s - %s",v("y",j),v("y",i)), 
				                            		 1)))));
		r.append(forAll("d",constraintEq(v("y",0),0)));
		r.append(boundsSection(listOf(0,n,i->constraintLe(v("y",i),(n-1)))));
		r.append(binaryVarsSection(listOf(0,n,0,n,(i, j)-> j != i && g.containsEdge(i,j),(i,j)->v("x",i,j))));
		r.append(intVarsSection(listOf(0,n,i->v("y",i))));
		r.append(endSection());
		return r.toString();
	}
	
	public static String getConstraints2() {
		Integer n = TspGurobi.n;
		Graph<Integer, SimpleEdge<Integer>> g = TspGurobi.graph;
		StringBuilder r = new StringBuilder();		
		r.append(goalMinSection(sum(listOf(0,n,0,n,
				(i, j)-> j != i && g.containsEdge(i,j),
			    (i, j)-> f(getEdgeWeight(i,j,g),"x",i,j)))));		
		r.append(constraintsSection());
		r.append(forAll("a",listOf(0,n,
				j->constraintEq(
						sum(listOf(0,n,i -> j != i && g.containsEdge(i,j), i->v("x",i,j))),
						1))));
		r.append(forAll("b",listOf(0,n,
				i->constraintEq(
						sum(listOf(0,n,j -> j != i && g.containsEdge(i,j),j->v("x",i,j))), 
						1))));
		r.append(forAll("c",listOf(0,n,1,n,
				(i, j)-> j != i && g.containsEdge(i,j),
				(i,j)->constraintLe(
						sum(v("y",i),f(-1,"y",j),f(n,"x",i,j)), 
				        (n-1)))));
		r.append(forAll("d",constraintEq(v("y",0),0)));
		r.append(boundsSection(listOf(0,n,i->constraintLe(v("y",i),(n-1)))));
		r.append(binaryVarsSection(listOf(0,n,0,n,(i, j)-> j != i && g.containsEdge(i,j),(i,j)->v("x",i,j))));
		r.append(intVarsSection(listOf(0,n,i->v("y",i))));
		r.append(endSection());
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
