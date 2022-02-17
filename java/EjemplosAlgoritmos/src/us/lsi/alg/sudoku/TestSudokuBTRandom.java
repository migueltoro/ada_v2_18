package us.lsi.alg.sudoku;

import java.util.Locale;
import java.util.function.Predicate;

import us.lsi.alg.sudoku.DatosSudoku.SolucionSudoku;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.alg.BackTrackingRandom;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.SimpleVirtualGraph;
import us.lsi.graphs.virtual.EGraph;

public class TestSudokuBTRandom {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosSudoku.iniDatos("ficheros/sudoku2.txt");
		SudokuVertex e1 = SudokuVertex.first(DatosSudoku.sudoku);
		Predicate<SudokuVertex> goal = SudokuVertex.goal();
		
		EGraph<SudokuVertex,SimpleEdgeAction<SudokuVertex,Integer>> graph = 
				SimpleVirtualGraph.last(e1,goal,v->(double)v.sudoku().errores(),SudokuVertex.constraint());		
		
		BackTrackingRandom<SudokuVertex,SimpleEdgeAction<SudokuVertex,Integer>,SolucionSudoku> ms = BackTrackingRandom.of(
				graph,
				SudokuVertex::solucion,
				BTType.One,
				v->DatosSudoku.numeroDeCasillas-v.index());
		
		BackTrackingRandom.threshold = 15;
		BackTrackingRandom.solutionsNumber = 1;
		
		ms.search();
//		SudokuVertex lv = List2.last(path.getVertexList());
		System.out.println("Solucion = \n"+ms.getSolution());
		System.out.println("Iteraciones = \n"+ms.iterations);
	}

}
