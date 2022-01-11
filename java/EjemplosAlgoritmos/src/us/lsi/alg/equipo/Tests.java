package us.lsi.alg.equipo;

import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class Tests {
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		
		tests("ficheros/DatosEquipo1.txt");
		tests("ficheros/DatosEquipo2.txt");
		tests("ficheros/DatosEquipo3.txt");
	}

	private static void tests(String fichero) {
		DatosEquipo.iniDatos(fichero);
		
		EGraph<EquipoVertex, EquipoEdge> grafoPDR = 
				SimpleVirtualGraph.sum(EquipoVertex.first(),EquipoVertex::goal, e-> e.weight());
		
		EGraph<EquipoVertex, EquipoEdge> grafoBT = 
				SimpleVirtualGraph.sum(EquipoVertex.first(),EquipoVertex::goal, e-> e.weight());
		
		EGraph<EquipoVertex, EquipoEdge> grafoAStar = 
		SimpleVirtualGraph.sum(EquipoVertex.first(), EquipoVertex::goal, e-> e.weight());
		
		testPDR(grafoPDR);
		testBT(grafoBT);
		testAStar(grafoAStar);
	}

	
	private static void testPDR(EGraph<EquipoVertex, EquipoEdge> grafo) {
		System.out.println("======================== PDR ======================== ");
		DynamicProgrammingReduction<EquipoVertex, EquipoEdge> alg_pdr = DynamicProgrammingReduction.of( 
			grafo,
			(v1,p,v2)->1000.,
			PDType.Max);
		GraphPath<EquipoVertex, EquipoEdge> gp = GreedyOnGraph.random(grafo).path();
		alg_pdr.bestValue = gp.getWeight();
		alg_pdr.optimalPath = gp;
		System.out.println(EquipoVertex.getSolucion(alg_pdr.search().get()));
	}
	
	private static void testBT(EGraph<EquipoVertex, EquipoEdge> grafo) {
		System.out.println("======================== BT ======================== ");
		BackTracking<EquipoVertex, EquipoEdge,SolucionEquipo> alg_bt = BackTracking.of(
			grafo,
			(v1,p,v2)->1000.,
			EquipoVertex::getSolucion,
			BTType.Max);
		GraphPath<EquipoVertex, EquipoEdge> gp = GreedyOnGraph.random(grafo).path();
		alg_bt.bestValue = gp.getWeight();
		alg_bt.optimalPath = gp;
		alg_bt.search();
	    System.out.println(alg_bt.getSolution().get());
	}

	private static void testAStar(EGraph<EquipoVertex, EquipoEdge> grafo) {
		System.out.println("======================== A* ======================== ");
		AStar<EquipoVertex, EquipoEdge> alg_star = AStar.of(
			grafo,
			(v1,p,v2)->1000.,
			AStarType.Max);
		System.out.println(EquipoVertex.getSolucion(alg_star.search().get()));
	}

}
