package us.lsi.search.test;

import java.util.Locale;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import us.lsi.mochila.datos.DatosMochila;
import us.lsi.astar.mochila.MochilaEdge;
import us.lsi.astar.mochila.MochilaVertex;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.search.BackTrackingSearch.State;

public class TestState {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;	
		MochilaVertex e1 = MochilaVertex.of(78.);
		MochilaVertex e2 = MochilaVertex.lastVertex();
		Graph<MochilaVertex, MochilaEdge> graph = Graphs2.astarSimpleVirtualGraph(x->x.getEdgeWeight());
		State<MochilaVertex,MochilaEdge> initial = State.of(graph, e1);
		System.out.println(initial);
		initial.forward(initial.actualVertex.getEdgeFromAction(2.));
		System.out.println(initial);
		initial.forward(initial.actualVertex.getEdgeFromAction(2.));
		System.out.println(initial);
		initial.back();
		System.out.println(initial);
		initial.back();
		System.out.println(initial);
		State<MochilaVertex,MochilaEdge> f = State.of(graph, e2);
		System.out.println(f);
		System.out.println(Graphs.neighborListOf(graph,e2));
	}

}
