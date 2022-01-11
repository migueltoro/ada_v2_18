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
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.virtual.EGraph;


public class AStarTest {
	
	public static Ciudad ciudad(Graph<Ciudad,Carretera> graph, String nombre) {
		return graph.vertexSet().stream().filter(c->c.nombre().equals(nombre)).findFirst().get();
	}
	
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));

		Graph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::km);		
		
		
		Ciudad start = ciudad(graph,"Sevilla");
		Ciudad end = ciudad(graph,"Almeria");
		
		EGraph<Ciudad,Carretera> g = Graphs2.eGraphSum(graph,start,c->c.equals(end),c->c.km());
		
				
		AStar<Ciudad, Carretera> ra = AStar.of(g,(v1,p,v2)->0.,AStarType.Min);
		
		List<Carretera> carreteras = ra.search().orElse(null).getEdgeList();
		
		
		GraphColors.<Ciudad,Carretera>toDot(graph,"ficheros/andalucia.gv",x->x.nombre(),x->x.nombre()+"--"+x.km());
		
		GraphColors.toDot(graph,"ficheros/andaluciaAStar.gv",
				x->x.nombre(),
				x->String.format("%.2f",x.km()),
				v->GraphColors.color(Color.black),
				e->GraphColors.styleIf(Style.bold,carreteras.contains(e)));
		
		
		System.out.println(carreteras);
	}

}
