package us.lsi.pd.mochila;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;
import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.AlgoritmoPD.Tipo;
import us.lsi.pd.ProblemaPDR;


public class ProblemaMochilaPD implements ProblemaPDR<SolucionMochila, Integer, ProblemaMochilaPD> {	
	
	public static ProblemaMochilaPD createInitial() {	
		return new ProblemaMochilaPD(0, 0, DatosMochila.capacidadInicial,0);
	}
	
	public static ProblemaMochilaPD create(ProblemaMochilaPD p, Integer a) {
		return new ProblemaMochilaPD(p, a);
	}

	private int index;	
	private Integer pesoAcumulado;
	private Integer capacidadRestante;
	private Integer valorAcumulado;
	
	
	private ProblemaMochilaPD(int index, int pesoAcumulado, int capacidadRestante, int valorAcumulado) {
		this.index = index;
		this.pesoAcumulado = pesoAcumulado;
		this.capacidadRestante = capacidadRestante;
		this.valorAcumulado = valorAcumulado;
	}
	
	private ProblemaMochilaPD(ProblemaMochilaPD p, Integer a) {
		this.pesoAcumulado = p.pesoAcumulado+a*DatosMochila.getPeso(p.index);
		this.capacidadRestante = p.capacidadRestante-a*DatosMochila.getPeso(p.index);;
		this.valorAcumulado = p.valorAcumulado+a*DatosMochila.getValor(p.index);
		this.index = p.index+1;
	}		
	
	@Override
	public Tipo getTipo(){
		return Tipo.Max;
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
	public Sp<Integer> getSolucionParcialCasoBase() {
		return Sp.create(null, 0.);
	}

	@Override
	public ProblemaMochilaPD getSubProblema(Integer a) {		
		return ProblemaMochilaPD.create(this, a);
	}

	@Override
	public Sp<Integer> getSolucionParcialPorAlternativa(Integer a, Sp<Integer> r) {
		Double valor = a*DatosMochila.getValor(index)+r.propiedad;
		return Sp.create(a, valor);
	}

	@Override
	public SolucionMochila getSolucionReconstruidaCasoBase(Sp<Integer> sp) {
		return SolucionMochila.create();
	}

	@Override
	public SolucionMochila getSolucionReconstruidaCasoRecursivo(Sp<Integer> sp, SolucionMochila s) {
		s.add(DatosMochila.getObjeto(this.index), sp.alternativa);
		return s;
	}

	
	@Override
	public Double getObjetivoEstimado(Integer a) {	
		return (double) this.valorAcumulado+ this.getCotaSuperiorValorEstimado(a);
	}

	@Override
	public Double getObjetivo() {
		Double r = null;
		if(esCasoBase()) r = (double) this.valorAcumulado;
		return r;
	}
	
	public int getIndex() {
		return index;
	}

	public Integer getPesoAcumulado() {
		return pesoAcumulado;
	}

	public Integer getCapacidadRestante() {
		return capacidadRestante;
	}

	public Integer getValorAcumulado() {
		return valorAcumulado;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((capacidadRestante == null) ? 0 : capacidadRestante
						.hashCode());
		result = prime * result + index;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProblemaMochilaPD))
			return false;
		ProblemaMochilaPD other = (ProblemaMochilaPD) obj;
		if (capacidadRestante == null) {
			if (other.capacidadRestante != null)
				return false;
		} else if (!capacidadRestante.equals(other.capacidadRestante))
			return false;
		if (index != other.index)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("(%d,%d,%d)",
				index,capacidadRestante,valorAcumulado);
	}

	
}
