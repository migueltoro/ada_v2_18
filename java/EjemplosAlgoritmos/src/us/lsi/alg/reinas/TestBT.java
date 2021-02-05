package us.lsi.alg.reinas;


import java.util.function.Predicate;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.EGraph;

public class TestBT {

	public static void main(String[] args) {
		ReinasVertex.n = 8;
		ReinasVertex e1 = ReinasVertex.first();
		Predicate<ReinasVertex> goal = v -> v.index == ReinasVertex.n;

		EGraph<ReinasVertex, ReinasEdge> graph = Graphs2.simpleVirtualGraph(e1);

		BT<ReinasVertex, ReinasEdge, SolucionReinas> ms = BT.backTrackingGoal(graph, goal, (v1, p, v2) -> 0.,
				SolucionReinas::of, ReinasVertex::copy, BTType.All);

		ms.search();
		System.out.println(ms.getSolutions());

	}
}
