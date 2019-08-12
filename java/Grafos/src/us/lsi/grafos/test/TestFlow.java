package us.lsi.grafos.test;

import us.lsi.common.Strings2;
import us.lsi.flowgraph.FlowGraph;
import us.lsi.flowgraph.FlowGraph.TipoDeOptimizacion;
import us.lsi.pli.AlgoritmoPLI;
import us.lsi.pli.SolutionPLI;
import us.lsi.flowgraph.FlowGraphSolution;

public class TestFlow {

	
	public static void main(String[] args) {
		FlowGraph g = FlowGraph.newGraph("ficheros/flow2.txt", TipoDeOptimizacion.Max);
		System.out.println(g);
		System.out.println(g.getConstraints());
		g.exportToDot("ficheros/flow2Export.gv");
		String constraints = g.getConstraints();
		Strings2.toFile(constraints, "ficheros/constraints2.txt");
		SolutionPLI s = AlgoritmoPLI.getSolution(constraints);	
		FlowGraphSolution fs = FlowGraphSolution.create(g, s);
		fs.exportToDot("ficheros/flow2SolutionExport.gv");
		System.out.println(fs.getGoal());
		System.out.println(fs.getFlowEdges());
		System.out.println(fs.getFlowVertices());
	}
}
