package us.lsi.alg.asignaturas;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestAstar {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "us"));
		String fichero = "ficheros/asignaturas.txt";

		DatosAsignaturas.iniciarDatos(fichero);
		System.out.println(DatosAsignaturas.mejoras);
		AsignaturasVertice v0 = AsignaturasVertice.inicial();
		Predicate<AsignaturasVertice> predicado = t ->AsignaturasVertice.goal(t);

		
		EGraph<AsignaturasVertice,AsignaturasEdge> grafo = 
				EGraph.virtual(v0,predicado,PathType.Last,Type.Max)
				.vertexWeight(v->(double)v.getPeso())
				.goalHasSolution(AsignaturasVertice.goalHasSolution())
				.heuristic(Heuristica::heuristic)
				.build();
	
		
		AStar<AsignaturasVertice, AsignaturasEdge> as = AStar.of(grafo);
		
		GraphPath<AsignaturasVertice, AsignaturasEdge> s1 = as.search().get();
		
		System.out.println(SolucionAsignaturas.of(s1));
		
		System.out.println("___________________");
		
		as = AStar.of(grafo);
		
		List<SolucionAsignaturas> s3 = as.searchAll().stream()
				.map(s->SolucionAsignaturas.of(s))
				.sorted(Comparator.comparing(s->-s.mejora()))
				.toList()
				.subList(0, 5);
		
		System.out.println(s3.stream().map(s->s.toString()).collect(Collectors.joining("\n")));
	}

}
