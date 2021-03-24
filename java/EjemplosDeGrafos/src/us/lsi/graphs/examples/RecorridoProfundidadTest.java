package us.lsi.graphs.examples;

import java.util.Set;

import org.jgrapht.Graph;


import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.alg.DephtSearch;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.EGraph;

public class RecorridoProfundidadTest {
	
	public static void main(String[] args) {
		Graph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::getKm);
		
		graph.addVertex(Ciudad.ofName("Londres"));
		
		EGraph<Ciudad,Carretera> g = Graphs2.eGraph(graph,Ciudad.ofName("Sevilla"));
		
		DephtSearch<Ciudad, Carretera> ra = GraphAlg.depth(g,Ciudad.ofName("Sevilla"));
		ra.stream().forEach(v->System.out.println(v.getNombre()));
		Set<Carretera> carreteras = ra.edges();
		
		Graphs2.<Ciudad,Carretera>toDot(graph,"ficheros/andalucia.gv",x->x.getNombre(),x->x.getNombre()+"--"+x.getKm());
		
		Graphs2.<Ciudad,Carretera>toDot(graph,"ficheros/andaluciaProfundidad.gv",
				x->String.format("%s",x.getNombre()+ra.position.get(x)),
				x->String.format("%.2f",x.getKm()),
				v->GraphColors.getColor(Color.black),
				e->GraphColors.getStyleIf(Style.bold,carreteras.contains(e)));
		
		
		System.out.println(carreteras);

	}


}
