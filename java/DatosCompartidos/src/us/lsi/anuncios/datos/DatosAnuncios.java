package us.lsi.anuncios.datos;

import java.util.*;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.common.Lists2;
import us.lsi.common.Preconditions;
import us.lsi.common.Sets2;
import us.lsi.common.Streams2;
import us.lsi.common.Tuple;
import us.lsi.common.Tuple2;



public class DatosAnuncios {

	public static List<Anuncio> todosLosAnunciosDisponibles;
	public static Integer tiempoTotal;
	public static Set<Tuple2<Integer,Integer>> restricciones;
	public static Set<Integer> todosLosAnuncios; 
	
	public DatosAnuncios() {
		super();
	}
	
	public static void leeYOrdenaAnuncios(String file){	
		List<String> ls = Files2.streamFromFile(file)
				.collect(Collectors.toList());
		int index = ls.indexOf("#");
		List<String> ls1 = ls.subList(0, index);
		List<String> ls2 = ls.subList(index+1, ls.size());
		todosLosAnunciosDisponibles = Lists2.empty();
		Anuncio a;
		for(String s : ls1){
			String[] at = Streams2.fromString(s, ",").<String>toArray((int x)->new String[x]);
			Preconditions.checkArgument(at.length==3);
			a = Anuncio.create(at);
			todosLosAnunciosDisponibles.add(a);
		}
		restricciones = new HashSet<>();
		for(String s : ls2){
			String[] at = Streams2.fromString(s, ",").<String>toArray((int e)->new String[e]);
			Preconditions.checkArgument(at.length==2);
			Integer n1 = Integer.parseInt(at[0]);
			Integer n2 = Integer.parseInt(at[1]);
			restricciones.add(Tuple.create(n1, n2));
			restricciones.add(Tuple.create(n2, n1));
		}
		Collections.sort(DatosAnuncios.todosLosAnunciosDisponibles, Comparator.<Anuncio>naturalOrder().reversed());
		todosLosAnuncios = Sets2.newSet(0, DatosAnuncios.todosLosAnunciosDisponibles.size());
	}

	public static DatosAnuncios create() {		
		return new DatosAnuncios();
	}

	public static Anuncio getAnuncio(int i){
		return todosLosAnunciosDisponibles.get(i);
	}
	
}
