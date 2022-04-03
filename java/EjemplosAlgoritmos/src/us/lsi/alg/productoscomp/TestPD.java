package us.lsi.alg.productoscomp;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestPD {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 0; id_fichero < 2; id_fichero++) {

			DatosProductos.iniDatos("ficheros/productoscomp"+ id_fichero +".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
//			DatosSubconjuntos.toConsole();
			// V�rtices clave

			VertexProductos start = VertexProductos.initial();

			// Grafo

			EGraph<VertexProductos, EdgeProductos> graph = 
					SimpleVirtualGraph.sum(start,VertexProductos.goal(), x -> x.weight());
			
			GraphPath<VertexProductos, EdgeProductos> path = GreedyOnGraph.of(graph,v->v.greedyEdge()).path();

			System.out.println("\n\n#### PI-7 Ej3 Algoritmo PD ####");

			// Algoritmo PD
			DynamicProgrammingReduction<VertexProductos, EdgeProductos> pdr = 
					DynamicProgrammingReduction.of(graph, 
							ProductosHeuristic::heuristic, 
							PDType.Max);
			
			pdr.withGraph = true;
			pdr.bestValue = path.getWeight();
			System.out.println(pdr.bestValue);
			pdr.optimalPath = path;
			Optional<GraphPath<VertexProductos, EdgeProductos>> gp = pdr.search();
			SolucionProductos s_pdr;
			if (gp.isPresent()) {
				List<Integer> gp_pdr = gp.get().getEdgeList().stream().map(x -> x.action())
						.collect(Collectors.toList());
				s_pdr = SolucionProductos.of(gp_pdr);
				System.out.println(s_pdr);
				
			} else {
				System.out.println("Solucion no encontrada!!!");
				//s_pdr = sv;
			}
			
			GraphColors.toDot(pdr.outGraph, "ficheros/productosPDGraph.gv", 
					v -> v.toGraph(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, VertexProductos.goal().test(v)),
					e -> GraphColors.colorIf(Color.red, gp.get().getEdgeList().contains(e)));

		}
	}

}
