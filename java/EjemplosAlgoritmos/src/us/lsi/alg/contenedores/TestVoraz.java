package us.lsi.alg.contenedores;

import java.util.Locale;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestVoraz {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 1; id_fichero < 2; id_fichero++) {

			DatosContenedores.iniDatos("ficheros/contenedores"+ id_fichero +".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");	

			// Vértices clave

			VertexContenedores start = VertexContenedores.initial();
			Predicate<VertexContenedores> goal = VertexContenedores.goal();

			// Grafo

			EGraph<VertexContenedores, EdgeContenedores> graph =
					SimpleVirtualGraph.last(start,goal,x -> (double)x.contenedoresCompletos().size());

			System.out.println("\n\n#### PI-7 Ej3 Algoritmo BT ####");
			
			GraphPath<VertexContenedores, EdgeContenedores> max = ContenedoresHeuristic.caminoVoraz(graph,50);
			GraphPath<VertexContenedores, EdgeContenedores> max2 = ContenedoresHeuristic.caminoVoraz(graph,50);
			Double hu = ContenedoresHeuristic.heuristic(start,goal, null);
			System.out.println(String.format("%.2f,%.2f,%.2f", max.getWeight(),max2.getWeight(),hu));
		}

	}

}
