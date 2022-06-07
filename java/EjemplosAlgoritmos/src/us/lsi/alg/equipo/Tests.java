package us.lsi.alg.equipo;

import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class Tests {
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		
		tests("ficheros/DatosEquipo1.txt");
		tests("ficheros/DatosEquipo2.txt");
		tests("ficheros/DatosEquipo3.txt");
	}

	private static void tests(String fichero) {
		DatosEquipo.iniDatos(fichero);
		
		EGraph<EquipoVertex, EquipoEdge> g = 
				EGraph.virtual(EquipoVertex.first(),EquipoVertex::goal,PathType.Sum, Type.Max)
				.heuristic((v1,p,v2)->1000.)
				.build();
		
		testPDR(g);
		testBT(g);
		testAStar(g);
	}

	
	private static void testPDR(EGraph<EquipoVertex, EquipoEdge> grafo) {
		System.out.println("======================== PDR ======================== ");
		GraphPath<EquipoVertex, EquipoEdge> gp = GreedyOnGraph.random(grafo).path();
		DynamicProgrammingReduction<EquipoVertex, EquipoEdge> alg_pdr = 
				DynamicProgrammingReduction.of(grafo,gp.getWeight(),gp,false);
		
		System.out.println(EquipoVertex.getSolucion(alg_pdr.search().get()));
	}
	
	private static void testBT(EGraph<EquipoVertex, EquipoEdge> grafo) {
		System.out.println("======================== BT ======================== ");
		GraphPath<EquipoVertex, EquipoEdge> gp = GreedyOnGraph.random(grafo).path();
		BackTracking<EquipoVertex, EquipoEdge,SolucionEquipo> alg_bt = BackTracking.of(
			grafo,
			EquipoVertex::getSolucion,
			gp.getWeight(),gp,false);
		alg_bt.search();
	    System.out.println(alg_bt.getSolution().get());
	}

	private static void testAStar(EGraph<EquipoVertex, EquipoEdge> grafo) {
		System.out.println("======================== A* ======================== ");
		GraphPath<EquipoVertex, EquipoEdge> gp = GreedyOnGraph.random(grafo).path();
		AStar<EquipoVertex, EquipoEdge> alg_star = AStar.of(
			grafo,
			gp.getWeight(),gp);
		System.out.println(EquipoVertex.getSolucion(alg_star.search().get()));
	}

}
