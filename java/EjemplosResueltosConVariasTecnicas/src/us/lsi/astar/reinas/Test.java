package us.lsi.astar.reinas;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import us.lsi.astar.AStarAlgorithm;
import us.lsi.astar.AStarGraph;
import us.lsi.astar.AStarSimpleVirtualGraph;

public class Test {

	public static void main(String[] args) {
		VertexReinas.numeroDeReinas = 100;
		VertexReinas e1 = VertexReinas.of();
		Predicate<VertexReinas> p = v->v.x==VertexReinas.numeroDeReinas;
		AStarGraph<VertexReinas,EdgeReinas> graph = AStarSimpleVirtualGraph.of(x->1.);
		AStarAlgorithm<VertexReinas,EdgeReinas> a = AStarAlgorithm.of(graph,e1,p,(x,y)->(double) (VertexReinas.numeroDeReinas-x.x));
		GraphPath<VertexReinas,EdgeReinas> gp = a.getPath();
		System.out.println(gp.getEdgeList().stream().map(e->e.y).map(e->e.toString()).collect(Collectors.joining(",")));
	}

}
