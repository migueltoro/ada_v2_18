package us.lsi.alg.subconjuntos;

import java.util.List;
import java.util.Locale;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestBT {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 1; id_fichero < 3; id_fichero++) {

			DatosSubconjuntos.iniDatos("ficheros/subconjuntos" + id_fichero + ".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
//			DatosSubconjuntos.toConsole();
			

			// V�rtices clave

			SubconjuntosVertex start = SubconjuntosVertex.initial();

			// Grafo

			EGraph<SubconjuntosVertex, SubconjuntosEdge> graph = 
					SimpleVirtualGraph.sum(start,SubconjuntosVertex.goal(), x -> x.weight());

			System.out.println("\n\n#### PI-7 Ej3 Algoritmo BT ####");

			// Algoritmo BT
			BackTracking<SubconjuntosVertex, SubconjuntosEdge, SolucionSubconjuntos> bta = 
				BackTracking.of(graph, 
					SubconjuntosHeuristic::heuristic,
					SolucionSubconjuntos::of, 
					BTType.Min);

			bta.withGraph = true;
			bta.bestValue = SubconjuntosHeuristic.voraz(start,DatosSubconjuntos.NUM_SC);
			System.out.println("Best = "+bta.bestValue);
			SolucionSubconjuntos sv = SubconjuntosHeuristic.solucionVoraz(start,DatosSubconjuntos.NUM_SC);
			List<SubconjuntosEdge> le = SubconjuntosHeuristic.pathVoraz(start,DatosSubconjuntos.NUM_SC);
			System.out.println("Sv = "+sv);
			bta.search();
			
			System.out.println(bta.getSolution().isPresent()?bta.getSolution().get():sv);
			List<SubconjuntosEdge> ls = bta.optimalPath != null?bta.optimalPath.getEdgeList():null;
			
			GraphColors.toDot(bta.graph(),"ficheros/subconjuntosBTGraph.gv",
					v->v.toGraph(),
					e->e.action().toString(),
					v->GraphColors.colorIf(Color.red,SubconjuntosVertex.goal().test(v)),
					e->GraphColors.colorIf(Color.red,(bta.optimalPath != null?ls:le).contains(e))
					);
			
//			System.out.println(ls.stream().map(e->e.action()).toList());
		}
	}

}

