package us.lsi.alg.asignaturas;


import java.util.Locale;
import java.util.function.Predicate;
import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;


public class TestAsignaturasaStar {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "us"));
		String fichero = "ficheros/asignaturas.txt";

		DatosAsignaturas.iniciarDatos(fichero);
		System.out.println(DatosAsignaturas.mejoras);
		AsignaturasVertice ini = AsignaturasVertice.inicial();
		Predicate<AsignaturasVertice> predicado = t ->AsignaturasVertice.goal(t);
		
		
		SimpleVirtualGraph.constraintG =  AsignaturasVertice.constraint();
		
		EGraph<AsignaturasVertice,AsignaturasEdge> grafoAStar = 
				SimpleVirtualGraph.last(ini,predicado,v->(double)v.getPeso());
	
		
		AStar<AsignaturasVertice, AsignaturasEdge> as = AStar
				.of(grafoAStar,Heuristica::heuristic, AStarType.Max);
		
		GraphPath<AsignaturasVertice, AsignaturasEdge> s1 = as.search().get();
		
		System.out.println(SolucionAsignaturas.of(s1));
		
		System.out.println("___________________");
		
		EGraph<AsignaturasVertice,AsignaturasEdge> grafoPDR = 
				SimpleVirtualGraph.last(ini, predicado,v->(double)v.getPeso());

		DynamicProgrammingReduction<AsignaturasVertice, AsignaturasEdge> pd = DynamicProgrammingReduction
						.of(grafoPDR, Heuristica::heuristic, PDType.Max);

		
		GraphPath<AsignaturasVertice, AsignaturasEdge> s2 = pd.search().get();
		
		System.out.println(SolucionAsignaturas.of(s2));
		
		System.out.println("___________________");
		
		EGraph<AsignaturasVertice,AsignaturasEdge> grafoBT = 
				SimpleVirtualGraph.last(ini,predicado,v->(double)v.getPeso());

		BackTracking<AsignaturasVertice, AsignaturasEdge,SolucionAsignaturas> bt = BackTracking.of(
				grafoBT, 
				Heuristica::heuristic, 
				SolucionAsignaturas::of,
				BTType.Max);
		bt.search();
		System.out.println(bt.getSolution().get());
		System.out.println("___________________");

	}
}
