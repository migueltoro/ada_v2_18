package us.lsi.graphs.examples;


import java.util.List;
import java.util.Locale;

import org.jgrapht.Graph;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.EGraph;


public class AStarTest {
	
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));

		Graph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::getKm);		
		
		
		Ciudad start = Ciudad.ofName("Sevila");
		Ciudad end = Ciudad.ofName("Almeria");
		
		EGraph<Ciudad,Carretera> g = Graphs2.eGraphSum(graph,start,c->c.equals(end),end,v->true);
		
				
		AStar<Ciudad, Carretera> ra = GraphAlg.aStar(g,(v1,p,v2)->0.);
		
		List<Carretera> carreteras = ra.search().orElse(null).getEdgeList();
		
		
		Graphs2.<Ciudad,Carretera>toDot(graph,"ficheros/andalucia.gv",x->x.getNombre(),x->x.getNombre()+"--"+x.getKm());
		
		Graphs2.<Ciudad,Carretera>toDot(graph,"ficheros/andaluciaAStar.gv",
				x->String.format("%s",x.getNombre()),
				x->String.format("%.sf",x.getKm()),
				v->GraphColors.getColor(Color.black),
				e->GraphColors.getStyleIf(Style.bold,carreteras.contains(e)));
		
		
		System.out.println(carreteras);
	}

}
