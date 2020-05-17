package us.lsi.monedas;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.graphs.virtual.ActionVirtualVertex;

public class MonedasVertex extends ActionVirtualVertex<MonedasVertex, MonedasEdge, Integer>{
	
	public static void datosIniciales(String fichero, Integer valorInicial) {
		Moneda.datos(fichero);
		MonedasVertex.valorInicial = valorInicial;
		MonedasVertex.n = Moneda.monedas.size();
	}

	public static MonedasVertex of(Integer index, Integer valorRestante) {
		return new MonedasVertex(index, valorRestante);
	}
	
	public static MonedasVertex first() {
		return new MonedasVertex(0, MonedasVertex.valorInicial);
	}
	
	public static MonedasVertex last() {
		return new MonedasVertex(MonedasVertex.n,0);
	}

	public final Integer index;
	public final Integer valorRestante;
	public static Integer n;
	public static Integer valorInicial;
//	• t: Integer, derivada n-i
	
	private MonedasVertex(Integer index, Integer valorRestante) {
		super();
		this.index = index;
		this.valorRestante = valorRestante;
	}
	
	public Integer size() {
		return MonedasVertex.n - this.index;
	}
	
	@Override
	public Boolean isValid() {
		return this.index >=0 && this.index <= MonedasVertex.n && this.valorRestante >=0;
	}

	public Integer accionVoraz() {
		return this.valorRestante/Moneda.monedas.get(this.index).valor;
	}
	
	public Double accionHeuristica() {
		return (1.*this.valorRestante)/Moneda.monedas.get(this.index).valor;
	}
	
//	private S getSolucionCasoBase() {
//		
//	}
	
	@Override
	public List<Integer> actions() {
		List<Integer> r;
		if(this.index == MonedasVertex.n) r = new ArrayList<>();
		else if(this.index == MonedasVertex.n-1) {
			if(this.valorRestante%Moneda.monedas.get(this.index).valor == 0) {
				r = List.of(this.accionVoraz());
			} else {
				r = new ArrayList<>();
			}
		} else if(this.valorRestante == 0) {
			r = List.of(0);
		} else {
			r = IntStream.rangeClosed(0, this.accionVoraz()).boxed().collect(Collectors.toList());
		}
		return r;
	}

	@Override
	public MonedasVertex neighbor(Integer a) {
		return MonedasVertex.of(this.index+1,this.valorRestante-a*Moneda.monedas.get(this.index).valor);
	}

	@Override
	public MonedasEdge edge(Integer a) {
		return MonedasEdge.of(this,this.neighbor(a),a);
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
		MonedasVertex other = (MonedasVertex) obj;
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

	@Override
	public String toString() {
		return String.format("(%d,%d)",index,valorRestante);
	}
	

}
