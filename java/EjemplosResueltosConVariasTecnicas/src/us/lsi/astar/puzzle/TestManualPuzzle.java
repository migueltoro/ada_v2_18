package us.lsi.astar.puzzle;

import java.util.List;
import java.util.stream.Collectors;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.ActionSimpleEdge;
import us.lsi.graphs.virtual.SimpleVirtualGraph;


public class TestManualPuzzle {
	
	public static void test2() {
		VertexPuzzle e1 = VertexPuzzle.of(0,1,2,3,4,5,6,7,8);
		VertexPuzzle e2 = VertexPuzzle.of(1,2,3,4,0,5,6,7,8);
		
		SimpleVirtualGraph<VertexPuzzle, ActionSimpleEdge<VertexPuzzle, ActionPuzzle>> graph = 
				Graphs2.simpleVirtualGraph(e1,x->1.);
		
		AStar<VertexPuzzle,ActionSimpleEdge<VertexPuzzle,ActionPuzzle>> a = 
				GraphAlg.aStarEnd(graph,e2,(x,p,y)->(double)x.getNumDiferentes(y));
		
		List<VertexPuzzle> vertices = a.search().get().getVertexList();
		String s = vertices.stream().map(x->x.toString()).collect(Collectors.joining("\n________\n","\nSolucion\n\n","\n_________"));
		System.out.println(e1);
		System.out.println(e2);
		System.out.println(s);
	}
	
	public static void main(String[] args) {	
		test2();
	}

}
