package us.lsi.pli.lpsolve;


import java.util.Locale;

import us.lsi.common.Files2;
import us.lsi.lpsolve.AlgoritmoLpSolve;
import us.lsi.lpsolve.SolutionLpSolve;
import us.lsi.pli.AuxiliaryPLI;
import us.lsi.pli.gurobi.AsignacionGurobiPLI;


public class AsignacionLpSolvePLI {

	

	public static void main(String[] args) {
		AuxiliaryPLI.setType(AuxiliaryPLI.TipoPLI.LPSolve);
		AsignacionGurobiPLI.leeFichero("ficheros/asignacionDeTareas.txt");
		String ct = AsignacionGurobiPLI.getConstraints();
		Files2.toFile(ct,"ficheros/asignacionDeTareas_2_sal.lp");
		Locale.setDefault(new Locale("en", "US"));
		SolutionLpSolve s = AlgoritmoLpSolve.getSolutionFromFile("ficheros/asignacionDeTareas_2_sal.lp");
		System.out.println("-------------------");
		System.out.println("________");
		System.out.println(s.getGoal());
		System.out.println("________");
		System.out.println("________");
		for (int i = 0; i < s.getNumVar(); i++) {
			if (s.getSolution(i) == 1.0)
				System.out.println(s.getName(i));
		}

	}

}
