package us.lsi.pd.floyd;

import org.jgrapht.GraphPath;

import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.GraphView;
import us.lsi.pd.AlgoritmoPD;




public class TestFloyd {

	public static void main(String[] args) {
				
		GrafoDelMapa g = GrafoDelMapa.create("./ficheros/andalucia.txt");
			
		System.out.println(g.getGrafo());
		
		GraphView<Ciudad,Carretera> gv = GraphView.create(g.getGrafo());
		
		int origen = gv.getIndex(Ciudad.create("Cadiz"));
		int destino = gv.getIndex(Ciudad.create("Almeria"));
		FloydPD<Ciudad,Carretera> p = FloydPD.create(origen,destino,gv);
		var a = AlgoritmoPD.createPD(p);
				
		a.ejecuta();
		
		GraphPath<Ciudad,Carretera> s = a.getSolucion();
		
		a.showAllGraph("ficheros/solucionMapa.gv", "SolucionMapa");
		
		System.out.println(s.getVertexList());
		
		a.getAlternativasDeSolucion(p).toDOT("ficheros/alternativasMapa.gv", "Alternativas");
		
	}
}
