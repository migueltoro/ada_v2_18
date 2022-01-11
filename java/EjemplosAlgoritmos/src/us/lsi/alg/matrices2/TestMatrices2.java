package us.lsi.alg.matrices2;

import java.util.Locale;
import java.util.stream.Collectors;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import us.lsi.common.String2;
import us.lsi.common.Union;
import us.lsi.hypergraphs2.Datos;
import us.lsi.hypergraphs2.GraphTree2;


public class TestMatrices2 {

		public static void main(String[] args) {
			Locale.setDefault(new Locale("en", "US"));
			Auxiliar2.leeFichero("./ficheros/matrices.txt");
			MatrixVertex2 p = MatrixVertex2.initial();
			String s = p.solution();
			String2.toConsole(s);
			GraphTree2<MatrixVertex2, MatrixEdge2, Integer, String> t = p.graphTree();
			String2.toConsole(t.toString());
			String2.toConsole(t.string());
			
			SimpleDirectedGraph<Union<MatrixVertex2, MatrixEdge2>, DefaultEdge> g = p.datos().graph();
			
			Datos.toDotHypergraph(g, "ficheros/hiperMatrix.gv", p);
			Datos.toDotAndOr(g, "ficheros/andOrMatrix.gv", p);
		}

}
