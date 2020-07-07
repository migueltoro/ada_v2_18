package us.lsi.pli.gurobi;

import java.io.IOException;
import java.util.Locale;

import org.jgrapht.Graph;

import us.lsi.common.TriFunction;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.SimpleEdge;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Caminos {

	public static Graph<Integer,SimpleEdge<Integer>> graph = null;
	public static Integer n = null;
	
	public static Integer getN() {
		return n;
	}
	
	public static Double costes(Integer i, Integer j) {
			SimpleEdge<Integer> e =  graph.getEdge(i,j); 
			return graph.getEdgeWeight(e);
	}
	
	public static Double capacidad(Integer i, Integer j) {
		SimpleEdge<Integer> e =  graph.getEdge(i,j); 
		return graph.getEdgeWeight(e);
	}
	
	public static Double longitud(Integer i, Integer j) {
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
//				Graphs2::simpleDirectedWeightedGraph,
				Graphs2::simpleWeightedGraph,
				e->e.getEdgeWeight());
		return graph;
	}
	
	public static void caminos_model(String model) throws IOException {
		Caminos.graph = leeGrafo("data/ruta_tren.txt");
		Caminos.n = Caminos.graph.vertexSet().size();
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(graph);		
		AuxGrammar.generate(Caminos.class,model,"ficheros/disjuntas.lp");
		Locale.setDefault(new Locale("en", "US"));
		GurobiSolution solution = GurobiLp.gurobi("ficheros/disjuntas.lp");
		System.out.println(solution.toString((s,d)->s.contains("y") || d>0.));
	}
	
	public static void main(String[] args) throws IOException {
		caminos_model("models/shortest_path.lsi");
	}

}
