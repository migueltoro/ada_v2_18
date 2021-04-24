package us.lsi.alg.monedas;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.graphs.virtual.ActionVirtualVertex;

public class MonedaVertex extends ActionVirtualVertex<MonedaVertex, MonedaEdge, Integer>{
	
	
	public static void datosIniciales(String fichero, Integer valorInicial) {
		Moneda.datos(fichero);
		MonedaVertex.valorInicial = valorInicial;
		MonedaVertex.n = Moneda.monedas.size();
	}

	public static MonedaVertex of(Integer index, Integer valorRestante) {
		return new MonedaVertex(index, valorRestante);
	}


	public static MonedaVertex first() {
		return new MonedaVertex(0,MonedaVertex.valorInicial);
	}
	
	public static MonedaVertex last() {
		return new MonedaVertex(MonedaVertex.n,0);
	}

	public final Integer index;
	public final Integer valorRestante;
	public static Integer n;
	public static Integer valorInicial;
	
//	• t: Integer, derivada n-i
	
	
	private MonedaVertex(Integer index, Integer valorRestante) {
		super();
		this.index = index;
		this.valorRestante = valorRestante<0?0:valorRestante;
	}
	
	public MonedaVertex copy() {
		return MonedaVertex.of(this.index, this.valorRestante);
	}
	
	public Integer size() {
		return MonedaVertex.n - this.index;
	}
	
	@Override
	public Boolean isValid() {
		return this.index >=0 && this.index <= MonedaVertex.n && this.valorRestante >=0;
	}

	public MonedaEdge accionVoraz() {
		Integer a = this.valorRestante/Moneda.valor(this.index);
		return MonedaEdge.of(this,this.neighbor(a),a);
	}
	
	public MonedaEdge accionHeuristica() {
		Integer a = this.valorRestante/Moneda.valor(this.index);
		if(a>0) a = a+1;
		return MonedaEdge.of(this,this.neighbor(a),a);
	}
	
	
	@Override
	public List<Integer> actions() {
		List<Integer> r;
		if(this.index == MonedaVertex.n) r = new ArrayList<>();
		else if(this.index == MonedaVertex.n-1 ) {	
			if(this.valorRestante%Moneda.valor(this.index) == 0) r = List.of(this.accionVoraz().getAction());
			else r = new ArrayList<>();
		} else {
			Integer nue = this.valorRestante/Moneda.valor(this.index);
			r = IntStream.range(0,nue+1).boxed().collect(Collectors.toList());
			Collections.reverse(r);
		}
		return r;
	}

	@Override
	public MonedaVertex neighbor(Integer a) {
		MonedaVertex r;
		if(this.valorRestante == 0) r = MonedaVertex.last();
		else r = MonedaVertex.of(this.index+1,this.valorRestante-a*Moneda.valor(this.index));
		return r;
	}

	@Override
	public MonedaEdge edge(Integer a) {
		return MonedaEdge.of(this,this.neighbor(a),a);
	}

	@Override
	public String toString() {
		return String.format("(%d,%d = %s)",this.index,this.valorRestante,this.actions());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result + ((valorRestante == null) ? 0 : valorRestante.hashCode());
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
		MonedaVertex other = (MonedaVertex) obj;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		if (valorRestante == null) {
			if (other.valorRestante != null)
				return false;
		} else if (!valorRestante.equals(other.valorRestante))
			return false;
		return true;
	}
	
	
	
}
