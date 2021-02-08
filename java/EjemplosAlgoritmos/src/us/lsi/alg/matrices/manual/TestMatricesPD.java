package us.lsi.alg.matrices.manual;

import java.util.Locale;

import us.lsi.alg.matrices.Auxiliar;
import us.lsi.alg.matrices.MatrixVertex;

public class TestMatricesPD {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		Auxiliar.leeFichero("./ficheros/matrices.txt");
		
		MatrixVertex initial = MatrixVertex.initial();
		
		MatricesPD a = MatricesPD.of(initial);
		
//		a.search();
		
//		GraphTree<MatrixVertex, MatrixEdge, Integer> tree = a.searchTree(initial);
		
		System.out.println(a.search());
		
//		System.out.println(Auxiliar.solucion(tree));

	}

}
