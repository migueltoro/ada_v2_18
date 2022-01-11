package us.lsi.alg.equipo;


import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class Test3 {
	
	public static void test(String file) {
		Locale.setDefault(new Locale("en", "US"));
		
		DatosEquipo.iniDatos(file);
		
		EGraph<EquipoVertex, EquipoEdge> g = 
				SimpleVirtualGraph.sum(EquipoVertex.first(),EquipoVertex::goal, e-> e.weight());
		
		GreedyOnGraph<EquipoVertex, EquipoEdge> vr2 = GreedyOnGraph.random(g);
		
		GraphPath<EquipoVertex, EquipoEdge> gp = vr2.path();
		
//		Optional<GraphPath<EquipoVertex, EquipoEdge>> r = vr2.path();
		
		Double r = gp.getWeight();
		System.out.println(r);
		System.out.println(gp.getEndVertex());
		System.out.println(vr2.isSolution(gp));
	}

	public static void main(String[] args) {
		
		test("ficheros/DatosEquipo1.txt");
	
	}

}
