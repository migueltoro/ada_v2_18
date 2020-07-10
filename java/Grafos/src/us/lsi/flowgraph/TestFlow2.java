package us.lsi.flowgraph;

import us.lsi.common.Strings2;
import us.lsi.flowgraph.FlowGraph.FGType;
import us.lsi.lpsolve.AlgoritmoLpSolve;
import us.lsi.lpsolve.SolutionLpSolve;

public class TestFlow2 {

	public static void main(String[] args) {
		FlowGraph g = FlowGraph.newGraph("ficheros/andaluciaFlow1.txt", FGType.Max);
		System.out.println(g);
		System.out.println(g.getConstraints());
		FlowGraph.exportToDot(g,"ficheros/andaluciaflow1.gv");
		String constraints = g.getConstraints();
		Strings2.toFile(constraints, "ficheros/andaluciaConstraints1.txt");
		SolutionLpSolve s = AlgoritmoLpSolve.getSolution(constraints);	
		FlowGraphSolution fs = FlowGraphSolution.create(g, s);
		FlowGraphSolution.exportToDot(g,fs,"ficheros/andaluciaSolution.gv");
		System.out.println(fs.getGoal());
		System.out.println(fs.getFlowEdges());
		System.out.println(fs.getFlowVertices());
	}

}
