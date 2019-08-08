package us.lsi.graphs.examples;

import java.io.PrintWriter;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.IntegerComponentNameProvider;

import us.lsi.common.Files2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphcolors.GraphColors;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.manual.RecorridoDijkstraManual;

public class DijkstraManualTest {

	
	public static void main(String[] args) {
		Graph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::create, 
						Carretera::create,
						()->new SimpleWeightedGraph<Ciudad,Carretera>(
								Ciudad::create,Carretera::create),
						Carretera::getKm);
		
		RecorridoDijkstraManual<Ciudad,Carretera> ra = 
				RecorridoDijkstraManual.of(graph,Ciudad.create("Sevilla"),Ciudad.create("Cordoba"));
		List<Carretera> carreteras = ra.minPathToOrigin(Ciudad.create("Cordoba")).getEdgeList();
		
		DOTExporter<Ciudad,Carretera> de1 = new DOTExporter<Ciudad,Carretera>(
				new IntegerComponentNameProvider<>(),
				x->x.getNombre(), 
				x->x.getNombre()+"--"+x.getKm());
		
		DOTExporter<Ciudad,Carretera> de2 = new DOTExporter<Ciudad,Carretera>(
				new IntegerComponentNameProvider<>(),
				x->x.getNombre()+"/"+ra.position(x)+"/"+ra.distanceToOrigin(x), 
				x->""+x.getKm(),
				null,
				e->GraphColors.getStyleIf("bold",e,x->carreteras.contains(x)));
		
		PrintWriter f1 = Files2.getWriter("ficheros/andalucia.gv");
		de1.exportGraph(graph, f1);
		
		PrintWriter f2 = Files2.getWriter("ficheros/andaluciaDijkstra.gv");
		de2.exportGraph(graph, f2);
		
		System.out.println(ra.minPathToOrigin(Ciudad.create("Antequera")));
		
	}
}
