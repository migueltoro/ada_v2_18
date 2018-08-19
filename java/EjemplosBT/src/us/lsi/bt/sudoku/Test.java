package us.lsi.bt.sudoku;

import java.util.stream.Collectors;

public class Test {
	


	public static void main(String[] args) {
		DatosSudoku.iniDatos("ficheros/sudoku.txt");
		SolucionSudoku s = SolucionSudoku.create();
		System.out.println(s);
		Casilla c = DatosSudoku.get(2,0);
		System.out.println(DatosSudoku.getValoresLibresEnCasilla(c));	
		String format = "(%d,%d),%d";
		String s2 = imprimeLibres(format);
		System.out.println(s2);
		DatosSudoku.ordena(0);
		s2 = imprimeLibres(format);
		System.out.println(s2);
	}

	/**
	 * @param format
	 * @return
	 */
	private static String imprimeLibres(String format) {
		return DatosSudoku.getCasillasLibres()
							  .stream()
							  .map(x->String.format(format,
									  x.getX(),
									  x.getY(),
									  DatosSudoku.getValoresLibresEnCasilla(x).size()))
							  .collect(Collectors.joining("\n","_______\n","\n_______\n"));
	}

}
