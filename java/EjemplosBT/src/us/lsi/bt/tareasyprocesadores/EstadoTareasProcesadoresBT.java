package us.lsi.bt.tareasyprocesadores;

import java.util.Comparator;
import java.util.List;

import us.lsi.bt.EstadoBT;
import us.lsi.tareasyprocesadores.datos.SolucionTareasProcesadores;
import us.lsi.tareasyprocesadores.datos.Tarea;

import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class EstadoTareasProcesadoresBT implements 
	EstadoBT<SolucionTareasProcesadores, Integer, EstadoTareasProcesadoresBT> {

	public static Integer numeroDeProcesadores;
	public static Integer numeroDeTareas;
	public static EstadoTareasProcesadoresBT inicial;
	
	public static EstadoTareasProcesadoresBT create(String fichero, Integer np) {
		Tarea.leeTareas(fichero);
		numeroDeProcesadores = np;
		numeroDeTareas = Tarea.tareas.size();
		inicial = new EstadoTareasProcesadoresBT(fichero, np);
		return inicial;
	}
	
	private int index;
	private SolucionTareasProcesadores solucion;
	
	private EstadoTareasProcesadoresBT(String fichero, Integer np){
		Tarea.leeTareas(fichero);
		numeroDeProcesadores = np;
		numeroDeTareas = Tarea.tareas.size();
		this.index = 0;		
		this.solucion = SolucionTareasProcesadores.create(np);
	}

	@Override
	public Tipo getTipo() {
		return Tipo.Min;
	}

	@Override
	public int size() {
		return numeroDeTareas - index;
	}

	@Override
	public EstadoTareasProcesadoresBT getEstadoInicial() {
		return inicial; 
	}
	
	@Override
	public boolean esCasoBase() {
		return index == numeroDeTareas;
	}
	
	@Override
	public SolucionTareasProcesadores getSolucion() {
		return solucion.copy();
	}

	@Override
	public List<Integer> getAlternativas() {
		return IntStream.range(0,numeroDeProcesadores)
				.boxed()
				.sorted(Comparator.<Integer,Double>comparing(x->solucion.getCargaProcesador(x)))
				.collect(Collectors.toList());
	}
	
	@Override
	public Double getObjetivoEstimado(Integer a){
		return this.solucion.nuevoObjetivo(a, index);
	}

	@Override
	public Double getObjetivo() {
		return this.solucion.getObjetivo();
	}
	
	@Override
	public EstadoTareasProcesadoresBT avanza(Integer a) {
		this.solucion.addTareaAProcesador(a, index);
		this.index = index+1;
		return this;
	}

	@Override
	public EstadoTareasProcesadoresBT retrocede(Integer a) {
		this.index = index-1;
		this.solucion.removeTareaAProcesador(a, this.index);		
		return this;
	}
	
	@Override
	public String toString() {
		return "(" + index
				+ "," + solucion
				+ ")";
	}

	
}
