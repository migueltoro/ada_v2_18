package us.lsi.search.mochila;

import us.lsi.graphs.virtual.ActionSimpleEdge;
import us.lsi.mochila.datos.DatosMochila;

public class MochilaEdge extends ActionSimpleEdge<MochilaVertex,Double> {
	
	public static MochilaEdge of(MochilaVertex v1, MochilaVertex v2, Double a) {		
		return new MochilaEdge(v1, v2, a);
	}

	public Double a;
	
	private MochilaEdge(MochilaVertex v1, MochilaVertex v2, Double a) {
		super(v1, v2);
		this.a = a;
		super.weight = a*DatosMochila.getValor(v1.index);
//		System.out.println(this);
	}

	@Override
	public String toString() {
		return String.format("(%d,%d,%.2f,%.2f)",this.source.index,this.target.index,a,this.getEdgeWeight());
	}
	
}

