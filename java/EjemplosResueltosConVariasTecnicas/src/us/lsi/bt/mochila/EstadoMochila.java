package us.lsi.bt.mochila;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.bt.EstadoBT;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;


public class EstadoMochila implements 
	EstadoBT<SolucionMochila, Integer, EstadoMochila> {

	public static EstadoMochila createInitial() {
		return new EstadoMochila(0,SolucionMochila.create(), 
				0,DatosMochila.capacidadInicial, 0);
	}

	private Integer index;
	private SolucionMochila s;
	private Integer pesoAcumulado;
	private Integer capacidadRestante;
	private Integer valorAcumulado;
	
	private EstadoMochila(Integer index, SolucionMochila s, Integer pesoAcumulado, Integer capacidadRestante,
			Integer valorAcumulado) {
		super();
		this.index = index;
		this.s = s;
		this.pesoAcumulado = pesoAcumulado;
		this.capacidadRestante = capacidadRestante;
		this.valorAcumulado = valorAcumulado;
	}
	
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public Integer getPesoAcumulado() {
		return pesoAcumulado;
	}
	public void setPesoAcumulado(Integer pesoAcumulado) {
		this.pesoAcumulado = pesoAcumulado;
	}
	public Integer getCapacidadRestante() {
		return capacidadRestante;
	}
	public Integer getValorAcumulado() {
		return valorAcumulado;
	}
	@Override
	public Tipo getTipo() {
		return Tipo.Max;
	}
	@Override
	public EstadoMochila getEstadoInicial() {
		return EstadoMochila.createInitial();
	}
	@Override
	public EstadoMochila avanza(Integer a) {
		this.pesoAcumulado = this.pesoAcumulado+a*DatosMochila.getPeso(this.index);
		this.capacidadRestante = this.capacidadRestante-a*DatosMochila.getPeso(this.index);;
		this.valorAcumulado = this.valorAcumulado+a*DatosMochila.getValor(this.index);
		this.s.add(DatosMochila.getObjeto(this.index),a);
		this.index = this.index+1;
		return this;
	}
	@Override
	public EstadoMochila retrocede(Integer a) {
		this.index = this.index-1;
		this.pesoAcumulado = this.pesoAcumulado-a*DatosMochila.getPeso(this.index);
		this.capacidadRestante = this.capacidadRestante+a*DatosMochila.getPeso(this.index);;
		this.valorAcumulado = this.valorAcumulado-a*DatosMochila.getValor(this.index);
		this.s.remove(DatosMochila.getObjeto(this.index),a);
		return this;
	}
	@Override
	public int size() {
		return DatosMochila.numeroDeObjetos-index;
	}	
	@Override
	public List<Integer> getAlternativas() {
		Integer r = DatosMochila.getNumUnidadesPosibles(index, capacidadRestante);
		List<Integer> ls = IntStream.rangeClosed(0, r)
				.boxed()
				.collect(Collectors.toList());
		Collections.reverse(ls);
		return ls;
	}	
	@Override
	public boolean esCasoBase() {
		return this.index == DatosMochila.numeroDeObjetos;
	}	
	@Override
	public Double getObjetivoEstimado(Integer a) {	
		return (double) this.valorAcumulado+ this.getCotaSuperiorValorEstimado(a);
	}
	@Override
	public Double getObjetivo() {
		return (double) this.valorAcumulado;
	}	
	/**
	 * @pre a está contenida en getAlternativas()
	 * @param a Una alternativa de this
	 * @return Una cota superior del valor de la solución del problema this si se escoge la alternativa a
	 */
	public Integer getCotaSuperiorValorEstimado(Integer a){
		Double r = (double) a*DatosMochila.getValor(index);
		Integer capacidadRestante = this.capacidadRestante - a*DatosMochila.getPeso(index);
		r = r + DatosMochila.getCotaSuperior(this.index+1,capacidadRestante);
		return (int)Math.ceil(r);
	}
	@Override
	public SolucionMochila getSolucion() {
		return s.copy();
	}

}
