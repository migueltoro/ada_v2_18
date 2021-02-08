package us.lsi.alg.matrices;

import java.util.Locale;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.DP;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.hypergraphs.GraphTree;
import us.lsi.hypergraphs.SimpleVirtualHyperGraph;

public class TestMatricesPD {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		Auxiliar.leeFichero("./ficheros/matrices_2.txt");
		
		MatrixVertex initial = MatrixVertex.initial();
		
		SimpleVirtualHyperGraph<MatrixVertex,MatrixEdge,Integer> graph = 
				Graphs2.simpleVirtualHyperGraph(initial);
		
		DP<MatrixVertex,MatrixEdge,Integer> a = DP.dynamicProgrammingSearch(graph,PDType.Min);
		
		a.search();
		
		GraphTree<MatrixVertex, MatrixEdge, Integer> tree = a.searchTree(initial);
		
		System.out.println(tree);
		
		System.out.println(Auxiliar.solucion(tree));
	}

}
