package us.lsi.alg.multiconjuntos;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class Test_aS {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 0; id_fichero < 7; id_fichero++) {

			DatosMulticonjunto.iniDatos("ficheros/multiconjuntos.txt", id_fichero);
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");

			// Vértices clave

			MulticonjuntoVertex start = MulticonjuntoVertex.initial();
			Predicate<MulticonjuntoVertex> goal = MulticonjuntoVertex.goal();

			// Grafo

			EGraph<MulticonjuntoVertex, MulticonjuntoEdge> graph;

			System.out.println("#### Algoritmo A* ####");

			// Algoritmo A*
			graph = SimpleVirtualGraph.sum(start,goal,x -> x.weight());
			AStar<MulticonjuntoVertex, MulticonjuntoEdge> aStar = AStar.of(graph,
					MulticonjuntoHeuristic::heuristic,AStarType.Min);
			
			GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> gp = aStar.search().get();
			
			List<Integer> gp_as = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList()); // getEdgeList();
			
			
			SolucionMulticonjunto s_as = SolucionMulticonjunto.create(gp_as);
			
			
			System.out.println(s_as);
			System.out.println(gp_as);

//			GraphColors.toDot(aStar.outGraph, "ficheros/multiconjuntosAStarGraph.gv", 
//					v -> v.toGraph(),
//					e -> e.action().toString(), 
//					v -> GraphColors.colorIf(Color.red, MulticonjuntoVertex.goal().test(v)),
//					e -> GraphColors.colorIf(Color.red, gp.getEdgeList().contains(e)));
		}
	}

}
