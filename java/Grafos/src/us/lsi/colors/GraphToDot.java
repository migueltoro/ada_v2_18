package us.lsi.colors;



import org.jgrapht.Graph;

import us.lsi.colors.GraphColors.*;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class GraphToDot {

	
	
	public static void main(String[] args) {
	
		
		Graph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::getKm);
		
		Graphs2.<Ciudad,Carretera>toDot(graph,"ficheros/andalucia.gv",v->v.getNombre(),
				e->e.getKm().toString(),
				v->GraphColors.getColorIf(Color.green,Color.blue,v.getHabitantes() < 500000),
				e->GraphColors.getStyleIf(Style.bold, e.getKm() < 100));

	}

}
