package us.lsi.astar.mochila;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.*;

import us.lsi.common.Preconditions;
import us.lsi.graphs.search.GraphSearch;
import us.lsi.graphs.virtual.ActionVirtualVertex;
import us.lsi.mochila.datos.SolucionMochila;
import us.lsi.mochila.datos.DatosMochila;



public class MochilaVertex extends ActionVirtualVertex<MochilaVertex, MochilaEdge, Double> {

	public static MochilaVertex of(Double capacidadInicial) {
		return of(0, capacidadInicial);
	}
	
	public static MochilaVertex copy(MochilaVertex m) {
		return of(m.index, m.capacidadRestante);
	}
	
	public static MochilaVertex of(int index, Double capacidadRestante) {
		return new MochilaVertex(index, capacidadRestante);
	}
	
	public static MochilaVertex lastVertex() {
		return new MochilaVertex(n, 0.);
	}

	public int index;
	public Double capacidadRestante;
	public static Integer n = DatosMochila.numeroDeObjetos;
	
	public MochilaVertex(int index, Double capacidadRestante) {
		super();
		this.index = index;
		if (index < n) {
			this.capacidadRestante = capacidadRestante;
		} else {
			this.capacidadRestante = 0.;
		}
	}

	public static SolucionMochila getSolucion(List<MochilaEdge> ls){
		SolucionMochila s = SolucionMochila.empty();
		ls.stream().forEach(e->s.add(DatosMochila.getObjeto(e.getSource().index),e.a.intValue()));
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
	public Boolean isValid() {
		return index>=0 && index<=DatosMochila.getObjetos().size();
	}
	
	public Double greedyAction() {
		Preconditions.checkElementIndex(index, DatosMochila.numeroDeObjetos);
		return Math.min(this.capacidadRestante/DatosMochila.getPeso(index),DatosMochila.getNumMaxDeUnidades(index));
	}

	@Override
	public List<Double> actions() {
		if(this.index == n) return new ArrayList<>();
		Integer nu = greedyAction().intValue();
		List<Double> alternativas = IntStream.rangeClosed(0,nu)
				.boxed()
				.map(x->x.doubleValue())
				.collect(Collectors.toList());
		Collections.reverse(alternativas);
		return alternativas;
	}
	
	@Override
	public MochilaVertex neighbor(Double a) {
		Double cr = capacidadRestante-a*DatosMochila.getPeso(index);
		return MochilaVertex.of(index+1,cr);
	}
	
	@Override
	public MochilaEdge getEdgeFromAction(Double a) {
		MochilaVertex v = this.neighbor(a);
		return MochilaEdge.of(this,v,a);
	}
	
	public static Double heuristic(MochilaVertex v1, MochilaVertex v2) {
		return GraphSearch.<MochilaVertex, MochilaEdge, Double>greedy(v1, v -> v.greedyAction(), (v, a) -> v.neighbor(a),
				MochilaEdge::of).findWeight(v2);
	}
	
	public static Double voraz(MochilaVertex v1, MochilaVertex v2) {
				return MochilaVertex.voraz(v1.index,v1.capacidadRestante,v2);
	}		
			
	public static Double voraz(int index, Double capacidadRestante, MochilaVertex v) {
		Double r = 0.;
		while (index != v.index) {
			Double a = Math.min(capacidadRestante/DatosMochila.getPeso(index),DatosMochila.getNumMaxDeUnidades(index));
			r = r + a*DatosMochila.getValor(index);
			capacidadRestante = capacidadRestante-a*DatosMochila.getPeso(index);
			index = index+1;			
		}
		return  r;
	}

	@Override
	public String toString() {
		return String.format("(%d,%.2f)",index,capacidadRestante);
	}
	
	
}
