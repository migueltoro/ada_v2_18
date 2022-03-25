package us.lsi.alg.productoscomp;

import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestBT {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 0; id_fichero < 2; id_fichero++) {

			DatosProductos.iniDatos("ficheros/productoscomp"+ id_fichero +".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
//			DatosSubconjuntos.toConsole();
			

			// Vértices clave

			VertexProductos start = VertexProductos.initial();

			// Grafo

			EGraph<VertexProductos, EdgeProductos> graph = 
					SimpleVirtualGraph.sum(start,VertexProductos.goal(), x -> x.weight());
			
			GraphPath<VertexProductos, EdgeProductos> gp = GreedyOnGraph.of(graph,v->v.greedyEdge()).path();

			System.out.println("\n\n#### PI-7 Ej3 Algoritmo BT ####");

			// Algoritmo BT
			BackTracking<VertexProductos, EdgeProductos, SolucionProductos> bta = 
				BackTracking.of(graph, 
					ProductosHeuristic::heuristic,
					SolucionProductos::of, 
					BTType.Max);

			bta.withGraph = true;
			bta.bestValue = gp.getWeight();
			bta.search();
			
			System.out.println(bta.getSolution());
			
			GraphColors.toDot(bta.graph(), "ficheros/productosPDGraph.gv", 
					v -> v.toGraph(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, VertexProductos.goal().test(v)),
					e -> GraphColors.colorIf(Color.red, bta.optimalPath.getEdgeList().contains(e)));

			
		}
	}

}

