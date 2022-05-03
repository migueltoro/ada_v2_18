package us.lsi.alg.recorridos;

import us.lsi.flowgraph.FlowEdge;
import us.lsi.flowgraph.FlowGraph;
import us.lsi.flowgraph.FlowVertex;
import us.lsi.graphs.alg.TopologicalSearch;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraphI;

public class Topological {

	public static void main(String[] args) {
		FlowGraph graph = FlowGraph.newGraph("ficheros/flow2.txt");
		
		System.out.println(graph);
//		System.out.println(FlowVertex.vertices.get(4));
		EGraph<FlowVertex, FlowEdge> g = EGraphI.sum(graph,FlowVertex.vertices.get(4),null,null);
		
		TopologicalSearch<FlowVertex, FlowEdge> ra = TopologicalSearch.topological(g,FlowVertex.vertices.get(4));
		
		ra.stream().forEach(c->System.out.println(c));;
	}

}
