package us.lsi.pli.gurobi;

import static us.lsi.pli.AuxiliaryPLI.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

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
import us.lsi.pli.AuxiliaryPLI;
import us.lsi.solve.AuxGrammar;


public class RutaTrenGurobiPLI {
	
	public static Graph<Integer,SimpleEdge<Integer>> graph = null;
	public static Integer n = null;
	
	public static Integer getN() {
		return n;
	}
	
	public static Double costes(Integer i, Integer j) {
			SimpleEdge<Integer> e =  graph.getEdge(i,j); 
			return graph.getEdgeWeight(e);
	}
	
	public static Boolean containsEdge(Integer i, Integer j) {
		return graph.containsEdge(i,j); 
	}
	
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
	
	public static String getContraints(PLIType type) {
		AuxiliaryPLI.setType(type);
		Locale.setDefault(new Locale("en", "US"));
		Integer n = RutaTrenGurobiPLI.n;
		StringBuilder r = new StringBuilder();
		r.append(goalMinSection(sum(listOf(0,n,0,n,(i,j)->graph.containsEdge(i,j),(i,j)->f(costes(i,j),"x",i,j)))));
		r.append(constraintsSection());
		r.append(forAll("a",constraintEq(sum(listOf(0,n,j->graph.containsEdge(0,j),j->v("x",0,j))),1)));
		r.append(forAll("b",constraintEq(sum(listOf(0,n,i->graph.containsEdge(i,n-1),i->v("x",i,n-1))),1)));
		r.append(forAll("c",listOf(1,n-1,
				i->constraintEq(sum(
						sum(listOf(0,n,j->graph.containsEdge(j,i),j->v("x",j,i))),
						sum(listOf(0,n,j->graph.containsEdge(i,j),j->f(-1,"x",i,j)))),
					0))));
		r.append(binaryVarsSection(listOf(0,n,0,n,(i,j)->graph.containsEdge(i,j),(i,j)->v("x",i,j))));
		r.append(endSection());
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
		String ct = RutaTrenGurobiPLI.getContraints(PLIType.Gurobi);
		Files2.toFile(ct, "ficheros/rutas_tren_sal.lp");
		Locale.setDefault(new Locale("en", "US"));
		GurobiSolution solution = GurobiLp.gurobi("ficheros/rutas_tren_sal.lp");
		return solution;
	}
	
	public static void tren_constraints() {
		Locale.setDefault(new Locale("en", "US"));
		RutaTrenGurobiPLI.graph = leeGrafo("ficheros/ruta_tren.txt");
		RutaTrenGurobiPLI.n = RutaTrenGurobiPLI.graph.vertexSet().size();
		showGraph();	
		GurobiSolution sg = gurobi();
		System.out.println(sg.toString(((x,y)->y>0.)));
		GraphPath<Integer, SimpleEdge<Integer>> gp = shortestPath();
		System.out.println(gp.getVertexList());
		System.out.println(gp.getWeight());
	}
	
	public static void tren_model() throws IOException {
		RutaTrenGurobiPLI.graph = leeGrafo("data/ruta_tren.txt");
		RutaTrenGurobiPLI.n = RutaTrenGurobiPLI.graph.vertexSet().size();
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(graph);	
		showGraph();		
		AuxGrammar.generate(RutaTrenGurobiPLI.class,"models/ruta_tren.lsi","ficheros/ruta_tren.lp");
		Locale.setDefault(new Locale("en", "US"));
		GurobiSolution solution = GurobiLp.gurobi("ficheros/ruta_tren.lp");
		System.out.println(solution.toString((s,d)->d>0.));
		GraphPath<Integer, SimpleEdge<Integer>> gp = shortestPath();
		System.out.println(gp.getVertexList());
		System.out.println(gp.getWeight());
	}
	
	public static void main(String[] args) throws IOException {
		tren_model();
	}

}
