package us.lsi.astar.vuelos;

import us.lsi.astar.AlgoritmoAStar;
import us.lsi.graphs.*;



public class Test {
	
	public static String create(String[] a){
		return a[0];
	}

	/**
	 * @param args Argumentos
	 */
	public static void main(String[] args) {
		GrafoVuelos grafo = GraphsReader.newGraph("ficheros/vuelos.txt", Test::create, Vuelo::create,()->new GrafoVuelos(null,null));
		grafo.horaDeLlegadaAlAeropuerto = 7.;
		System.out.println(grafo.vertexSet());
		AlgoritmoAStar<String,Vuelo> a = AlgoritmoAStar.create(grafo, "Sevilla", "Malaga");
		System.out.println(a.getPath());
		System.out.println(a.getPath().getWeight());
	}

}

