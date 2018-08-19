package us.lsi.astar.puzzle;


import us.lsi.astar.AStarGraph;
import us.lsi.graphs.*;


public class PuzzleGraph extends UndirectedSimpleVirtualGraph<EstadoPuzzle, SimpleEdge<EstadoPuzzle>>
	implements AStarGraph<EstadoPuzzle, SimpleEdge<EstadoPuzzle>>{

	
	public static PuzzleGraph create(EstadoPuzzle... v) {
		return new PuzzleGraph(v);
	}

	protected PuzzleGraph(EstadoPuzzle... v) {
		super(v);
	}

	

	@Override
	public double getWeightToEnd(EstadoPuzzle startVertex,EstadoPuzzle endVertex) {
		if(startVertex==null || endVertex==null)
			throw new IllegalArgumentException("El vértice inicial y final no pueden ser null");
		return startVertex.getNumDiferentes(endVertex);
	}


}
