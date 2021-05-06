package us.lsi.alg.multiconjuntos;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.EGraph;

public class Test_aS {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 0; id_fichero < 1; id_fichero++) {

			DatosMulticonjunto.iniDatos("ficheros/multiconjuntos.txt", id_fichero);
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");

			// Vértices clave

			MulticonjuntoVertex start = MulticonjuntoVertex.initial();
			Predicate<MulticonjuntoVertex> finalVertex = v -> MulticonjuntoVertex.goal(v);

			// Grafo

			EGraph<MulticonjuntoVertex, MulticonjuntoEdge> graph;

			System.out.println("#### Algoritmo A* ####");

			// Algoritmo A*
			graph = Graphs2.simpleVirtualGraph(start, x -> x.weight());
			AStar<MulticonjuntoVertex, MulticonjuntoEdge> aStar = GraphAlg.aStar(graph, finalVertex, null,
					MulticonjuntoHeuristic::heuristic);

			aStar.withGraph = true;
			
			GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> gp = aStar.search().get();
			
			List<Integer> gp_as = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList()); // getEdgeList();
			
			
			SolucionMulticonjunto s_as = SolucionMulticonjunto.create(gp_as);
			
			
			System.out.println(s_as);
			System.out.println(gp_as);

			Graphs2.toDot(aStar.outGraph, "ficheros/multiconjuntosAStarGraph.gv", 
					v -> v.toGraph(),
					e -> e.action().toString(), 
					v -> GraphColors.getColorIf(Color.red, MulticonjuntoVertex.goal(v)),
					e -> GraphColors.getColorIf(Color.red, gp.getEdgeList().contains(e)));
		}
	}

}
