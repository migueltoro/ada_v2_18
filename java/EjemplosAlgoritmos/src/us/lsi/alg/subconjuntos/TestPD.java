package us.lsi.alg.subconjuntos;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.virtual.EGraph;

public class TestPD {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 1; id_fichero < 3; id_fichero++) {

			DatosSubconjuntos.iniDatos("ficheros/subconjuntos" + id_fichero + ".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");

			// Vértices clave

			SubconjuntosVertex start = SubconjuntosVertex.initial();

			// Grafo

			EGraph<SubconjuntosVertex, SubconjuntosEdge> graph = Graphs2.simpleVirtualGraphSum(start,x -> x.weight());

			System.out.println("\n\n#### PI-7 Ej3 Algoritmo PD ####");

			// Algoritmo PD
			DynamicProgrammingReduction<SubconjuntosVertex, SubconjuntosEdge> pdr = 
					DPR.dynamicProgrammingReductionGoal(graph, 
							SubconjuntosVertex.goal(), 
							SubconjuntosHeuristic::heuristic, 
							PDType.Min);
			List<Integer> gp_pdr = 
					pdr.search().get().getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList());
			SolucionSubconjuntos s_pdr = SolucionSubconjuntos.of(gp_pdr);
			System.out.println(s_pdr);

		}
	}

}

