package us.lsi.alg.multiconjuntos;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.path.EGraphPath;


public class MulticonjuntoHeuristic {

	public static Double heuristic(MulticonjuntoVertex v1, Predicate<MulticonjuntoVertex> goal, MulticonjuntoVertex v2) {
		return heuristic(v1, MulticonjuntoVertex.n_elementos);
	}

	public static Double heuristic(MulticonjuntoVertex vertice, Integer lastIndex) {
			return valReal(vertice,lastIndex);
	}
	
	public static Integer valEntero(MulticonjuntoVertex vertice, Integer lastIndex) {
		Integer r = 0;	
		Integer index = vertice.indice();
		Integer sr = vertice.sr_suma_restante();
		while (sr > 0 && index < lastIndex) {
		    Integer a = sr / DatosMulticonjunto.getElemento(index);
			r = r + a ;
			sr = sr - a * DatosMulticonjunto.getElemento(index);
			index = index + 1;
		}
		return r;
	}
	
	public static SolucionMulticonjunto sol(MulticonjuntoVertex vertice, Integer lastIndex) {
		Integer index = vertice.indice();
		List<Integer>  alt = new ArrayList<>();
		Integer sr = vertice.sr_suma_restante();
		while (index < lastIndex) {
		    Integer a = sr / DatosMulticonjunto.getElemento(index);
		    alt.add(a);
			sr = sr - a * DatosMulticonjunto.getElemento(index);
			index = index + 1;
		}
		return SolucionMulticonjunto.create(alt);
	}
	
	
	public static Double valReal(MulticonjuntoVertex vertice, Integer lastIndex) {
		Double r = 0.;	
		Integer index = vertice.indice();
		Double sr = (double) vertice.sr_suma_restante();
		while (sr > 0 && index < lastIndex) {
		    Double a = sr / DatosMulticonjunto.getElemento(index);
			r = r + a ;
			sr = sr - a * DatosMulticonjunto.getElemento(index);
			index = index + 1;
		}
		return r;
	}
	
	public static void main(String[] args) {

		// Set up
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 0; id_fichero < 7; id_fichero++) {

			DatosMulticonjunto.iniDatos("ficheros/multiconjuntos.txt", id_fichero);
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");

			// Vértices clave

			MulticonjuntoVertex start = MulticonjuntoVertex.initial();
//			Predicate<MulticonjuntoVertex> finalVertex = v -> MulticonjuntoVertex.goal(v);
			
			System.out.println(valEntero(start,DatosMulticonjunto.NUM_E));
			System.out.println(valReal(start,DatosMulticonjunto.NUM_E));
		}
	}

}

