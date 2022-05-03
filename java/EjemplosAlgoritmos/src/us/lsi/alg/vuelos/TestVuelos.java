package us.lsi.alg.vuelos;


import java.util.Locale;

import java.util.stream.Collectors;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraphI;
import us.lsi.path.EGraphPath.PathType;

public class TestVuelos {
	
	public static DirectedWeightedMultigraph<String,Vuelo> leeGrafo(String fichero) {
		DirectedWeightedMultigraph<String,Vuelo> graph = GraphsReader.newGraph(fichero, 
				s->s[0], 
			    Vuelo::ofFormat,
				Graphs2::directedWeightedMultigraph, 
				Vuelo::getDuracion);
		return graph;
	}
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DirectedWeightedMultigraph<String,Vuelo> graph = leeGrafo("ficheros/vuelos.txt");
		System.out.println(graph);
		String end = "Malaga";
		
		EGraph<String, Vuelo> g = EGraphI.of(
				graph,
				"Sevilla",
				v->v.equals(end),
				end,
				v->true,
				PathType.Sum,
				Vuelo::getDuracion,
				null,
				(v,e1,e2)-> Vuelo.getVertexPassWeight(v,e1,e2));	
		
		AStar<String,Vuelo> ms = AStar.of(g,(v1,p,v2)->0.,AStarType.Min);
		
		GraphPath<String,Vuelo> path = ms.search().orElse(null);
		System.out.printf("Timepo de Recorrido = %.2f\n",path.getWeight());
		System.out.println(path.getVertexList().stream().map(e->e.toString()).collect(Collectors.joining("\n")));	
		System.out.println(path.getEdgeList().stream().map(e->e.toString()).collect(Collectors.joining("\n")));
		System.out.println(path);
	}	

}
