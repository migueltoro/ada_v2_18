package us.lsi.alg.contenedores;

import java.util.Locale;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestBT {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 1; id_fichero < 2; id_fichero++) {

			DatosContenedores.iniDatos("ficheros/contenedores"+ id_fichero +".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
//			DatosSubconjuntos.toConsole();
			

			// Vértices clave

			VertexContenedores start = VertexContenedores.initial();
			Predicate<VertexContenedores> goal = VertexContenedores.goal();

			// Grafo

			EGraph<VertexContenedores, EdgeContenedores> graph =
					SimpleVirtualGraph.last(start,goal,x -> (double)x.contenedoresCompletos().size());

			System.out.println("\n\n#### PI-7 Ej3 Algoritmo BT ####");
			
			GraphPath<VertexContenedores, EdgeContenedores> max = ContenedoresHeuristic.caminoVoraz(graph,50);
			GraphPath<VertexContenedores, EdgeContenedores> max2 = ContenedoresHeuristic.caminoVoraz(graph,50);
			
			System.out.println("Solucion Voraz ="+max.getWeight());
			System.out.println("Solucion Voraz 2 ="+max2.getWeight());
			System.out.println("Heuristica ="+ContenedoresHeuristic.heuristic(start, goal, null));
			// Algoritmo BT
			BackTracking<VertexContenedores, EdgeContenedores, SolucionContenedores> bta = 
				BackTracking.of(graph, 
					ContenedoresHeuristic::heuristic,
					SolucionContenedores::of, 
					BTType.Max);
			
			bta.bestValue = max.getWeight();
			bta.optimalPath = max;

			bta.search();
			
			System.out.println(bta.getSolution());
		}
	}

}

