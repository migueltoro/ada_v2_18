package us.lsi.graphs.examples;


import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Style;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

/**
 * Resulve un problema de coloreado de grafos
 * 
 * @author Miguel Toro
 *
 */
public class ColoreadoDeGrafos {

	public static void main(String[] args) {
		
		Graph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::getKm);
		
		VertexColoringAlgorithm<Ciudad> vca = new GreedyColoring<>(graph);
		VertexColoringAlgorithm.Coloring<Ciudad> vc = vca.getColoring();
		Map<Ciudad,Integer> colorDeCiudad = vc.getColors();
		System.out.println(vc.getNumberColors());
		
		Graphs2.toDot(graph,"ficheros/coloresAndalucia.gv",x->x.getNombre(),x->x.getNombre(),
				x->GraphColors.getColor(colorDeCiudad.get(x)),
				e->GraphColors.getStyle(Style.solid));

	}

}
