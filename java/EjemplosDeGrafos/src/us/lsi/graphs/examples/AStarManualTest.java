package us.lsi.graphs.examples;

import java.io.PrintWriter;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.IntegerComponentNameProvider;

import us.lsi.colors.GraphColors;
import us.lsi.common.Files2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.walks.manual.RecorridoAStarManual;


public class AStarManualTest {
	
	public static void main(String[] args) {
		Graph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::getKm);		
		RecorridoAStarManual<Ciudad,Carretera> ra = 
				RecorridoAStarManual.of(graph,Ciudad.ofName("Sevilla"),Ciudad.ofName("Almeria"), null);
		List<Carretera> carreteras = ra.minPathToOrigin(Ciudad.ofName("Almeria")).getEdgeList();
		
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
		
		PrintWriter f2 = Files2.getWriter("ficheros/andaluciaAStar.gv");
		de2.exportGraph(graph, f2);
		
		System.out.println(ra.minPathToOrigin(Ciudad.ofName("Almeria")));
	}

}
