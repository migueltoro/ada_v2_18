package us.lsi.ag.sudoku;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import us.lsi.ag.ValuesInSetProblemAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.agchromosomes.ValuesInSetChromosome;
import us.lsi.sudoku.datos.Casilla;
import us.lsi.sudoku.datos.DatosSudoku;
import us.lsi.sudoku.datos.SolucionSudoku;

public class ProblemaSudokuAG implements ValuesInSetProblemAG<SolucionSudoku> {

	List<Casilla> casillasLibres;
	Integer n; 
	Map<Integer,List<Integer>> values = new HashMap<>();
	
	public ProblemaSudokuAG() {	
		super();
		this.casillasLibres = DatosSudoku.casillasLibres();		  
		this.n = casillasLibres.size();
	}
	
	@Override
	public Integer getVariableNumber() {
		return n;
	}

	@Override
	public List<Integer> values(Integer index) {
		if(!values.containsKey(index)) {
			List<Integer> r = DatosSudoku.getValoresLibresEnCasilla(DatosSudoku.getCasillaLibre(index));
			values.put(index, r);
		}
		return  values.get(index);
	}

	@Override
	public Double fitnessFunction(ValuesInSetChromosome cr) {
		SolucionSudoku s = getSolucion(cr);
		return -(double)s.getErrores();
	}

	private void setValorEnCasilla(Integer ic, Integer a) {
		DatosSudoku.getCasillaLibre(ic).setValue(a);
	}
	
	@Override
	public SolucionSudoku getSolucion(ValuesInSetChromosome cr) {
		List<Integer> dc = cr.decode();
		IntStream.range(0,n).forEach(i->setValorEnCasilla(i,dc.get(i)));	
		SolucionSudoku s = SolucionSudoku.of();
		return s;
	}

	@Override
	public ChromosomeType getType() {
		return ChromosomeType.InSet;
	}

}
