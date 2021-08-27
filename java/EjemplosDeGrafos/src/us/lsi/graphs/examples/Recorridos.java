package us.lsi.graphs.examples;

import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import us.lsi.colors.GraphColors;
import us.lsi.common.Map2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

/**
 * Calcula varios recorridos sobre un grafo
 * 
 * @author Miguel Toro
 *
 */
public class Recorridos {

	public static void main(String[] args) {

		Graph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::getKm);
		
		DepthFirstIterator<Ciudad, Carretera> rp = new DepthFirstIterator<>(graph,Ciudad.ofName("Sevilla"));
		Map<Ciudad,Integer> m = Map2.empty();
		Integer n = 0;
		while(rp.hasNext()){
			m.put(rp.next(), n);
			n++;
		}	
		
		GraphColors.<Ciudad,Carretera>toDot(graph,"ficheros/andalucia.gv",x->x.getNombre(),x->x.getNombre()+"--"+x.getKm());
		
		BreadthFirstIterator<Ciudad, Carretera> ra = new BreadthFirstIterator<>(graph,Ciudad.ofName("Sevilla"));
		m.clear();
		n = 0;
		while(ra.hasNext()){
			m.put(ra.next(), n);
			n++;
		}	
		
		GraphColors.<Ciudad,Carretera>toDot(graph,"ficheros/andalucia.gv",x->x.getNombre(),x->x.getNombre()+"--"+x.getKm());
		
	}

}
