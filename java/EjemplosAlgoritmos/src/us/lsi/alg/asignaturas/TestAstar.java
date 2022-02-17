package us.lsi.alg.asignaturas;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestAstar {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "us"));
		String fichero = "ficheros/asignaturas.txt";

		DatosAsignaturas.iniciarDatos(fichero);
		System.out.println(DatosAsignaturas.mejoras);
		AsignaturasVertice ini = AsignaturasVertice.inicial();
		Predicate<AsignaturasVertice> predicado = t ->AsignaturasVertice.goal(t);
		
		EGraph<AsignaturasVertice,AsignaturasEdge> grafoAStar = 
				SimpleVirtualGraph.last(ini,predicado,v->(double)v.getPeso(), AsignaturasVertice.constraint());
	
		
		AStar<AsignaturasVertice, AsignaturasEdge> as = AStar
				.of(grafoAStar,Heuristica::heuristic,AStarType.Max);
		
		GraphPath<AsignaturasVertice, AsignaturasEdge> s1 = as.search().get();
		
		System.out.println(SolucionAsignaturas.of(s1));
		
		System.out.println("___________________");
		
		as = AStar.of(grafoAStar,Heuristica::heuristic, AStarType.Max);
		
		List<SolucionAsignaturas> s3 = as.searchAll().stream()
				.map(s->SolucionAsignaturas.of(s))
				.sorted(Comparator.comparing(s->-s.mejora()))
				.toList()
				.subList(0, 5);
		
		System.out.println(s3.stream().map(s->s.toString()).collect(Collectors.joining("\n")));
	}

}
