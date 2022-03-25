package us.lsi.alg.productoscomp;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestAStart {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(new Locale("en", "US"));
		
		for (Integer i = 0; i < 2; i++) {

			DatosProductos.iniDatos("ficheros/productoscomp"+ i +".txt");
			System.out.println("\n\n>\tResultados para el test " + i + "\n");

			// Vértices clave

			VertexProductos start = VertexProductos.initial();
			Predicate<VertexProductos> goal = VertexProductos.goal();

			// Grafo

			System.out.println("#### Algoritmo A* ####");

			// Algoritmo A*
			
			EGraph<VertexProductos, EdgeProductos> graph = 
					SimpleVirtualGraph.sum(start,goal,x -> x.weight());
			
			AStar<VertexProductos, EdgeProductos> aStar = AStar.of(graph,
					ProductosHeuristic::heuristic,AStarType.Max);
			
			GraphPath<VertexProductos, EdgeProductos> gp = aStar.search().get();
			
			List<Integer> gp_as = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList()); // getEdgeList();
				
			SolucionProductos s_as = SolucionProductos.of(gp);
			
			
			System.out.println(s_as);
			System.out.println(gp_as);

			GraphColors.toDot(aStar.graph(), "ficheros/productosAStarGraph.gv", 
					v -> v.toGraph(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, VertexProductos.goal().test(v)),
					e -> GraphColors.colorIf(Color.red, gp.getEdgeList().contains(e)));
		}
	}

}
