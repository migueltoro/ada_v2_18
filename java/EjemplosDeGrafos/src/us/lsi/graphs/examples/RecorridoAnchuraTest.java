package us.lsi.graphs.examples;

import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.IntegerComponentNameProvider;

import us.lsi.common.Files2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphcolors.GraphColors;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.BreadthSearch;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.manual.RecorridoEnAnchuraManual;

public class RecorridoAnchuraTest {
	
	public static void main(String[] args) {
		Graph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::getKm);
		
		graph.addVertex(Ciudad.ofName("Londres"));
		
		RecorridoEnAnchuraManual<Ciudad,Carretera> ra = RecorridoEnAnchuraManual.of(graph,Ciudad.ofName("Sevilla"));
		Set<Ciudad> ciudades = ra.graph.vertexSet();
		Set<Carretera> setAnchura = ciudades.stream().map(v->ra.tree(v)).filter(x->x!=null)
				.collect(Collectors.toSet());
		
		DOTExporter<Ciudad,Carretera> de1 = new DOTExporter<Ciudad,Carretera>(
				new IntegerComponentNameProvider<>(),
				x->x.getNombre(), 
				x->x.getNombre()+"--"+x.getKm());
		
		DOTExporter<Ciudad,Carretera> de2 = new DOTExporter<Ciudad,Carretera>(
				new IntegerComponentNameProvider<>(),
				x->x.getNombre()+"/"+ra.position(x)+"/"+ra.level(x), 
				x->""+x.getKm(),
				null,
				e->GraphColors.getStyleIf("bold",e,x->setAnchura.contains(x)));
		
		PrintWriter f1 = Files2.getWriter("ficheros/andalucia.gv");
		de1.exportGraph(graph, f1);
		
		PrintWriter f2 = Files2.getWriter("ficheros/andaluciaAnchura.gv");
		de2.exportGraph(graph, f2);

		BreadthSearch<Ciudad,Carretera> ba = BreadthSearch.of(graph,List.of(Ciudad.ofName("Sevilla"),Ciudad.ofName("Londres")));		
		ba.stream().forEach(c->System.out.println(c));	
		System.out.println(ba.getDepth(Ciudad.ofName("Londres")));
	}


}
