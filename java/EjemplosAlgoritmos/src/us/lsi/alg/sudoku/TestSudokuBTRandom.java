package us.lsi.alg.sudoku;

import java.util.Locale;
import java.util.function.Predicate;

import us.lsi.alg.sudoku.DatosSudoku.SolucionSudoku;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.alg.BackTrackingRandom;
import us.lsi.graphs.virtual.ActionSimpleEdge;
import us.lsi.graphs.virtual.EGraph;

public class TestSudokuBTRandom {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosSudoku.iniDatos("ficheros/sudoku2.txt");
		SudokuVertex e1 = SudokuVertex.first(DatosSudoku.sudoku);
		Predicate<SudokuVertex> goal = SudokuVertex.goal();
		Predicate<SudokuVertex> constraint = SudokuVertex.constraint();
		EGraph<SudokuVertex,ActionSimpleEdge<SudokuVertex,Integer>> graph = 
				Graphs2.simpleVirtualGraphLast(e1,goal,null,constraint,v->(double)v.sudoku().errores());		
		
		BackTrackingRandom<SudokuVertex,ActionSimpleEdge<SudokuVertex,Integer>,SolucionSudoku> ms = BT.random(
				graph,
				SudokuVertex::solucion,
				SudokuVertex::copy,
				BTType.Min,
				v->DatosSudoku.numeroDeCasillas-v.index());
		
		BackTrackingRandom.threshold = 15;
		BackTrackingRandom.solutionsNumber = 1;
		
		ms.search();
//		SudokuVertex lv = List2.last(path.getVertexList());
		System.out.println("Solucion = \n"+ms.getSolution());
		System.out.println("Iteraciones = \n"+ms.iterations);
	}

}
