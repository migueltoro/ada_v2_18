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
public class Flow1 {

	/**
	 * @param args Argumentos
	 */
	public static void main(String[] args) {
		
		FlowGraphSolution fs = SolveFlowGraph.solve(
				TipoDeOptimizacion.Max,
				"ficheros/flow1.txt",
				"ficheros/flow1Grafo.gv",
				"ficheros/flow1Soluciones.gv",
				"ficheros/flow1Constraints.txt");		

		System.out.println(fs.getGoal());

	}
	
	
	
}
