package us.lsi.alg.contenedores;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestAStar {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(new Locale("en", "US"));
		
		for (Integer i = 1; i < 2; i++) {

			DatosContenedores.iniDatos("ficheros/contenedores"+ i +".txt");
			DatosContenedores.toConsole();
			System.out.println("\n\n>\tResultados para el test " + i + "\n");

			// V�rtices clave

			VertexContenedores start = VertexContenedores.initial();
			Predicate<VertexContenedores> goal = VertexContenedores.goal();

			// Grafo
			
			//SimpleVirtualGraph.constraintG =  MulticonjuntoVertex.constraint();

			

			System.out.println("#### Algoritmo A* ####");

			// Algoritmo A*
			EGraph<VertexContenedores, EdgeContenedores> graph =
					SimpleVirtualGraph.last(start,goal,x -> (double)x.contenedoresCompletos().size());
			
			AStar<VertexContenedores, EdgeContenedores> aStar = AStar.of(graph,
					ContenedoresHeuristic::heuristic,AStarType.Max);
			
			Optional<GraphPath<VertexContenedores, EdgeContenedores>> gp = aStar.search();
			
			
			if (gp.isPresent()) {
				SolucionContenedores s_as = SolucionContenedores.of(gp.get());
				System.out.println(s_as);
			} else {
				System.out.println("Solucion no encontrada!!!!");
			}

			GraphColors.toDot(aStar.graph(), "ficheros/contenedoresAStarGraph.gv", 
					v -> v.toString(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, VertexContenedores.goal().test(v)),
					e -> GraphColors.colorIf(Color.red, gp.get().getEdgeList().contains(e)));
		}
	}

}
