package us.lsi.alg.secuencias;

import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.common.List2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.EGraph;

public class TestSeqAStar {

	public static void main(String[] args) {
		
		Locale.setDefault(new Locale("en", "US"));
		
		SeqVertex.data("cbrrrarreterb", "carretera");
		SeqVertex e1 = SeqVertex.first();
		SeqVertex e2 = SeqVertex.last();
		EGraph<SeqVertex, SeqEdge> graph = Graphs2.simpleVirtualGraphSum(e1,v->v.equals(e2),e2,v->true);		
		
		AStar<SeqVertex, SeqEdge> ms = GraphAlg.aStar(
				graph,
				SeqHeuristic::heuristic);
		
		GraphPath<SeqVertex, SeqEdge> path = ms.search().orElse(null);
		SeqSolution s = SeqSolution.of(path);
		System.out.println(s);
		System.out.println(List2.last(path.getVertexList()));
	}

}
