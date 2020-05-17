package us.lsi.astar.puzzle;

import us.lsi.astar.AStarGraph;
import us.lsi.astar.AStarSimpleVirtualGraph;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.astar.AStarAlgorithm;
import us.lsi.graphs.virtual.ActionSimpleEdge;



public class TestMainPuzzle {

	public static void main(String[] args) {
		VertexPuzzle e1 = VertexPuzzle.of(0,1,2,3,4,5,6,7,8);
		VertexPuzzle e2 = VertexPuzzle.of(1,2,3,4,0,5,6,7,8);
		AStarGraph<VertexPuzzle,ActionSimpleEdge<VertexPuzzle,ActionPuzzle>> graph = AStarSimpleVirtualGraph.create(e1,x->1.);
		AStarAlgorithm<VertexPuzzle,ActionSimpleEdge<VertexPuzzle,ActionPuzzle>> a = AStarAlgorithm.of(graph,e1,e2,(x,y)->(double)x.getNumDiferentes(y));
		List<VertexPuzzle> vertices = a.getPathVertexList();
		String s = vertices.stream().map(x->x.toString()).collect(Collectors.joining("\n________\n","Solucion\n","\n_________"));
		System.out.println(s);
	}

}
