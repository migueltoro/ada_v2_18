package us.lsi.astar.reinas;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GSearch;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class Test {

	public static void main(String[] args) {
		VertexReinas.numeroDeReinas = 8;
		VertexReinas e1 = VertexReinas.of();
		Predicate<VertexReinas> p = v->v.x==VertexReinas.numeroDeReinas;
		
		SimpleVirtualGraph<VertexReinas, EdgeReinas> graph = Graphs2.simpleVirtualGraph(e1,x->1.);
		
		AStar<VertexReinas,EdgeReinas> a = 
				GSearch.aStarGoal(graph,p,(x,goal,y)->(double) (VertexReinas.numeroDeReinas-x.x));
		
		GraphPath<VertexReinas,EdgeReinas> gp = a.pathToEnd();
		
		System.out.println(gp.getEdgeList().stream().map(e->e.y).map(e->e.toString()).collect(Collectors.joining(",")));
	}

}
