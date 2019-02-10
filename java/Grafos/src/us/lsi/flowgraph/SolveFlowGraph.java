package us.lsi.flowgraph;

import us.lsi.common.Strings2;
import us.lsi.flowgraph.FlowGraph.TipoDeOptimizacion;
import us.lsi.lpsolve.solution.AlgoritmoPLI;
import us.lsi.lpsolve.solution.SolutionPLI;

public class SolveFlowGraph {

	/**
	 *
	 * 
	 * <p><strong>Fichero de Entrada</strong></p>
	 * <p>#VERTEX#<br>A,1,0.,15.,0.<br>B,0,0.,11.,0.<br>C,0,4.,6.,2.<br>D,0,0.,10.,0.<br>E,0,0.,8.,0.<br>F,2,0.,15,-1.<br>#EDGE#<br>A,B,3.,9.,0.<br>A,C,0.,7.,0.<br>B,D,6.,13.,0.<br>D,E,4.,15.,0.<br>E,F,3.,6.,0.<br>C,F,0.,8.,0.<br>C,E,0.,2.,0.</p>
	 * <p><strong><span style="color: #000000;">Restricciones</span></strong></p>
	 * <p>max +2.0x1-x8<br>+x0 &lt;= 9.0<br>+x0 &gt;= 3.0<br>+x1 &lt;= 7.0<br>+x2 &lt;= 13.0<br>+x2 &gt;= 6.0<br>+x3 &lt;= 15.0<br>+x3 &gt;= 4.0<br>+x4 &lt;= 6.0<br>+x4 &gt;= 3.0<br>+x5 &lt;= 8.0<br>+x6 &lt;= 2.0<br>-x0-x1+x7 = 0.0<br>+x7 &lt;= 15.0<br>+x0-x2 = 0.0<br>+x0 &lt;= 11.0<br>+x1-x5-x6 = 0.0<br>+x1 &lt;= 6.0<br>+x1 &gt;= 4.0<br>+x2-x3 = 0.0<br>+x2 &lt;= 10.0<br>+x3-x4+x6 = 0.0<br>+x3+x6 &lt;= 8.0<br>+x4+x5-x8 = 0.0<br>+x8 &lt;= 15.0</p>
	 * <p><br><strong>Indices </strong></p>	
	 * <p><br>5,C--F<br>7,A<br>4,E--F<br>2,B--D<br>0,A--B<br>1,A--C<br>6,C--E<br>8,F<br>3,D--E</p>
	 * <p><strong>Grafo del Problema</strong></p>
	 * <p>digraph G {<br> A [ label="A<br>/15.0" style="bold" ];<br> B [ label="B<br>/11.0" ];<br> C [ label="C<br>4.0/6.0" ];<br> D [ label="D<br>/10.0" ];<br> E [ label="E<br>/8.0" ];<br> F [ label="F<br>/15.0" style="dotted" ];<br> A -&gt; B [ label="3.0/9.0" ];<br> A -&gt; C [ label="/7.0" ];<br> B -&gt; D [ label="6.0/13.0" ];<br> D -&gt; E [ label="4.0/15.0" ];<br> E -&gt; F [ label="3.0/6.0" ];<br> C -&gt; F [ label="/8.0" ];<br> C -&gt; E [ label="/2.0" ];<br>}</p>
	 * <p><strong>&nbsp;</strong></p>
	 * <p><a href="../../../../document/problemaLiga.jpg" target="_blank">Grafo del Problema</a></p>
	 * <p><strong>&nbsp;</strong></p>
	 * <p><strong>Grafo con las Soluciones</strong></p>
	 * <p>digraph G {<br> A [ label="A<br>12.0" style="bold" ];<br> B [ ];<br> C [ ];<br> D [ ];<br> E [ ];<br> F [ label="F<br>-12.0" style="dotted" ];<br> A -&gt; B [ label="6.0" ];<br> A -&gt; C [ label="6.0" ];<br> B -&gt; D [ label="6.0" ];<br> D -&gt; E [ label="6.0" ];<br> E -&gt; F [ label="6.0" ];<br> C -&gt; F [ label="6.0" ];<br> C -&gt; E [ label="0.0" ];<br>}</p>
	 * <p><strong>&nbsp;</strong></p>
	 * <p><a href="../../../../document/solucionesLiga.jpeg" target="_blank">Grafo con las Soluciones</a></p>
	 * <p><strong>&nbsp;</strong></p>
	 * 
	 * 
	 * 
	 * 
	 * @param tipo Tipo de optimizaci&oacute;n
	 * @param fileIn Fichero con los datos de entrada de la Red de Flujo
	 * @param fileGraphFlow Fichero que contiene el grafo asociado a la Red de Flujo 
	 * @param fileSolutionsGraphFlow Fichero que contiene el grafo con las soluciones de la Red de Flujo
	 * @param fileConstraints Fichero que contiene las restricciones asociadas a la Red de Flujo
	 * @return Las propiedades de la solución
	 */
	
	public static FlowGraphSolution solve(
			TipoDeOptimizacion tipo,
			String fileIn,		
			String fileGraphFlow, 
			String fileSolutionsGraphFlow,
			String fileConstraints) {
		FlowGraph graph = FlowGraph.newGraph(fileIn,tipo);
		String constraints = graph.getConstraints();
		SolutionPLI s = AlgoritmoPLI.getSolution(constraints);	
		FlowGraphSolution fs = FlowGraphSolution.create(graph, s);
		Strings2.toFile(constraints, fileConstraints);
		graph.exportToDot(fileGraphFlow);
		fs.exportToDot(fileSolutionsGraphFlow);
		return fs;
	}
	
	public static FlowGraphSolution solve(TipoDeOptimizacion tipo, String fileIn) {
		FlowGraph graph = FlowGraph.newGraph(fileIn,tipo);
		String constraints = graph.getConstraints();
		SolutionPLI s = AlgoritmoPLI.getSolution(constraints);	
		FlowGraphSolution fs = FlowGraphSolution.create(graph, s);
		return fs;
	}
	
}
