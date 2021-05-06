package us.lsi.alg.productos;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.EGraph;

public class TestAS {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 1; id_fichero < 4; id_fichero++) {

			DatosProductos.iniDatos("ficheros/productos" + id_fichero + ".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
			System.out.println("#### Algoritmo A* ####");

			ProductosVertex start = ProductosVertex.createInitialVertex();
			Predicate<ProductosVertex> finalVertex = v -> ProductosVertex.goal(v);

			EGraph<ProductosVertex, ProductosEdge> graph = Graphs2.simpleVirtualGraph(start, x -> x.weight());

			

			// Algoritmo A*
//			graph = Graphs2.simpleVirtualGraph(start, x -> x.weight());
			AStar<ProductosVertex, ProductosEdge> aStar = GraphAlg.aStar(graph, finalVertex, null, (v1, p, v2) -> 0.);
			aStar.withGraph = true;
			GraphPath<ProductosVertex, ProductosEdge> gp = aStar.search().get();

			List<Integer> gp_as = gp.getEdgeList().stream().map(x -> x.action()).collect(Collectors.toList()); // getEdgeList();

			SolucionProductos s_as = SolucionProductos.create(gp_as);
			System.out.println(s_as);

//			System.out.println(gp.getVertexList());
//			System.out.println(gp.getEdgeList());
			Graphs2.toDot(aStar.outGraph, "ficheros/productosAStarGraph.gv", v -> v.toGraph(),
					e -> e.action().toString(), v -> GraphColors.getColorIf(Color.red, ProductosVertex.goal(v)),
					e -> GraphColors.getColorIf(Color.red, gp.getEdgeList().contains(e)));

		}

	}

}

