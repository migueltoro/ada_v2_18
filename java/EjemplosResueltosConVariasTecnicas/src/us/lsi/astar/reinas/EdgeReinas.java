package us.lsi.astar.reinas;

import us.lsi.graphs.virtual.ActionSimpleEdge;

public class EdgeReinas extends ActionSimpleEdge<VertexReinas,Integer> {
	
	public static EdgeReinas of(VertexReinas c1, VertexReinas c2, Integer y) {
		return new EdgeReinas(c1,c2,y);
	}
	
	Integer y;

	public EdgeReinas(VertexReinas c1, VertexReinas c2, Integer y) {
		super(c1, c2);
		this.y = y;
	}
	
}
