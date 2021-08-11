package us.lsi.flowgraph.examples;

import java.io.IOException;
import java.util.Locale;
import java.util.function.Function;

import org.jgrapht.Graph;

import us.lsi.common.TriFunction;
import us.lsi.graphs.GraphData;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.SimpleEdge;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Caminos {

	
	public static Function<String[],Double> edge = f ->(double)Integer.parseInt(f[2]);

	public static Graph<Integer,Double> leeGrafo(String fichero){
		Graph<Integer,Double> graph = GraphsReader.newGraph(fichero, 
				e->Integer.parseInt(e[0]), 
				edge,
				Graphs2::simpleDirectedWeightedGraph,
//				Graphs2::simpleWeightedGraph,
				e->e);
		return graph;
	}
	
	public static void caminos_model(String model) throws IOException {
		Graph<Integer,Double>  graph = leeGrafo("data/ruta_tren.txt");
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(graph);	
		GraphData.graph = graph;
		GraphData.n = graph.vertexSet().size();
		AuxGrammar.generate(GraphData.class,model,"ficheros/disjuntas.lp");
		Locale.setDefault(new Locale("en", "US"));
		GurobiSolution solution = GurobiLp.gurobi("ficheros/disjuntas.lp");
		System.out.println(solution.toString((s,d)->s.contains("y") || d>0.));
	}
	
	public static void main(String[] args) throws IOException {
//		caminos_model("models/caminos_aristas_y_vertices_disjuntos.lsi");
//		caminos_model("models/caminos_aristas_disjuntas.lsi");
		caminos_model("models/ruta_tren.lsi");
	}

}
