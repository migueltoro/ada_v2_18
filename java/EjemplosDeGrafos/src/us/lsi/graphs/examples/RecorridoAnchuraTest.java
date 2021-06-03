package us.lsi.graphs.examples;

import java.util.Locale;
import java.util.Set;

import org.jgrapht.Graph;


import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.alg.BreadthSearch;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.EGraph;

public class RecorridoAnchuraTest {
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		Graph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("data/andalucia.txt",
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::getKm);
		
		graph.addVertex(Ciudad.ofName("Londres"));
		
		EGraph<Ciudad,Carretera> g = Graphs2.eGraphSum(graph,Ciudad.ofName("Sevilla"));
		
		BreadthSearch<Ciudad, Carretera> ra = GraphAlg.breadth(g,Ciudad.ofName("Sevilla"));
		ra.withGraph = true;
		ra.findEnd();
		Set<Carretera> carreteras = ra.edges();
		
		Graphs2.toDot(graph,"ficheros/andalucia.gv",x->x.getNombre(),x->x.getNombre()+"--"+x.getKm());
		
		Graphs2.toDot(ra.outGraph,"ficheros/andaluciaAnchura.gv",
				x->String.format("%s",x.getNombre()+ra.position.get(x)),
				x->String.format("%.2f",x.getKm()));
		
		
		System.out.println(carreteras);
		

	}


}
