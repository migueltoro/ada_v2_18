package us.lsi.astar.mochila;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.*;
import us.lsi.graphs.virtual.ActionVirtualVertex;
import us.lsi.mochila.datos.SolucionMochila;
import us.lsi.mochila.datos.DatosMochila;



public class MochilaVertex extends ActionVirtualVertex<MochilaVertex, MochilaEdge, Integer> {

	public static MochilaVertex of(int capacidadInicial) {
		return of(0, capacidadInicial);
	}
	
	public static MochilaVertex of(int index, int capacidadRestante) {
		return new MochilaVertex(index, capacidadRestante);
	}

	public int index;
	public Integer capacidadRestante;
	
	public MochilaVertex(int index, int capacidadRestante) {
		super();
		this.index = index;
		this.capacidadRestante = capacidadRestante;
	}

	public static SolucionMochila getSolucion(List<MochilaEdge> ls){
		SolucionMochila s = SolucionMochila.create();		
		ls.stream().forEach(e->s.add(DatosMochila.getObjeto(e.getSource().index),e.a));
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((capacidadRestante == null) ? 0 : capacidadRestante.hashCode());
		result = prime * result + index;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MochilaVertex other = (MochilaVertex) obj;
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
	public boolean isValid() {
		return index>=0 && index<=DatosMochila.getObjetos().size();
	}

	@Override
	protected List<Integer> actions() {
		Integer nu = Math.min(capacidadRestante/DatosMochila.getPeso(index),DatosMochila.getNumMaxDeUnidades(index));
		List<Integer> alternativas = IntStream.rangeClosed(0,nu).boxed().collect(Collectors.toList());
		Collections.reverse(alternativas);
		return alternativas;
	}

	@Override
	protected MochilaVertex neighbor(Integer a) {
		Integer cr = capacidadRestante-a*DatosMochila.getPeso(index);
		return MochilaVertex.of(index+1,cr);
	}

	@Override
	protected MochilaEdge getEdge(Integer a) {
		return MochilaEdge.of(this, neighbor(a), a);
	}
	
	public Double voraz(Predicate<MochilaVertex> p) {
		return -voraz(index,(double)capacidadRestante,p);
	}
	
	public static Double voraz(int index, Double capacidadRestante, Predicate<MochilaVertex> p) {
		Double r = 0.;
//		while(!p.test(MochilaVertex.of(index, 0))) {
		while (index < DatosMochila.numeroDeObjetos) {
			Double a = Math.min(capacidadRestante/DatosMochila.getPeso(index),DatosMochila.getNumMaxDeUnidades(index));
			r = r + a*DatosMochila.getValor(index);
			capacidadRestante = capacidadRestante-a*DatosMochila.getPeso(index);
			index = index+1;			
		}
		return  r;
	}
}
