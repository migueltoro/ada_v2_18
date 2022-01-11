package us.lsi.alg.multiconjuntos;

import java.util.Locale;
import java.util.function.Predicate;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class Test_BT {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 0; id_fichero < 1; id_fichero++) {

			DatosMulticonjunto.iniDatos("ficheros/multiconjuntos.txt", id_fichero);
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");

			// Vértices clave

			MulticonjuntoVertex start = MulticonjuntoVertex.initial();
			Predicate<MulticonjuntoVertex> goal = MulticonjuntoVertex.goal();

			// Grafo

			EGraph<MulticonjuntoVertex, MulticonjuntoEdge> graph;

			System.out.println("\n\n#### Algoritmo BT ####");
			
			// Algoritmo BT
			graph = SimpleVirtualGraph.sum(start, goal,x -> x.weight());
			BackTracking<MulticonjuntoVertex, MulticonjuntoEdge,SolucionMulticonjunto> bta = 
					BackTracking.of(graph, 
							MulticonjuntoHeuristic::heuristic,
							MulticonjuntoVertex::getSolucion, 
							BTType.Min);

			bta.bestValue = (double)MulticonjuntoHeuristic.valEntero(start,DatosMulticonjunto.NUM_E);
			bta.solutions.add(MulticonjuntoHeuristic.sol(start,DatosMulticonjunto.NUM_E));
			bta.withGraph = true;
			
			bta.search();
			System.out.println(bta.getSolution().get());
			
//			System.out.println(bta.path.getEdgeList().stream().map(x -> x.action())
//					.collect(Collectors.toList()));
			
			
			GraphColors.toDot(bta.graph(), "ficheros/multiconjuntosBTGraph.gv", 
					v -> v.toGraph(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, MulticonjuntoVertex.goal().test(v)),
					e -> GraphColors.colorIf(Color.red, bta.optimalPath.getEdgeList().contains(e)));

		}
	}

}

