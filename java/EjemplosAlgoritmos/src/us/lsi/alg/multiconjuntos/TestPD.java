package us.lsi.alg.multiconjuntos;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.virtual.EGraph;

public class TestPD {

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

			System.out.println("\n\n#### Algoritmo PD ####");

			// Algoritmo PD
			graph = Graphs2.simpleVirtualGraphSum(start, x -> x.weight());
			DynamicProgrammingReduction<MulticonjuntoVertex, MulticonjuntoEdge> pdr = DPR
					.dynamicProgrammingReductionGoal(graph, finalVertex, 
							MulticonjuntoHeuristic::heuristic, 
							PDType.Min);
			
			pdr.withGraph = true;
			pdr.bestValue = (double)MulticonjuntoHeuristic.valEntero(start,DatosMulticonjunto.NUM_E);
			
			System.out.println(pdr.bestValue);
			
			
			Optional<GraphPath<MulticonjuntoVertex, MulticonjuntoEdge>> gp = pdr.search();
			
			SolucionMulticonjunto s_pdr;
			
			if (gp.isPresent()) {
				System.out.println(gp.get().getEdgeList().stream().map(x -> x.action()).collect(Collectors.toList()));
				List<Integer> gp_pdr = gp.get().getEdgeList().stream().map(x -> x.action()).collect(Collectors.toList()); // getEdgeList();

				s_pdr = SolucionMulticonjunto.create(gp_pdr);

			} else {
				s_pdr = MulticonjuntoHeuristic.sol(start, DatosMulticonjunto.NUM_E);
			}
			
			
			System.out.println(s_pdr);
			
			
			Graphs2.toDot(pdr.outGraph, "ficheros/multiconjuntosPDRGraph.gv", 
					v -> v.toGraph(),
					e -> e.action().toString(), 
					v -> GraphColors.getColorIf(Color.red, MulticonjuntoVertex.goal(v)),
					e -> GraphColors.getColorIf(Color.red, gp.isPresent()?gp.get().getEdgeList().contains(e):false));

		}
	}
}

