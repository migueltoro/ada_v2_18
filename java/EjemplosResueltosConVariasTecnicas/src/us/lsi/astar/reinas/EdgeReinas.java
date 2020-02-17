package us.lsi.astar.reinas;

import us.lsi.graphs.virtual.ActionSimpleEdge;

public class EdgeReinas extends ActionSimpleEdge<VertexReinas,ActionReinas> {
	
	public static EdgeReinas of(VertexReinas c1, VertexReinas c2, ActionReinas y) {
		return new EdgeReinas(c1,c2,y);
	}
	
	ActionReinas y;

	public EdgeReinas(VertexReinas c1, VertexReinas c2, ActionReinas y) {
		super(c1, c2);
		this.y = y;
	}
	
}
