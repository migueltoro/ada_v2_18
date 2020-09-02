package us.lsi.geneticosbt.sudoku;

import java.util.stream.Collectors;

import us.lsi.common.Strings2;
import us.lsi.sudoku.datos.DatosSudoku;
import us.lsi.sudoku.datos.SolucionSudoku;

public class Test2 {

	public static void main(String[] args) {
		DatosSudoku.tamSubCuadro = 3;
		DatosSudoku.iniDatos("ficheros/sudoku.txt");
		System.out.println(DatosSudoku.casillasLibres().size());
		System.out.println(DatosSudoku.casillasLibres().stream().allMatch(c->c.isFree()));
		Strings2.toConsole(DatosSudoku.casillasLibres(), "Casillas Libres");
		SolucionSudoku s = SolucionSudoku.of();
		Strings2.toConsole(DatosSudoku.casillasLibres()
				.stream()
				.map(c->Par.of(s.getValoresLibresEnCasilla(s.getCasilla(c)), DatosSudoku.getValoresLibresEnCasilla(c)))
				.collect(Collectors.toList())
				, "Valores Libres en Solucion");
		
//		var p = new ProblemaSudokuAGBT();
	}

	public static class Par{
		Object p1;
		Object p2;
		public static Par of(Object p1, Object p2) {
			return new Par(p1,p2);
		}
		public Par(Object p1, Object p2) {
			super();
			this.p1 = p1;
			this.p2 = p2;
		}
		@Override
		public String toString() {
			return "(" + p1 + ", " + p2 + ")";
		}
		
	}
}
