package us.lsi.search.secuencias;

import java.util.List;
import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.EGraph;

public class TestSeqAStar {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		
		SeqVertex.data("cbrrrarreterb", "carretera");
		SeqVertex e1 = SeqVertex.first();
		SeqVertex e2 = SeqVertex.last();
		EGraph<SeqVertex, SeqEdge> graph = Graphs2.simpleVirtualGraph(e1);		
		
		GraphAlg<SeqVertex,SeqEdge> ms = GraphAlg.aStarEnd(
				graph,
				e2,
				SeqHeuristic::heuristic);
		
//		Optional<SeqVertex> r = ms.stream().peek(e->System.out.println(e)).filter(e->e.equals(e2)).findFirst();
		
		GraphPath<SeqVertex, SeqEdge> path = ms.pathTo(e2);
		List<SeqEdge> edges = path.getEdgeList();
		System.out.println(edges);
		SeqSolution s = SeqSolution.of(path);
		System.out.println(s);
		
//		System.out.println(e1);
//		System.out.println(e1.actions());
//		SeqVertex v2 = e1.neighbor(e1.actions().get(0));
//		System.out.println(v2);
//		System.out.println(v2.actions());
//		SeqVertex v3 = v2.neighbor(v2.actions().get(0));
//		System.out.println(v3);
//		System.out.println(v3.actions());	
	}

}
