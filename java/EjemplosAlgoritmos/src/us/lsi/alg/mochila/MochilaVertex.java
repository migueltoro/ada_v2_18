package us.lsi.alg.mochila;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.*;

import org.jgrapht.GraphPath;

import us.lsi.common.Lists2;
import us.lsi.common.Preconditions;
import us.lsi.graphs.virtual.ActionVirtualVertex;
import us.lsi.mochila.datos.SolucionMochila;
import us.lsi.mochila.datos.DatosMochila;



public class MochilaVertex extends ActionVirtualVertex<MochilaVertex, MochilaEdge, Integer> {

	public static MochilaVertex initialVertex() {
		return of(0, capacidadInicial);
	}
	
	public static MochilaVertex copy(MochilaVertex m) {
		return of(m.index, m.capacidadRestante);
	}
	
	public static MochilaVertex of(int index, Integer capacidadRestante) {
		return new MochilaVertex(index, capacidadRestante);
	}
	
	public static MochilaVertex lastVertex() {
		return new MochilaVertex(n, 0);
	}
	
	public static Predicate<MochilaVertex> goal() {
		return (MochilaVertex v)->v.index == MochilaVertex.n;
	}

	public int index;
	public Integer capacidadRestante;
	public static Integer n = DatosMochila.numeroDeObjetos;
	public static Integer capacidadInicial;
	
	public MochilaVertex(int index, Integer capacidadRestante) {
		super();
		this.index = index;
		if (index < n) {
			this.capacidadRestante = capacidadRestante;
		} else {
			this.capacidadRestante = 0;
		}		
	}
	
	public static SolucionMochila getSolucion(GraphPath<MochilaVertex, MochilaEdge> path){
		return MochilaVertex.getSolucion(path.getEdgeList());
	}

	public static SolucionMochila getSolucion(List<MochilaEdge> ls){
		SolucionMochila s = SolucionMochila.empty();
		ls.stream().forEach(e->s.add(DatosMochila.getObjeto(e.getSource().index),e.a.intValue()));
		return s;
	}

	@Override
	public Boolean isValid() {
		return index>=0 && index<=DatosMochila.getObjetos().size();
	}
	
	
	
	public MochilaEdge greedyEdge() {
		Preconditions.checkElementIndex(index, DatosMochila.numeroDeObjetos);
		Integer a = Math.min(this.capacidadRestante/DatosMochila.getPeso(index),DatosMochila.getNumMaxDeUnidades(index));
		return MochilaEdge.of(this,this.neighbor(a), a);
	}
	
	public Integer greedyAction() {
		Preconditions.checkElementIndex(index, DatosMochila.numeroDeObjetos);
		return Math.min(this.capacidadRestante/DatosMochila.getPeso(index),DatosMochila.getNumMaxDeUnidades(index));
	}
	
	public Double heuristicAction() {
		Preconditions.checkElementIndex(index, DatosMochila.numeroDeObjetos);
		return Math.min(this.capacidadRestante.doubleValue()/DatosMochila.getPeso(index),DatosMochila.getNumMaxDeUnidades(index));
	}

	@Override
	public List<Integer> actions() {
		if(this.index == n) return new ArrayList<>();
		Integer nu = greedyAction().intValue();
		if(this.index == n-1) return Lists2.of(nu);
		List<Integer> alternativas = IntStream.rangeClosed(0,nu)
				.boxed()
				.collect(Collectors.toList());
		Collections.reverse(alternativas);
		return alternativas;
	}
	
	@Override
	public MochilaVertex neighbor(Integer a) {
		MochilaVertex r;
		if(this.capacidadRestante == 0.) r =  MochilaVertex.of(n,0);
		if(this.index == n-1) r =  MochilaVertex.of(n,0);
		else {
			Integer cr = capacidadRestante-a*DatosMochila.getPeso(index);
			r = MochilaVertex.of(index+1,cr);
		}
		return r;
	}
	
	@Override
	public MochilaEdge edge(Integer a) {
		MochilaVertex v = this.neighbor(a);
		return MochilaEdge.of(this,v,a);
	}
	

	@Override
	public String toString() {
		return String.format("(%d,%d)",index,capacidadRestante);
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
	
	
}

