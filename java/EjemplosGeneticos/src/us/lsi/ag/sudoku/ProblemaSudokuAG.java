package us.lsi.ag.sudoku;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.ag.ValuesInRangeChromosome;
import us.lsi.ag.ValuesInRangeProblemAG;
import us.lsi.sudoku.datos.Casilla;
import us.lsi.sudoku.datos.DatosSudoku;
import us.lsi.sudoku.datos.SolucionSudoku;


public class ProblemaSudokuAG 
	implements ValuesInRangeProblemAG<Integer,SolucionSudoku> {

	List<Casilla> casillasLibres;
	Integer n; 
	List<List<Integer>> valoresPosibles;
		
	public ProblemaSudokuAG() {
		super();
		this.casillasLibres = DatosSudoku.getCasillasLibres();
		this.n = casillasLibres.size();
		this.valoresPosibles = 
				casillasLibres
				.stream()
				.map(c->DatosSudoku.getValoresLibresEnCasilla(c))
				.collect(Collectors.toList());
	}

	@Override
	public Integer getVariableNumber() {
		return casillasLibres.size();
	}

	@Override
	public Integer getMax(Integer i) {
		return valoresPosibles.get(i).size()-1;
	}

	@Override
	public Integer getMin(Integer i) {
		return 0;
	}
	
	@Override
	public Double fitnessFunction(ValuesInRangeChromosome<Integer> cr) {
		SolucionSudoku s = SolucionSudoku.create();
		return -(double)s.getErrores();
	}

	private Integer valorPosible(Integer i, Integer a) {
		return valoresPosibles.get(i).get(a);
	}
	
	private void setValorEnCasilla(Integer ic, Integer a) {
		Integer r = valorPosible(ic, a);
		DatosSudoku.getCasillaLibre(ic).setValue(r);
	}
	
	@Override
	public SolucionSudoku getSolucion(ValuesInRangeChromosome<Integer> cr) {
		List<Integer> dc = cr.decode();
		IntStream.range(0,n)
			.forEach(i->setValorEnCasilla(i,dc.get(i)));		
		return SolucionSudoku.create();
	}

}
