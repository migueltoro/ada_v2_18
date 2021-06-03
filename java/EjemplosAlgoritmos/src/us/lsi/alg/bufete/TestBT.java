package us.lsi.alg.bufete;

import java.util.Locale;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.GreedySearchOnGraph;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.EGraph;

public class TestBT {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 1; id_fichero < 4; id_fichero++) {

			DatosBufete.iniDatos("ficheros/bufete" + id_fichero + ".txt");
//			DatosBufete.toConsole();

			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");

			BufeteVertex start = BufeteVertex.initialVertex();
			Predicate<BufeteVertex> goal = BufeteVertex.goal();

			/**
			 * IMPORTANTE. En este tipo se usa el tipo "Last".
			 */
			EGraph<BufeteVertex, BufeteEdge> graph = 
					Graphs2.simpleVirtualGraphLast(start,goal,null,v->true,v -> (double)v.maxCarga());
			
			System.out.println("\n\n#### Algoritmo BT ####");
			
			GreedySearchOnGraph<BufeteVertex, BufeteEdge> rr = 
					GraphAlg.greedy(graph,
							BufeteVertex::greadyEdge);
			
			GraphPath<BufeteVertex, BufeteEdge> path = rr.search().orElse(null);
			SolucionBufete sm = SolucionBufete.of(path);
			Double bv = path.getWeight();
			System.out.println(bv);
			
			// Algoritmo BT
			BackTracking<BufeteVertex, BufeteEdge, SolucionBufete> bta = BT.backTracking(graph, 
					Heuristica::heuristic, 
					SolucionBufete::of,
					BufeteVertex::copy, 
					BTType.Min);

			bta.bestValue = bv;
			bta.solutions.add(sm);
			
			bta.search();

			SolucionBufete mejorSolucion = bta.getSolution().get();
			System.out.println("maxCarga = "+mejorSolucion.maxCarga());
			System.out.println(mejorSolucion);
		}
	}
}

