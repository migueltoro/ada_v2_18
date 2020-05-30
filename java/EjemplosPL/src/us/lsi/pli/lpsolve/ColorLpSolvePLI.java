package us.lsi.pli.lpsolve;

import java.util.Locale;

import us.lsi.lpsolve.AlgoritmoLpSolve;
import us.lsi.lpsolve.SolutionLpSolve;

import us.lsi.pli.AuxiliaryPLI;
import us.lsi.pli.gurobi.ColorGraphGurobi;
import us.lsi.common.Files2;

public class ColorLpSolvePLI {

	
	
	public static void main(String[] args) {		
		ColorGraphGurobi.graph = ColorGraphGurobi.graph(5,0.3);
		ColorGraphGurobi.n = ColorGraphGurobi.graph.vertexSet().size();
		ColorGraphGurobi.m = 5;
		System.out.println(ColorGraphGurobi.graph);
		AuxiliaryPLI.setType(AuxiliaryPLI.TipoPLI.LPSolve);;
		String ct = ColorGraphGurobi.getConstraints();
		Files2.toFile(ct,"ficheros/color_2_sal.txt");
		Locale.setDefault(new Locale("en", "US"));
		SolutionLpSolve s = AlgoritmoLpSolve.getSolutionFromFile("ficheros/color_2_sal.txt");
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
