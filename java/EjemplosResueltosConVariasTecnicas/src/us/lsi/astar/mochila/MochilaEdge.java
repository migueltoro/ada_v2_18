package us.lsi.astar.mochila;

import us.lsi.graphs.SimpleEdge;
import us.lsi.mochila.datos.DatosMochila;

public class MochilaEdge extends SimpleEdge<MochilaVertex> {
	
	public static MochilaEdge of(MochilaVertex v1, MochilaVertex v2, Integer a) {
		Double weight = -(double) a*DatosMochila.getValor(v1.index);
		return new MochilaEdge(v1, v2, weight, a);
	}

	public Integer a;
	
	private MochilaEdge(MochilaVertex c1, 
			MochilaVertex c2, double weight, Integer a) {
		super(c1, c2, weight);
		this.a = a;
	}

	public MochilaEdge(MochilaVertex c1, MochilaVertex c2) {
		super(c1, c2);
		
	}

}
