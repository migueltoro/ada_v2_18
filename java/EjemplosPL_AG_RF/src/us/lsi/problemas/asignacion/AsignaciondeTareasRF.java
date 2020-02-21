package us.lsi.problemas.asignacion;


import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.common.Strings2;
import us.lsi.flowgraph.FlowGraph;
import us.lsi.flowgraph.FlowGraph.TipoDeOptimizacion;
import us.lsi.pli.AlgoritmoPLI;
import us.lsi.pli.SolutionPLI;
import us.lsi.flowgraph.FlowGraphSolution;

/**
 * @author Miguel Toro
 * 
 *         Resuelve le problema de la asignación de tareas mediante una red de
 *         flujo
 *
 */
public class AsignaciondeTareasRF {

	private Integer n;
	private Integer m;
	private Double[][] costes;

	public static AsignaciondeTareasRF create(String f) {
		return new AsignaciondeTareasRF(f);
	}

	public Integer getN() {
		return n;
	}

	public Integer getM() {
		return m;
	}

	public Double getCoste(int i, int j) {
		return costes[i][j];
	}

	private AsignaciondeTareasRF(String f) {
		super();
		this.leeFichero(f);
	}

	private void leeFichero(String f) {
		List<String> lineas = Files2.getLines(f);
		this.n = Integer.parseInt(lineas.get(0));
		this.m = Integer.parseInt(lineas.get(1));
		this.costes = new Double[n][m];
		String[] dat;
		Integer i, j;
		for (int k = 2; k < lineas.size(); k++) {
			dat = lineas.get(k).split(",");
			i = Integer.parseInt(dat[0].trim());
			j = Integer.parseInt(dat[1].trim());
			costes[i][j] = Double.parseDouble(dat[2].trim());
		}
	}

	public void creaFichero(String file) {

		PrintWriter f = Files2.getWriter(file);

		f.println("#VERTEX#");
		for (int i = 0; i < n; i++) {
			f.println("p" + i + ",Source,0.0,1.0,0.0," + "p" + i);
		}
		for (int j = 0; j < m; j++) {
			f.println("t" + j + ",Sink,0.0,1.0,0.0," + "t" + j);
		}
		f.println("#EDGE#");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				f.println("p" + i + "," + "t" + j + ",0.0,1.0," + costes[i][j]);
			}
		}
		f.close();
	}

	public static void main(String[] args) {
		FlowGraph.allInteger = true;
		AsignaciondeTareasRF a = AsignaciondeTareasRF.create("ficheros/asignacionDeTareas.txt");
		a.creaFichero("ficheros/redFlujoTareas.txt");

		FlowGraph fg = FlowGraph.newGraph("ficheros/redFlujoTareas2.txt", TipoDeOptimizacion.Max);
		// Se ha modificado la funcion number() de los Vertices y Aristas para que el
		// separador de ',' sea '.' segun formato LpSolve.
		String constraints = fg.getConstraints();
		// constraints = constraints.replaceAll(",", ".");
		System.out.println(constraints);
		System.out.println("======================================");
		System.out.println();

		Files2.toFile(constraints, "ficheros/redFlujoTareasConstraints.txt");

		// SolutionPLI s =
		// AlgoritmoPLI.getSolutionFromFile("ficheros/redFlujoTareasConstraints.txt");
		SolutionPLI s = AlgoritmoPLI.getSolution(fg.getConstraints());
		FlowGraphSolution fs = FlowGraphSolution.create(fg, s);
		fs.exportToDot("ficheros/redFlujoTareasConstraints.gv");

		FlowGraphSolution fs2 = FlowGraphSolution.createOnlySaturated(fs);
		fs.getGraph().exportToDot("ficheros/redFlujoTareasGrafo.gv");
		fs2.exportToDot("ficheros/redFlujoTareasSoluciones.gv");

		// Strings2.toFile(fs.getGraph().getConstraints(),
		// "ficheros/redFlujoTareasConstraints.txt");

		System.out.println(fs.getGoal());
		System.out.println(fs.getFlowVertices());
		System.out.println(fs.getFlowEdges());

		fs.getFlowEdges().entrySet().stream().filter(x -> x.getValue() == 1.0)
				.forEach(x -> System.out.println("(" + x.getKey().getFrom() + "," + x.getKey().getTo() + ")"));

	}
}
