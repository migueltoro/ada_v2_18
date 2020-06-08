package us.lsi.pli.lpsolve;


import java.util.Locale;

import us.lsi.common.Files2;
import us.lsi.lpsolve.AlgoritmoLpSolve;
import us.lsi.lpsolve.SolutionLpSolve;
import us.lsi.pli.AuxiliaryPLI.PLIType;
import us.lsi.pli.gurobi.AsignacionGurobiPLI;


public class AsignacionLpSolvePLI {

	

	public static void main(String[] args) {
		AsignacionGurobiPLI.leeFichero("ficheros/asignacionDeTareas.txt");
		String ct = AsignacionGurobiPLI.getConstraints(PLIType.LPSolve);
		Files2.toFile(ct,"ficheros/asignacionDeTareas_sal.txt");
		Locale.setDefault(new Locale("en", "US"));
		SolutionLpSolve s = AlgoritmoLpSolve.getSolutionFromFile("ficheros/asignacionDeTareas_sal.txt");
		System.out.println(s.solutions());
	}

}
