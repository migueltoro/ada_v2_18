package us.lsi.bt.sudoku;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Streams2;

public class SolucionSudoku {
	
	private Casilla[][] casillas;
	private Integer errores;
	
	public static SolucionSudoku create() {
		return new SolucionSudoku();
	}

	private SolucionSudoku() {
		super();
		Integer n = DatosSudoku.numeroDeFilas;
		this.casillas = new Casilla[n][n];
		DatosSudoku.getCasillas()
			.stream()
			.forEach(c->casillas[c.getX()][c.getY()] = c.copy());
		this.errores = calculaErrores();
	}	

	private Integer calculaErrores() {
		Integer r = 0;
		Integer n = DatosSudoku.numeroDeFilas;
		r = r+IntStream.range(0, n)
			.map(y->n-DatosSudoku.getValoresOcupadosEnFila(y).size())
			.sum();
		r = r+IntStream.range(0, n)
			.map(x->n-DatosSudoku.getValoresOcupadosEnColumna(x).size())
			.sum();
		r = r+IntStream.range(0, n)
			.map(sc->n-DatosSudoku.getValoresOcupadosEnSubCuadro(sc).size())
			.sum();
		return r;
	}

	private String fila(Integer y) {
		return IntStream.range(0,DatosSudoku.numeroDeFilas)
					.boxed()
					.map(x->casillas[x][y].getStringValue())
					.collect(Collectors.joining(" "));
	}
	@Override
	public String toString() {
		return Streams2.range(DatosSudoku.numeroDeFilas-1, -1, -1)
			    .boxed()
			    .map(y->fila(y))
			    .collect(Collectors.joining("\n",
			    		"Errores = "+this.errores+"\n\n","\n__________\n"));
	}

	public int[] getValores() {
		int[] values = new int[81];
		int k = 0;
		for(int y = DatosSudoku.numeroDeFilas-1; y > -1;y = y -1) {
			for(int x = 0; x < DatosSudoku.numeroDeFilas;x = x+1) {
				values[k]= casillas[x][y].getValue();
				k++;
			}
		}
		return values;
	}
	
	public Integer getErrores() {
		return errores;
	}

	public Casilla getCasilla(Integer x, Integer y) {
		return casillas[x][y];
	}
	

}
