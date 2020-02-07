package us.lsi.pd.floyd;


import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.GraphsReader;


import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleWeightedGraph;

public class GrafoDelMapa {

	
	public static GrafoDelMapa create(String fichero) {
		return new GrafoDelMapa(fichero);
	}

	SimpleWeightedGraph<Ciudad, Carretera> graph;		
	
	private GrafoDelMapa(String fichero) {
		super();
		leeDatos(fichero);		
	}

	public void leeDatos(String fichero) {
		graph = GraphsReader.newGraph("ficheros/andalucia.txt", 
				Ciudad::ofFormat, 
				Carretera::ofFormat,
				() -> new SimpleWeightedGraph<Ciudad, Carretera>(Ciudad::of, Carretera::of), 
				Carretera::getKm);
		for (Carretera c : graph.edgeSet()) {
			graph.setEdgeWeight(c, c.getKm());
		}
	}

	public Graph<Ciudad, Carretera> getGraph() {
		return graph;
	}	
	
}
