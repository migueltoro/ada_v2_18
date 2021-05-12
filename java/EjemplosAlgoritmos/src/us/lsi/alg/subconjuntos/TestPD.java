package us.lsi.alg.subconjuntos;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
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

		for (Integer id_fichero = 2; id_fichero < 3; id_fichero++) {

			DatosSubconjuntos.iniDatos("ficheros/subconjuntos" + id_fichero + ".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
//			DatosSubconjuntos.toConsole();
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
			pdr.bestValue = SubconjuntosHeuristic.voraz(start,DatosSubconjuntos.NUM_SC);
			System.out.println("Best = "+pdr.bestValue);
			SolucionSubconjuntos sv = SubconjuntosHeuristic.solucionVoraz(start,DatosSubconjuntos.NUM_SC);
			List<SubconjuntosEdge> le = SubconjuntosHeuristic.pathVoraz(start,DatosSubconjuntos.NUM_SC);
			System.out.println("Sv = "+sv);
			pdr.withGraph = true;
			Optional<GraphPath<SubconjuntosVertex, SubconjuntosEdge>> gp = pdr.search();
			System.out.println(gp);
			SolucionSubconjuntos s_pdr;
			if (gp.isPresent()) {
				List<Integer> gp_pdr = gp.get().getEdgeList().stream().map(x -> x.action())
						.collect(Collectors.toList());
				s_pdr = SolucionSubconjuntos.of(gp_pdr);
			} else { 				
				s_pdr = sv;
			}

			System.out.println(s_pdr);
			
			Graphs2.toDot(pdr.outGraph,"ficheros/subconjuntosPDGraph.gv",
					v->v.toGraph(),
					e->e.action().toString(),
					v->GraphColors.getColorIf(Color.red,SubconjuntosVertex.goal().test(v)),
					e->GraphColors.getColorIf(Color.red,(gp.isPresent()?gp.get().getEdgeList():le).contains(e))
					);
		}
	}

}

