package us.lsi.alg.tsp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.jgrapht.Graph;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.virtual.EGraph;

public class TestCamino {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		Graph<Ciudad,Carretera> graph1 = AuxiliaryTsp.leeGraph("ficheros/andalucia.txt");
		Graph<Ciudad,Carretera> graph2 = AuxiliaryTsp.completeGraph(graph1);
		TravelVertex.graph = graph2;
		List<Ciudad> camino = Arrays.asList(Ciudad.ofName("Sevilla"),
				Ciudad.ofName("Huelva"),
				Ciudad.ofName("Cordoba"),
				Ciudad.ofName("Almeria"),
				Ciudad.ofName("Malaga"),
				Ciudad.ofName("Algeciras"),
				Ciudad.ofName("Malaga"),
				Ciudad.ofName("Granada"));
		Collections.shuffle(camino);
		
		TravelVertex v1 = TravelVertex.of(camino);
		EGraph<TravelVertex,TravelEdge> graph = 
				Graphs2.simpleVirtualGraphLast(v1,null,null,v->true,v->v.weight());
		
		Double error = 0.1;
		Double r;
		do {
			System.out.println(v1);
			Double w = v1.weight();
			v1 = v1.neighbor(v1.greedyAction());
			Double nw = v1.weight();
			r = Math.abs(w-nw);			
		}while(r > error);
	}

}
