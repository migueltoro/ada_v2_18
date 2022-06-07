package us.lsi.alg.asignaturas;


import java.util.Locale;
import java.util.function.Predicate;
import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;


public class TestAsignaturasaStar {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "us"));
		String fichero = "ficheros/asignaturas.txt";

		DatosAsignaturas.iniciarDatos(fichero);
		System.out.println(DatosAsignaturas.mejoras);
		AsignaturasVertice v0 = AsignaturasVertice.inicial();
		Predicate<AsignaturasVertice> predicado = t ->AsignaturasVertice.goal(t);
		
		EGraph<AsignaturasVertice,AsignaturasEdge> grafo = 
				EGraph.<AsignaturasVertice,AsignaturasEdge>virtual(v0,predicado,PathType.Last,Type.Max)
				.vertexWeight(v->(double)v.getPeso())
				.goalHasSolution(AsignaturasVertice.goalHasSolution())
				.heuristic(Heuristica::heuristic)
				.build();
		
		AStar<AsignaturasVertice, AsignaturasEdge> as = AStar.of(grafo);
		
		GraphPath<AsignaturasVertice, AsignaturasEdge> s1 = as.search().get();
		
		System.out.println(SolucionAsignaturas.of(s1));
		
		System.out.println("___________________");

		DynamicProgrammingReduction<AsignaturasVertice, AsignaturasEdge> pd = DynamicProgrammingReduction
						.of(grafo);

		
		GraphPath<AsignaturasVertice, AsignaturasEdge> s2 = pd.search().get();
		
		System.out.println(SolucionAsignaturas.of(s2));
		
		System.out.println("___________________");

		BackTracking<AsignaturasVertice, AsignaturasEdge,SolucionAsignaturas> bt = BackTracking.of(
				grafo, 
				SolucionAsignaturas::of);
		bt.search();
		System.out.println(bt.getSolution().get());
		System.out.println("___________________");

	}
}
