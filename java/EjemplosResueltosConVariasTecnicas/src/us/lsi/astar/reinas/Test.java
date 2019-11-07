package us.lsi.astar.reinas;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import us.lsi.astar.AStarAlgorithm;
import us.lsi.astar.AStarGraph;
import us.lsi.astar.AStarSimpleVirtualGraph;
import us.lsi.math.Math2;





public class Test {

	public static void main(String[] args) {
		Math2.initRandom();
		VertexReinas.numeroDeReinas =100;
		VertexReinas.resto = 10;
		VertexReinas e1 = VertexReinas.of();
		Predicate<VertexReinas> p = v->v.x==VertexReinas.numeroDeReinas;
		AStarGraph<VertexReinas,EdgeReinas> graph = AStarSimpleVirtualGraph.<VertexReinas,EdgeReinas>of(x->1.);
		AStarAlgorithm<VertexReinas,EdgeReinas> a = AStarAlgorithm.of(graph,e1,p,(x,y)->(double) (VertexReinas.numeroDeReinas-x.x));
		while(true) {
			Math2.initRandom();
			if(a.getPath()!=null) break;
			System.out.println("Un paso");
		}
		List<Integer> yO = a.getPath().getEdgeList().stream().map(e->e.y).collect(Collectors.toList());
		System.out.println(yO);
	}

}
