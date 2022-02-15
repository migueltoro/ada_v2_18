package us.lsi.alg.multiconjuntos;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestPD {

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
			
			SimpleVirtualGraph.constraintG =  MulticonjuntoVertex.constraint();

			EGraph<MulticonjuntoVertex, MulticonjuntoEdge> graph;

			System.out.println("\n\n#### Algoritmo PD ####");

			// Algoritmo PD
			
			graph = SimpleVirtualGraph.sum(start,goal,x -> x.weight());
			
			GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> r = 
					GreedyOnGraph.of(graph,MulticonjuntoVertex::greedyEdge).path();
			
			System.out.println("Voraz = "+r.getWeight()+"  == "+MulticonjuntoVertex.getSolucion(r));
			
			Boolean c = MulticonjuntoVertex.constraint().test(r.getEndVertex());
			
			DynamicProgrammingReduction<MulticonjuntoVertex, MulticonjuntoEdge> pdr = DynamicProgrammingReduction
					.of(graph, 
						MulticonjuntoHeuristic::heuristic, 
						PDType.Min);
			
			pdr.withGraph = true;
			
			if (c) {
				pdr.bestValue = r.getWeight();
			}
			
			
			Optional<GraphPath<MulticonjuntoVertex, MulticonjuntoEdge>> gp = pdr.search();
			
			SolucionMulticonjunto s_pdr = null;
			
			if (gp.isPresent()) {
				System.out.println(gp.get().getEdgeList().stream().map(x -> x.action()).collect(Collectors.toList()));
				List<Integer> gp_pdr = gp.get().getEdgeList().stream().map(x -> x.action()).collect(Collectors.toList()); // getEdgeList();

				s_pdr = SolucionMulticonjunto.create(gp_pdr);

			} 
			
			
			System.out.println(s_pdr);
			
			
			GraphColors.toDot(pdr.outGraph, "ficheros/multiconjuntosPDRGraph.gv", 
					v -> v.toGraph(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, MulticonjuntoVertex.goal().test(v)),
					e -> GraphColors.colorIf(Color.red, gp.isPresent()?gp.get().getEdgeList().contains(e):false));

		}
	}
}

