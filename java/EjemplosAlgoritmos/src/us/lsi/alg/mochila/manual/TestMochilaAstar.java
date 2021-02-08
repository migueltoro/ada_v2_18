package us.lsi.alg.mochila.manual;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import us.lsi.alg.mochila.MochilaEdge;
import us.lsi.alg.mochila.MochilaVertex;
import us.lsi.mochila.datos.DatosMochila;

public class TestMochilaAstar {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		MochilaVertex.capacidadInicial = 78;
		MochilaVertex v1 = MochilaVertex.initialVertex();
		MochilaVertex v2 = MochilaVertex.lastVertex();
		Optional<List<MochilaEdge>> edges = MochilaAStar.of(v1, v2, Auxiliar::heuristic_2).search();	
		System.out.println(Auxiliar.solucionFromEdges(edges.get()));
	}

}
