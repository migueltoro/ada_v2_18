package us.lsi.alg.puzzle;

import java.util.List;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.EGraph;

public class TestMinimoPuzzle {

	public static void main(String[] args) {
		VertexPuzzle start = VertexPuzzle.of(1, 2, 3, 4, 5, 0, 6, 7, 8);
		VertexPuzzle end = VertexPuzzle.of(1,2,3,4,6,5,8,7,0);
		EGraph<VertexPuzzle, EdgePuzzle> graph = Graphs2.simpleVirtualGraph(start,x->x.getWeight());	
		AStar<VertexPuzzle, EdgePuzzle> a = GraphAlg.dijsktra(graph, end);
		GraphPath<VertexPuzzle,EdgePuzzle> path = a.search().orElse(null);
		List<VertexPuzzle> vertices = path.getVertexList();
		for (VertexPuzzle v: vertices) {
			System.out.println(v);
			System.out.println("====================");
		}
	}

}
