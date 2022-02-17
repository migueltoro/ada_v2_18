package us.lsi.alg.multiconjuntos;

import java.util.Locale;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.alg.monedas.MonedaEdge;
import us.lsi.alg.monedas.MonedaVertex;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class Test_BT {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 0; id_fichero < 7; id_fichero++) {

			DatosMulticonjunto.iniDatos("ficheros/multiconjuntos.txt", id_fichero);
			System.out.println("=============");
			System.out.println("\tResultados para el test " + id_fichero + "\n");
			
			DatosMulticonjunto.toConsole();

			// Vértices clave

			MulticonjuntoVertex start = MulticonjuntoVertex.initial();
			Predicate<MulticonjuntoVertex> goal = MulticonjuntoVertex.goal();

			// Grafo

			

			System.out.println("\n#### Algoritmo BT ####");
			
			// Algoritmo BT
			
			EGraph<MulticonjuntoVertex, MulticonjuntoEdge> graph = 
					SimpleVirtualGraph.sum(start, goal,x -> x.weight(), MulticonjuntoVertex.constraint());
			
			GreedyOnGraph<MulticonjuntoVertex, MulticonjuntoEdge> rr = GreedyOnGraph.of(graph,MulticonjuntoVertex::greedyEdge);
			
			GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> r = rr.path();
			
			System.out.println("Voraz = "+r.getWeight()+"  == "+MulticonjuntoVertex.getSolucion(r));
			
			BackTracking<MulticonjuntoVertex, MulticonjuntoEdge,SolucionMulticonjunto> bta = 
					BackTracking.of(graph, 
							MulticonjuntoHeuristic::heuristic,
							MulticonjuntoVertex::getSolucion, 
							BTType.Min);

			if (rr.isSolution(r)) {
				bta.bestValue = r.getWeight();
				bta.optimalPath = r;
				bta.withGraph = true;
			}
			bta.search();
			System.out.println(bta.getSolution().get());
			
//			System.out.println(bta.path.getEdgeList().stream().map(x -> x.action())
//					.collect(Collectors.toList()));
			
			
//			GraphColors.toDot(bta.graph(), "ficheros/multiconjuntosBTGraph.gv", 
//					v -> v.toGraph(),
//					e -> e.action().toString(), 
//					v -> GraphColors.colorIf(Color.red, MulticonjuntoVertex.goal().test(v)),
//					e -> GraphColors.colorIf(Color.red, bta.optimalPath.getEdgeList().contains(e)));

		}
	}

}

