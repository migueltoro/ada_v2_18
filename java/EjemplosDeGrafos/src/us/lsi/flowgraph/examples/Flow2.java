package us.lsi.flowgraph.examples;


import us.lsi.flowgraph.FlowGraphSolution;
import us.lsi.flowgraph.SolveFlowGraph;
import us.lsi.flowgraph.FlowGraph.TipoDeOptimizacion;


/**
 * Un ejemplo de red de flujo
 * 
 * @author Miguel Toro
 *
 */
public class Flow2 {

	public static void main(String[] args){

		FlowGraphSolution fs = SolveFlowGraph.solve(
				TipoDeOptimizacion.Max,
				"ficheros/flow2.txt",
				"ficheros/flow2Grafo.gv",
				"ficheros/flow2Soluciones.gv",
				"ficheros/flow2Constraints.txt");		

		System.out.println(fs.getGoal());
	
	}
	

}
