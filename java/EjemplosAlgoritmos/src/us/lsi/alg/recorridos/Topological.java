package us.lsi.alg.recorridos;

import us.lsi.flowgraph.FlowEdge;
import us.lsi.flowgraph.FlowGraph;
import us.lsi.flowgraph.FlowVertex;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.TopologicalSearch;
import us.lsi.graphs.virtual.EGraph;

public class Topological {

	public static void main(String[] args) {
		FlowGraph graph = FlowGraph.newGraph("ficheros/flow2.txt");
		
		System.out.println(graph);
//		System.out.println(FlowVertex.vertices.get(4));
		EGraph<FlowVertex, FlowEdge> g = Graphs2.eGraphSum(graph,FlowVertex.vertices.get(4),null,null,v->true);
		
		TopologicalSearch<FlowVertex, FlowEdge> ra = GraphAlg.topological(g,FlowVertex.vertices.get(4));
		
		ra.stream().forEach(c->System.out.println(c));;
	}

}
