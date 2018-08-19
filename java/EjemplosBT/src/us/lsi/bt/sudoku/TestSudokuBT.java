package us.lsi.bt.sudoku;

import us.lsi.bt.AlgoritmoBT;

public class TestSudokuBT{

	

	public static void main(String[] args) {
		
		AlgoritmoBT.isRandomize = false;
		AlgoritmoBT.numeroDeSoluciones = 4;
		DatosSudoku.tamSubCuadro = 3;
		DatosSudoku.iniDatos("ficheros/sudoku.txt");
		EstadoSudoku e = EstadoSudoku.createInitial();
		var a = AlgoritmoBT.create(e);
		
		a.ejecuta();

		System.out.println("Num de Soluciones = "+a.getSoluciones().size()+"\n\n");
		SolucionSudoku c = a.getSolucion();
		System.out.println(c);
//		Sudoku s =  new Sudoku();
//		
//
//		int[] nPresetLoc = new int[81];		
//		IntStream.range(0,81).boxed().forEach(x->nPresetLoc[x]=x);
//		s.presetLoc = nPresetLoc;
//		s.presetInts = c.getValores();
//		try {
//			s.run();
//		} catch (IOException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}


	}

}
