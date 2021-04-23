package us.lsi.graphs.examples;


import java.util.List;
import java.util.Locale;

import org.jgrapht.Graph;

import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.EGraph;

public class DijkstraTest {

	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		Graph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::getKm);
		
		EGraph<Ciudad,Carretera> g = Graphs2.eGraph(graph,Ciudad.ofName("Sevilla"));
		
		AStar<Ciudad, Carretera> rd = GraphAlg.dijsktra(g,Ciudad.ofName("Almeria"));
		rd.withGraph = true;
		
		List<Carretera> carreteras = rd.search().orElse(null).getEdgeList();
		
		
		Graphs2.toDot(graph,"ficheros/andalucia.gv",
				x->x.getNombre(),x->x.getNombre()+"--"+x.getKm());
		
		Graphs2.toDot(rd.outGraph,"ficheros/andaluciaAStar.gv",
				x->String.format("%s",x.getNombre()),
				x->String.format("%.2f",x.getKm()));
		
		System.out.println(carreteras);
	}
}
