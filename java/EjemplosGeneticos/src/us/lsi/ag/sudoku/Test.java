package us.lsi.ag.sudoku;

import us.lsi.sudoku.datos.DatosSudoku;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.sudoku.datos.Casilla;

public class Test {
	
	public static void main(String[] args) {
		
		DatosSudoku.tamSubCuadro = 3;
		DatosSudoku.iniDatos("ficheros/sudoku.txt");
		List<Casilla> casillasLibres = DatosSudoku.casillasLibres();
		Integer n = casillasLibres.size();
		System.out.println(n);
		System.out.println(IntStream.range(0,n).boxed()
				.map(index->DatosSudoku.getValoresLibresEnCasilla(DatosSudoku.getCasillaLibre(index)))
				.map(e->e.toString())
				.collect(Collectors.toList()));
		
	}

}
