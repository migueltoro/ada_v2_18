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

	private Graph<Ciudad,Carretera> grafo;		
	
	private GrafoDelMapa(String fichero) {
		super();
		leeDatos(fichero);		
	}

	public void leeDatos(String fichero){
		this.grafo = GraphsReader.newGraph(fichero, 
				Ciudad::create,
				Carretera::create,
				()->new SimpleWeightedGraph<Ciudad,Carretera>(null,null),
				Carretera::getKm);			
		for(Carretera c: grafo.edgeSet()){
			grafo.setEdgeWeight(c, c.getKm());
		}		
	}

	public Graph<Ciudad, Carretera> getGrafo() {
		return grafo;
	}	
	
}
