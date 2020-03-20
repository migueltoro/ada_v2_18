package us.lsi.search.test;


import java.util.Locale;
import us.lsi.graphs.search.Search;
import us.lsi.mochila.datos.DatosMochila;

import us.lsi.astar.mochila.MochilaEdge;
import us.lsi.astar.mochila.MochilaVertex;

public class TestSearch {
	
	

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;		
		MochilaVertex e1 = MochilaVertex.of(78.);
		Search<MochilaVertex,MochilaEdge> ms = 
				Search.<MochilaVertex,MochilaEdge,Double>greedy(e1,
				v->v.greedyAction(),
				(v,a)->v.neighbor(a),
				MochilaEdge::of);
//		MochilaVertex lv = ms.find(MochilaVertex.lastVertex());
//		System.out.println(ms.pathFromOrigin(lv).getEdgeList().stream().mapToDouble(e->e.getEdgeWeight()).sum());
		System.out.println(ms.findWeight(MochilaVertex.lastVertex()));
	}

}
