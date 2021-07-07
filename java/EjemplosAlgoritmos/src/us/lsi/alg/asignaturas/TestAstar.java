package us.lsi.alg.asignaturas;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;

import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestAstar {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "us"));
		String fichero = "ficheros/asignaturas.txt";

		DatosAsignaturas.iniciarDatos(fichero);
		System.out.println(DatosAsignaturas.mejoras);
		AsignaturasVertice ini = AsignaturasVertice.inicial();
		Predicate<AsignaturasVertice> predicado = t ->AsignaturasVertice.goal(t);
		
		SimpleVirtualGraph<AsignaturasVertice,AsignaturasEdge> grafoAStar = 
				Graphs2.simpleVirtualGraphLast(ini,predicado,null,v->v.constraint(),v->-(double)v.getPeso());
	
		
		AStar<AsignaturasVertice, AsignaturasEdge> as = GraphAlg
				.aStar(grafoAStar,Heuristica::heuristic_negate);
		
		GraphPath<AsignaturasVertice, AsignaturasEdge> s1 = as.search().get();
		
		System.out.println(SolucionPlan.of(s1));
		
		System.out.println("___________________");
		
		as = GraphAlg.aStar(grafoAStar,Heuristica::heuristic_negate);
		
		List<SolucionPlan> s3 = as.searchAll().stream()
				.map(s->SolucionPlan.of(s))
				.sorted(Comparator.comparing(s->-s.mejora()))
				.toList()
				.subList(0, 5);
		
		System.out.println(s3.stream().map(s->s.toString()).collect(Collectors.joining("\n")));
	}

}
