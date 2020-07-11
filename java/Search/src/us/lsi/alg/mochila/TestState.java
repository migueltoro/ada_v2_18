package us.lsi.alg.mochila;

import java.util.Locale;

import org.jgrapht.Graphs;

import us.lsi.mochila.datos.DatosMochila;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.BackTracking.State;
import us.lsi.graphs.alg.BackTracking.StatePath;


public class TestState {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;	
		MochilaVertex v1 = MochilaVertex.of(78.);
		MochilaVertex v2 = MochilaVertex.lastVertex();
		
		EGraph<MochilaVertex, MochilaEdge> graph = Graphs2.simpleVirtualGraph(v1,x->x.getEdgeWeight());
		State<MochilaVertex,MochilaEdge> initialState = StatePath.of(graph, e->e.equals(v2), v2);
		System.out.println(initialState);
		MochilaEdge e1 = initialState.getActualVertex().edge(2.);
		initialState.forward(e1);
		System.out.println(initialState);
		MochilaEdge e2 = initialState.getActualVertex().edge(2.);
		initialState.forward(e2);
		System.out.println(initialState);
		initialState.back(e2);
		System.out.println(initialState);
		initialState.back(e1);
		System.out.println(initialState);
		State<MochilaVertex, MochilaEdge> f = StatePath.of(graph,  e->e.equals(v2), v2);
		System.out.println(f);
		System.out.println(Graphs.neighborListOf(graph,v2));
	}

}
