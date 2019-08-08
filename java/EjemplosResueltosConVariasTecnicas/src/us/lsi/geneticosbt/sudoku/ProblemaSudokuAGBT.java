package us.lsi.geneticosbt.sudoku;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.ag.ValuesInSetProblemAG;
import us.lsi.ag.agchromosomes.ValuesInSetChromosome;
import us.lsi.sudoku.datos.Casilla;
import us.lsi.sudoku.datos.DatosSudoku;
import us.lsi.sudoku.datos.SolucionSudoku;

public class ProblemaSudokuAGBT implements ValuesInSetProblemAG<SolucionSudoku> {
	
	public static Integer ns = 0;
	
	public static SolucionSudoku btSudoku(int index, Integer n, SolucionSudoku solucion) {
		SolucionSudoku r = null;	
		if(index == n) {
			r =  solucion.copy();
			if(r.getErrores() > 0) r = null;
			ns++;
		} else {
			List<Integer> alternativas = solucion.getValoresLibresEnCasilla(solucion.getCasillaLibre(index));
			for(Integer a:alternativas) {
				solucion.getCasillaLibre(index).setValue(a);
				r = btSudoku(index+1, n, solucion);	
				if(r!=null) break;
				solucion.getCasillaLibre(index).setFree();
			}
		}
		return r;
	}

	List<Casilla> casillasLibres;
	Integer n; 
	Map<Integer,List<Integer>> values = new HashMap<>();
	Integer ncb = 15;
	
	public ProblemaSudokuAGBT() {
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
		Double r = -1000.;
		if(s!=null) r = -(double)s.getErrores();
		return r;
	}
	
	@Override
	public SolucionSudoku getSolucion(ValuesInSetChromosome cr) {
		List<Integer> dc = cr.decode();	
		SolucionSudoku s = SolucionSudoku.of(dc.subList(0, n-ncb));
		s = btSudoku(n-ncb,DatosSudoku.casillasLibres().size(),s);	
		if(s==null) s = SolucionSudoku.of(dc);
		return s;
	}
	

}
