package us.lsi.flowgraph.examples;


import java.io.PrintWriter;
import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.Strings2;
import us.lsi.flowgraph.FlowGraph;
import us.lsi.flowgraph.FlowGraph.TipoDeOptimizacion;
import us.lsi.flowgraph.FlowGraphSolution;
import us.lsi.flowgraph.SolveFlowGraph;

/**
 * @author Miguel Toro
 * 
 * Resuelve le problema de la asignación de tareas mediante una red de flujo
 *
 */
public class AsignaciondeTareas {

	private Integer n;
	private Integer m;
	private Double[][] costes;	
	
	public static AsignaciondeTareas create(String f) {
		return new AsignaciondeTareas(f);
	}
	
	private AsignaciondeTareas(String f) {
		super();
		this.leeFichero(f);
	}

	private void leeFichero(String f){
		List<String> lineas = Files2.getLines(f);
		this.n = Integer.parseInt(lineas.get(0));
		this.m = Integer.parseInt(lineas.get(1));
		this.costes = new  Double[n][m];
		String[] dat; 
		Integer i, j;
		for(int k=2;k<lineas.size();k++){
			dat = lineas.get(k).split(",");
			i = Integer.parseInt(dat[0].trim());
			j = Integer.parseInt(dat[1].trim());
			costes[i][j] = Double.parseDouble(dat[2].trim());
		}
	}

	public void creaFichero(String file){
		
		PrintWriter f = Files2.getWriter(file);
		
		f.println("#VERTEX#");
		for(int i=0;i<n;i++){
			f.println("p"+i+",Source,0.0,1.0,0.0,"+"p"+i);
		}
		for(int j=0;j<m;j++){
			f.println("t"+j+",Sink,0.0,1.0,0.0,"+"t"+j);
		}
		f.println("#EDGE#");
		for(int i=0;i<n;i++){
			for(int j=0;j<m;j++){
				f.println("p"+i+","+"t"+j+",0.0,1.0,"+costes[i][j]);
			}
		}
		f.close();
	}
	
	
	public static void main(String[] args) {
		FlowGraph.allInteger = true;
		AsignaciondeTareas a = AsignaciondeTareas.create("ficheros/asignacionDeTareas.txt");
		a.creaFichero("ficheros/redFlujoTareas.txt");

		FlowGraphSolution fs = SolveFlowGraph.solve(TipoDeOptimizacion.Max, "ficheros/redFlujoTareas.txt");

		FlowGraphSolution fs2 = FlowGraphSolution.createOnlySaturated(fs);
		fs.getGraph().exportToDot("ficheros/redFlujoTareasGrafo.gv");
		fs2.exportToDot("ficheros/redFlujoTareasSoluciones.gv");

		Strings2.toFile(fs.getGraph().getConstraints(), "ficheros/redFlujoTareasConstraints.txt");

		System.out.println(fs.getGoal());

		fs.getFlowEdges().entrySet().stream().filter(x -> x.getValue() == 1.0)
				.forEach(x -> System.out.println("(" + x.getKey().getFrom() + "," + x.getKey().getTo() + ")"));
	}
}
