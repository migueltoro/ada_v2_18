package us.lsi.grafos.test;

import us.lsi.common.Strings2;
import us.lsi.flowgraph.FlowGraph;
import us.lsi.flowgraph.FlowGraphSolution;
import us.lsi.flowgraph.FlowGraph.TipoDeOptimizacion;
import us.lsi.pli.AlgoritmoPLI;
import us.lsi.pli.SolutionPLI;

public class TestFlow2 {

	public static void main(String[] args) {
		FlowGraph g = FlowGraph.newGraph("ficheros/andaluciaFlow1.txt", TipoDeOptimizacion.Max);
		System.out.println(g);
		System.out.println(g.getConstraints());
		g.exportToDot("ficheros/andaluciaflow1.gv");
		String constraints = g.getConstraints();
		Strings2.toFile(constraints, "ficheros/andaluciaConstraints1.txt");
		SolutionPLI s = AlgoritmoPLI.getSolution(constraints);	
		FlowGraphSolution fs = FlowGraphSolution.create(g, s);
		fs.exportToDot("ficheros/andaluciaSolution.gv");
		System.out.println(fs.getGoal());
		System.out.println(fs.getFlowEdges());
		System.out.println(fs.getFlowVertices());
	}

}
