package us.lsi.alg.puzzle;

import us.lsi.graphs.virtual.ActionSimpleEdge;

public class EdgePuzzle extends ActionSimpleEdge<VertexPuzzle,ActionPuzzle> {
	
	public static EdgePuzzle of(VertexPuzzle v1, VertexPuzzle v2, ActionPuzzle a) {		
		return new EdgePuzzle(v1, v2, a);
	}

	public ActionPuzzle a;
	
	private EdgePuzzle(VertexPuzzle v1, VertexPuzzle v2, ActionPuzzle a) {
		super(v1, v2);
		this.a = a;
		super.weight = 1.;
//		System.out.println(this);
	}

	@Override
	public String toString() {
		return String.format("(%s,%s,%s,%.2f)",this.source.toString(),this.target.toString(),a,this.getEdgeWeight());
	}
	
}


