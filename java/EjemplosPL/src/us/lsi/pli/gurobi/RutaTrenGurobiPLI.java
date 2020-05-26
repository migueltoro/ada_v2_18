package us.lsi.pli.gurobi;

import static us.lsi.pli.AuxiliaryPLI.*;

import java.io.PrintWriter;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.IntegerComponentNameProvider;

import us.lsi.common.Files2;
import us.lsi.common.TriFunction;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.SimpleEdge;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;

public class RutaTrenGurobiPLI {
	
	public static Graph<Integer,SimpleEdge<Integer>> graph = null;
	public static Integer n = null;
	public static BiPredicate<Integer,Integer> pd = (i,j)->graph.containsEdge(i,j);
	public static BiFunction<Integer,Integer,String> costes = 
			(i,j)->{var e = graph.getEdge(i,j); return String.format("%.2f",graph.getEdgeWeight(e));};
	
	public static TriFunction<Integer,Integer,String[],SimpleEdge<Integer>> edge = 
			(v1,v2,f) -> SimpleEdge.of(v1,v2,(double)Integer.parseInt(f[2]));

	public static Graph<Integer,SimpleEdge<Integer>> leeGrafo(String fichero){
		Graph<Integer,SimpleEdge<Integer>> graph = GraphsReader.newGraph(fichero, 
				e->Integer.parseInt(e[0]), 
				edge,
				Graphs2::simpleDirectedWeightedGraph,
				e->e.getEdgeWeight());
		return graph;
	}
	
	public static String getContraints() {
		Integer n = RutaTrenGurobiPLI.n;
		StringBuilder r = new StringBuilder();
		r.append(min);
		r.append(goal(sum_2(n,n,"x",pd,costes)));
		r.append(constraintsSection);
		r.append(constraint("a",constraintEq(sum_2_2_p(n,0,"x",pd)," 1")));
		r.append(constraint("b",constraintEq(sum_2_1_p(n,n-1,"x",pd)," 1")));
		r.append(forAll_1_r(1,n-1,"b",
				i->constraintEq(
						sum(sum_2_1_p(n,i,"x",pd),sum_2_2(n,i,"x",pd,(k,j)->"-1")),
						" 0")));
		r.append(binaryVars);
		r.append(vars_2_p(n,n,"x",pd));
		r.append(lastEnd);
		return r.toString();
	}
	
	public static GraphPath<Integer, SimpleEdge<Integer>> shortestPath() {
		DijkstraShortestPath<Integer,SimpleEdge<Integer>> a = new DijkstraShortestPath<>(graph);
		GraphPath<Integer,SimpleEdge<Integer>> gp = a.getPath(0,n-1);
		return gp;
	}
	
	public static void showGraph() {
		DOTExporter<Integer,SimpleEdge<Integer>> de1 = new DOTExporter<Integer,SimpleEdge<Integer>>(
				new IntegerComponentNameProvider<>(),
				x->x.toString(), 
				x->Double.valueOf(x.getEdgeWeight()).toString());
		PrintWriter f1 = Files2.getWriter("ficheros/ruta_tren.gv");
		de1.exportGraph(graph, f1);
	}
	
	public static GurobiSolution gurobi() {
		String ct = RutaTrenGurobiPLI.getContraints();
		Files2.toFile(ct, "ficheros/rutas_tren_sal.lp");
		Locale.setDefault(new Locale("en", "US"));
		GurobiSolution solution = GurobiLp.gurobi("ficheros/rutas_tren_sal.lp");
		return solution;
	}
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		RutaTrenGurobiPLI.graph = leeGrafo("ficheros/ruta_tren.txt");
		RutaTrenGurobiPLI.n = RutaTrenGurobiPLI.graph.vertexSet().size();
		System.out.println(graph);	
		GurobiSolution sg = gurobi();
		System.out.println(sg.toString(((x,y)->y>0.)));
		GraphPath<Integer, SimpleEdge<Integer>> gp = shortestPath();
		System.out.println(gp.getVertexList());
	}

}
