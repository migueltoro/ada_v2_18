package us.lsi.flowgraph.examples;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.graphs.*;
import us.lsi.common.Strings2;
import us.lsi.flowgraph.FlowGraph.TipoDeOptimizacion;
import us.lsi.flowgraph.FlowGraphSolution;
import us.lsi.flowgraph.SolveFlowGraph;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;



/**
 * 
 * Resuelve le problema del  cálculo de los caminos con vértices y aristas disjuntos mediante una red de flujo
 * 
 * @author Miguel Toro
 *
 */

public class CaminosVerticesDisjuntos {
	
	public static void creaFichero(String fileIn, String fileOut, Ciudad from, Ciudad to){
		
		SimpleWeightedGraph<Ciudad,Carretera> graph =  
			GraphsReader.newGraph("ficheros/andalucia.txt",
				Ciudad::create, 
				Carretera::create,
				()->new SimpleWeightedGraph<Ciudad,Carretera>(
						Ciudad::create,
						Carretera::create),
				Carretera::getKm);
		System.out.println(graph);
		SimpleDirectedWeightedGraph<Ciudad,Carretera> gs = Graphs2.toDirectedGraph(graph);
		PrintWriter f = null;
		try {
			f = new PrintWriter(new BufferedWriter(new FileWriter(fileOut)));
		} catch (IOException e) {
			System.err.println(e.toString());
		}
		
		f.println("#VERTEX#");
		for(Ciudad c:gs.vertexSet()){
			if (c.equals(from)) {
				f.println(c + ",Source,0.0,inf,1.0,"+c);
			}else if(c.equals(to)){
				f.println(c + ",Sink,0.0,inf,0.0,"+c);
			}else {
				f.println(c + ",Intermediate,0.0,1.0,0.0,"+c);
			}
		}		
		f.println("#EDGE#");
		for(Carretera cr:gs.edgeSet()){
			Ciudad source = gs.getEdgeSource(cr);
			Ciudad target = gs.getEdgeTarget(cr);
			if (!source.equals(to) && !target.equals(from)) {
				f.println(source + "," + target+ ",0.0,1.0,0.0");
			}
		}
		f.close();
	}
	
	public static void main(String[] args) {
		Ciudad from = Ciudad.create("Cadiz");
		Ciudad to = Ciudad.create("Almeria");
		CaminosVerticesDisjuntos.creaFichero("ficheros/andalucia.txt","ficheros/andaluciaFlow1.txt",from,to);
		
		FlowGraphSolution fs = SolveFlowGraph.solve(TipoDeOptimizacion.Max, "ficheros/andaluciaFlow1.txt");

    	FlowGraphSolution fs2 = FlowGraphSolution.createOnlySaturated(fs);
		fs.getGraph().exportToDot("ficheros/andaluciaFlowGrafo1.gv");
		fs2.exportToDot("ficheros/andaluciaFlowSoluciones1.gv");
		Strings2.toFile(fs.getGraph().getConstraints(), "ficheros/andaluciaFlowConstraints1.txt");

		System.out.println(fs.getGoal());
		
		
		
	}
}
