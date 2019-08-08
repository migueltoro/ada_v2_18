package us.lsi.astar.puzzle;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.astar.AStarGraph;
import us.lsi.astar.AStarSimpleVirtualGraph;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.manual.RecorridoAStarManual;

public class TestManualPuzzle {
	
	public static void test2() {
		VertexPuzzle e1 = VertexPuzzle.of(0,1,2,3,4,5,6,7,8);
		VertexPuzzle e2 = VertexPuzzle.of(1,2,3,4,0,5,6,7,8);
		AStarGraph<VertexPuzzle,SimpleEdge<VertexPuzzle>> graph = AStarSimpleVirtualGraph.of(x->1.);
		RecorridoAStarManual<VertexPuzzle,SimpleEdge<VertexPuzzle>> a = RecorridoAStarManual.of(graph,e1,e2,(x,y)->(double)x.getNumDiferentes(y));
		List<VertexPuzzle> vertices = a.minPathToOrigin(e2).getVertexList();
		String s = vertices.stream().map(x->x.toString()).collect(Collectors.joining("\n________\n","\nSolucion\n\n","\n_________"));
		System.out.println(e1);
		System.out.println(e2);
		System.out.println(s);
	}
	
	public static void main(String[] args) {	
		test2();
	}

}
