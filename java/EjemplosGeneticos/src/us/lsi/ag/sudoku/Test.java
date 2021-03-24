package us.lsi.ag.sudoku;

import us.lsi.sudoku.datos.DatosSudoku;
import us.lsi.sudoku.datos.SolucionSudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.IntPair;
import us.lsi.common.Pair;
import us.lsi.sudoku.datos.Casilla;

public class Test {
	
	public static void main(String[] args) {
		
		DatosSudoku.tamSubCuadro = 3;
		DatosSudoku.iniDatos("ficheros/sudoku.txt");
		Integer n = DatosSudoku.casillasLibres().size();
		System.out.println(n);
		System.out.println(DatosSudoku.casillasLibres());
		System.out.println(DatosSudoku.getValoresLibresEnCasilla(DatosSudoku.get(2, 2)));
		System.out.println(DatosSudoku.getValoresLibresEnCasilla(DatosSudoku.get(2, 8)));
		System.out.println(SolucionSudoku.of());
		List<Integer> initialValues = new ArrayList<Integer>();
		List<Integer> blocksLimits = new ArrayList<Integer>();
		Integer limits = 0;
		List<Casilla> casillasLibres = new ArrayList<Casilla>();
		for(int j=8; j>=0;j--) {	
			Set<Integer> s0 = new HashSet<>();
			for(int i=0; i<9;i++) {
				Casilla c = DatosSudoku.get(i, j);			
				if(c.isFree()) {
					s0.addAll(DatosSudoku.getValoresLibresEnCasilla(c));
					casillasLibres.add(c);
				}
			}
			initialValues.addAll(s0);
			if(s0.size()>0 && j != 0) {
				limits += s0.size();
				blocksLimits.add(limits);
			}
			System.out.printf("Fila = %d %s\n",j,s0);
		}
		
//		Collections.sort(DatosSudoku.casillasLibres,Comparator.comparing(c->c.getY()));
		System.out.println(DatosSudoku.casillasLibres().stream().map(c->c.getString()).collect(Collectors.toList()));
		System.out.println(DatosSudoku.casillasLibres().size());
		System.out.println(initialValues);
		System.out.println(initialValues.size());
		System.out.println(blocksLimits);
		System.out.println(blocksLimits.size());
		
	}

}
