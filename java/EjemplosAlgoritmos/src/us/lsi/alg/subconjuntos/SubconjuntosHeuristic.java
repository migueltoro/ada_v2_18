package us.lsi.alg.subconjuntos;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import us.lsi.common.String2;

public class SubconjuntosHeuristic {

	public static Double heuristic(SubconjuntosVertex v1, Predicate<SubconjuntosVertex> goal, SubconjuntosVertex v2) {
		return heuristic1(v1, DatosSubconjuntos.NUM_SC);
	}
	
	public static Double voraz(SubconjuntosVertex vertice, Integer lastIndex) {
		Double peso = 0.;
		while(!vertice.cubreUniverso() && vertice.indice() < lastIndex) {
			Integer a = vertice.greedyAction();
			peso = a*DatosSubconjuntos.peso(vertice.indice());
			vertice = vertice.neighbor(a);
		}
		return peso;
	}
	
	public static SolucionSubconjuntos solucionVoraz(SubconjuntosVertex vertice, Integer lastIndex) {
		Double peso = 0.;
		Set<String> s = new HashSet<>();
		Set<Integer> ss = new HashSet<>();
		while(vertice.indice() < lastIndex && !vertice.cubreUniverso()) {
			Integer a = vertice.greedyAction();
			peso += a*DatosSubconjuntos.peso(vertice.indice());
			if(a==1) {
				s.add(DatosSubconjuntos.nombre(vertice.indice()));
				ss.addAll(DatosSubconjuntos.conjunto(vertice.indice()));
			}
			vertice = vertice.neighbor(a);
		}
//		String2.toConsole(String.format("%s,\nSolucion = %s",vertice,s));
		Boolean c = ss.equals(DatosSubconjuntos.universo());
		return new SolucionSubconjuntos(peso,s,c);
	}

	public static Double heuristic1(SubconjuntosVertex vertice, Integer lastIndex) {
		if (vertice.cubreUniverso())  return 0.;
		else return IntStream.range(vertice.indice(),lastIndex)
				.mapToDouble(i->DatosSubconjuntos.peso(i))
				.min()
				.getAsDouble();
	}
	
	public static Double heuristic2(SubconjuntosVertex vertice, Integer lastIndex) {
		Double peso = 0.;
		while(vertice.indice() < lastIndex && !vertice.cubreUniversoDespues()) {
			Integer a = vertice.greedyAction();
			peso += a*DatosSubconjuntos.peso(vertice.indice());
			vertice = vertice.neighbor(a);
		}
		return peso;
	}
	
	public static void main(String[] args) {

		
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 1; id_fichero < 3; id_fichero++) {

			DatosSubconjuntos.iniDatos("ficheros/subconjuntos" + id_fichero + ".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
//			DatosSubconjuntos.toConsole();
			
			SubconjuntosVertex start = SubconjuntosVertex.initial();
			
			String2.toConsole("Voraz = "+solucionVoraz(start,DatosSubconjuntos.NUM_SC).toString());
			String2.toConsole("H1 = "+heuristic1(start,DatosSubconjuntos.NUM_SC).toString());
			String2.toConsole("H2 = "+heuristic2(start,DatosSubconjuntos.NUM_SC).toString());
		}
	}

}

