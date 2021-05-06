package us.lsi.alg.productos;

import java.util.Locale;
import java.util.function.Predicate;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.EGraph;

public class TestBT {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 1; id_fichero < 4; id_fichero++) {

			DatosProductos.iniDatos("ficheros/productos" + id_fichero + ".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");

			ProductosVertex start = ProductosVertex.createInitialVertex();
			Predicate<ProductosVertex> finalVertex = v -> ProductosVertex.goal(v);

			EGraph<ProductosVertex, ProductosEdge> graph = Graphs2.simpleVirtualGraph(start, x -> x.weight());

			
			System.out.println("\n\n#### Algoritmo BT ####");

			// Algoritmo BT
			BackTracking<ProductosVertex, ProductosEdge,SolucionProductos> bta = 
					BT.backTrackingGoal(graph, finalVertex, 
					(v1,p,v2)->0.,
					ProductosVertex::getSolucion, 
					ProductosVertex::copy, BTType.Min);
			bta.bestValue = ProductosHeuristic.entero(start,DatosProductos.NUM_PRODUCTOS);
			bta.withGraph = true;
			bta.search();
			
			System.out.println(bta.getSolution());
			
			Graphs2.toDot(bta.outGraph,"ficheros/productosBTGraph.gv",
					v->v.toGraph(),
					e->e.action().toString(),
					v->GraphColors.getColorIf(Color.red,ProductosVertex.goal(v)),
					e->GraphColors.getColorIf(Color.red,bta.path.getEdgeList().contains(e))
					);

		}
	}

}

