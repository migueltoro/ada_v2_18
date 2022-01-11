package us.lsi.alg.bufete;


import java.util.Locale;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestPD {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 1; id_fichero < 4; id_fichero++) {

			DatosBufete.iniDatos("ficheros/bufete" + id_fichero + ".txt");

			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
			final BufeteVertex start = BufeteVertex.initialVertex();
			Predicate<BufeteVertex> goal = BufeteVertex.goal();

			/**
			 * IMPORTANTE. En este tipo se usa el tipo "Last".
			 */
			EGraph<BufeteVertex, BufeteEdge> graph = 
					SimpleVirtualGraph.last(start,goal,v -> (double) v.maxCarga());

			System.out.println("\n\n#### Algoritmo PD ####");
			
			GreedyOnGraph<BufeteVertex, BufeteEdge> rr = 
					GreedyOnGraph.of(graph,
							BufeteVertex::greadyEdge);
			
			GraphPath<BufeteVertex, BufeteEdge> path = rr.path();
			SolucionBufete sm = SolucionBufete.of(path);
			Double bv = path.getWeight();			
			System.out.println(bv);
			
			// Algoritmo PD
			DynamicProgrammingReduction<BufeteVertex, BufeteEdge> pdr = 
					DynamicProgrammingReduction.of(graph,
					Heuristica::heuristic, 
					PDType.Min);
			
			pdr.bestValue = bv;
			pdr.optimalPath = path;

			GraphPath<BufeteVertex, BufeteEdge> gp_pdr = pdr.search().orElse(null); // getEdgeList();
			if ( gp_pdr != null) {
				SolucionBufete s_pdr = SolucionBufete.of(gp_pdr);
				System.out.println(s_pdr.maxCarga());
				System.out.println(s_pdr);
			} else {
				System.out.println(sm.maxCarga());
				System.out.println(sm);
			}
		}
	}
}

