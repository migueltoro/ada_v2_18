package us.lsi.astar.puzzle;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.astar.AStarAlgorithm;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.virtual.ActionSimpleEdge;
import us.lsi.graphs.virtual.SimpleVirtualGraph;



public class TestMainPuzzle {

	public static void main(String[] args) {
		VertexPuzzle e1 = VertexPuzzle.of(0,1,2,3,4,5,6,7,8);
		VertexPuzzle e2 = VertexPuzzle.of(1,2,3,4,0,5,6,7,8);
		
		SimpleVirtualGraph<VertexPuzzle, ActionSimpleEdge<VertexPuzzle, ActionPuzzle>> graph = Graphs2.sum(e1,x->1.);
		
		AStarAlgorithm<VertexPuzzle,ActionSimpleEdge<VertexPuzzle,ActionPuzzle>> a = 
				AStarAlgorithm.of(graph,e1,null,e2,(x,p,y)->(double)x.getNumDiferentes(y));
		
		List<VertexPuzzle> vertices = a.getPathVertexList();
		String s = vertices.stream().map(x->x.toString()).collect(Collectors.joining("\n________\n","Solucion\n","\n_________"));
		System.out.println(s);
	}

}
