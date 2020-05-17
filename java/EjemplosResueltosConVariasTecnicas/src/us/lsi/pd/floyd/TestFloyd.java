package us.lsi.pd.floyd;

import java.util.stream.Collectors;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphWalk;

import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.views.IntegerMappingGraphView;
import us.lsi.pd.AlgoritmoPD;
import us.lsi.pd.floyd.FloydPD.Alternativa;




public class TestFloyd {

	public static void main(String[] args) {
				
		GrafoDelMapa g = GrafoDelMapa.create("./ficheros/andalucia.txt");
			
		System.out.println(g.getGraph());
		
		IntegerMappingGraphView<Ciudad,Carretera> gv = IntegerMappingGraphView.of(g.getGraph());
		
		int origen = gv.getIndex(Ciudad.ofName("Cadiz"));
		int destino = gv.getIndex(Ciudad.ofName("Almeria"));
		FloydPD<Ciudad,Carretera> p = FloydPD.create(origen,destino,gv);
		AlgoritmoPD<GraphWalk<Integer, Carretera>, Alternativa, FloydPD<Ciudad, Carretera>> a = AlgoritmoPD.createPD(p);
				
		a.ejecuta();
		
		GraphPath<Integer,Carretera> s = a.getSolucion();
		
		a.showAllGraph("ficheros/solucionMapa.gv", "SolucionMapa");
		
		System.out.println(s.getVertexList().stream().map(x->gv.getVertex(x)).collect(Collectors.toList()));
		
		a.getAlternativasDeSolucion(p).toDOT("ficheros/alternativasMapa.gv", "Alternativas");
		
	}
}
