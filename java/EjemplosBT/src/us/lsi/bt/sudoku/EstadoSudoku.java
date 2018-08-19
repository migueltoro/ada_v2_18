package us.lsi.bt.sudoku;

import java.util.List;
import us.lsi.bt.EstadoBT;

public class EstadoSudoku implements 
	EstadoBT<SolucionSudoku, Integer, EstadoSudoku> {


	public static EstadoSudoku createInitial() {
		return new EstadoSudoku(0);
	}
	
	private int index;
		
	private EstadoSudoku(Integer index) {
		super();
		this.index = index;
	}
	
	@Override
	public Tipo getTipo() {
		return Tipo.TodasLasSoluciones;
	}

	@Override
	public EstadoSudoku getEstadoInicial() {
		return EstadoSudoku.createInitial();
	}

	
	@Override
	public EstadoSudoku avanza(Integer a) {
		DatosSudoku.getCasillaLibre(this.index).setValue(a);
		this.index = this.index+1;	
		DatosSudoku.ordena(index);
		return this;
	}

	@Override
	public EstadoSudoku retrocede(Integer a) {
		this.index = this.index-1;
		DatosSudoku.getCasillaLibre(this.index).setFree();
		return this;
	}

	@Override
	public int size() {
		return DatosSudoku.getCasillasLibres().size()-this.index;
	}

	@Override
	public boolean esCasoBase() {
		return DatosSudoku.getCasillasLibres().size()== this.index;
	}

	@Override
	public List<Integer> getAlternativas() {
		Casilla c = DatosSudoku.getCasillasLibres().get(index);
		return DatosSudoku.getValoresLibresEnCasilla(c);
	}

	@Override
	public SolucionSudoku getSolucion() {
		return SolucionSudoku.create();
	}		
}
