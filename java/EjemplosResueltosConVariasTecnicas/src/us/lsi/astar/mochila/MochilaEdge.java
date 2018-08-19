package us.lsi.astar.mochila;

import us.lsi.graphs.SimpleEdge;

public class MochilaEdge extends SimpleEdge<MochilaVertex> {

	/**
	 * 
	 */
	
	public static MochilaEdge create(MochilaVertex c1, MochilaVertex c2, double weight, Integer a) {
		return new MochilaEdge(c1, c2, weight, a);
	}

	private Integer a;
	
	private MochilaEdge(MochilaVertex c1, 
			MochilaVertex c2, double weight, Integer a) {
		super(c1, c2, weight);
		this.a = a;
	}

	public MochilaEdge(MochilaVertex c1, MochilaVertex c2) {
		super(c1, c2);
		
	}

	public Integer getAlternativa() {
		return a;
	}

}
