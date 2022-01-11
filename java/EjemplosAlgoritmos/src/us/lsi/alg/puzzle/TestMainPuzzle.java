package us.lsi.alg.puzzle;

import java.util.List;
import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;


public class TestMainPuzzle {
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		
		VertexPuzzle v1 = VertexPuzzle.of(1, 2, 3, 4, 5, 0, 6, 7, 8);
		VertexPuzzle v2 = VertexPuzzle.of(1,2,3,4,0,5,6,7,8);
		VertexPuzzle v3 = VertexPuzzle.of(1,2,3,4,6,5,8,7,0);
		VertexPuzzle v4 = VertexPuzzle.of(1,2,3,4,5,6,7,8,0);
		VertexPuzzle v5 = VertexPuzzle.of(3,1,6,0,8,2,4,5,7);
		VertexPuzzle v6 = VertexPuzzle.of(0,1,2,3,4,5,6,7,8);
		VertexPuzzle v7 = VertexPuzzle.of(1,2,3,4,5,6,7,8,0);
		VertexPuzzle v8 = VertexPuzzle.of(8,1,3,4,0,2,7,6,5);
		
		VertexPuzzle e1 = v1;
		VertexPuzzle e2 = v8;
		System.out.println(VertexPuzzle.isSolvable2(e1.datos(),e2.datos()));
		
		EGraph<VertexPuzzle, EdgePuzzle> graph = 
				SimpleVirtualGraph.sum(e1,v->v.equals(e2),e->e.weight());		
		
		AStar<VertexPuzzle, EdgePuzzle> ms = 
				AStar.of(graph,HeuristicaPuzzle::heuristicaManhattan,AStarType.Min);
		
//		ms.search();
		
		GraphPath<VertexPuzzle,EdgePuzzle> path = ms.search().orElse(null);
		List<VertexPuzzle> vertices = path.getVertexList();
		for (VertexPuzzle v: vertices) {
			System.out.println(v);
			System.out.println("====================");
		}

	}
}
