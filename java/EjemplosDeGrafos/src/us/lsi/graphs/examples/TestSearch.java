package us.lsi.graphs.examples;

import org.jgrapht.Graph;

import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.search.GreedySearchOnGraph;
import us.lsi.graphs.search.Search;

public class TestSearch {

	public static void main(String[] args) {
		Graph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::getKm);
		Search<Ciudad, Carretera> ba = Search.breadth(graph,Ciudad.ofName("Sevilla"));		
		ba.find(x->x.equals(Ciudad.ofName("Almeria")));		
		System.out.println("Anchura --------------");
		var gpa = ba.pathToOrigin(Ciudad.ofName("Almeria"));
		System.out.println(gpa);
		System.out.println(gpa.getLength());
		Search<Ciudad, Carretera> bp = Search.depth(graph,Ciudad.ofName("Sevilla"));
		System.out.println("Profundidad --------------");	
		bp.find(x->x.equals(Ciudad.ofName("Almeria")));
		var gpp = bp.pathToOrigin(Ciudad.ofName("Almeria"));
		System.out.println(gpp);
		System.out.println(gpp.getLength());
		System.out.println("Dijsktra --------------");
		Search<Ciudad, Carretera> bd = Search.dijsktra(graph,Ciudad.ofName("Sevilla"));			
		bd.find(x->x.equals(Ciudad.ofName("Almeria")));	
		var gpd = bd.pathToOrigin(Ciudad.ofName("Almeria"));
		System.out.println(gpd);
		System.out.println(gpd.getWeight());
		System.out.println("Greedy --------------");	
		GreedySearchOnGraph<Ciudad, Carretera> bg = (GreedySearchOnGraph<Ciudad, Carretera>)
				Search.greedy(graph,Ciudad.ofName("Sevilla"),v->Graphs2.closestVertex(graph,v));
		bg.stream().forEach(c->System.out.println(c));
		System.out.println(bg.edgeToOrigin);
		var gpg = bg.pathFromOrigin(Ciudad.ofName("Almeria"));
		System.out.println(gpg);
		System.out.println(gpg.getWeight());
	}

}
