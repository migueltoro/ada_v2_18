package us.lsi.alg.productos;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.virtual.EGraph;

public class TestPD {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 1; id_fichero < 4; id_fichero++) {

			DatosProductos.iniDatos("ficheros/productos" + id_fichero + ".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
			

			ProductosVertex start = ProductosVertex.initial();
			Predicate<ProductosVertex> goal = ProductosVertex.goal();

			EGraph<ProductosVertex, ProductosEdge> graph = 
					Graphs2.simpleVirtualGraphSum(start, goal,null,v->true,x -> x.weight());

			System.out.println("\n\n#### Algoritmo PD ####");

			// Algoritmo PD
			graph = Graphs2.simpleVirtualGraphSum(start,goal,null,v->true,x -> x.weight());
			
			DynamicProgrammingReduction<ProductosVertex, ProductosEdge> pdr = 
					DPR.dynamicProgrammingReduction(graph,
					(v1,p,v2)->0., 
					PDType.Min);
//			pdr.bestValue = ProductosHeuristic.entero(start,DatosProductos.NUM_PRODUCTOS);
			pdr.withGraph = true;
			List<Integer> gp_pdr = pdr.search().get().getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList()); // getEdgeList();
			SolucionProductos s_pdr = SolucionProductos.of(gp_pdr);
			System.out.println(s_pdr);
			Graphs2.toDot(pdr.outGraph,"ficheros/productosPDRGraph.gv",
					v->v.toGraph(),
					e->e.action().toString(),
					v->GraphColors.getColorIf(Color.red,goal.test(v)),
					e->GraphColors.getColorIf(Color.red,pdr.optPath.get().getEdgeList().contains(e))
					);
		}
	}

}

