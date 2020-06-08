package us.lsi.pli.lpsolve;

import java.util.Locale;
import java.util.stream.Collectors;

import us.lsi.lpsolve.AlgoritmoLpSolve;
import us.lsi.lpsolve.SolutionLpSolve;
import us.lsi.pli.AuxiliaryPLI.PLIType;
import us.lsi.pli.gurobi.ColorGraphGurobi;
import us.lsi.common.Files2;

public class ColorLpSolvePLI {

	
	
	public static void main(String[] args) {		
		ColorGraphGurobi.graph = ColorGraphGurobi.graph(10,0.3);
		ColorGraphGurobi.n = ColorGraphGurobi.graph.vertexSet().size();
		ColorGraphGurobi.m = 10;
//		System.out.println(ColorGraphGurobi.graph);
		String ct = ColorGraphGurobi.getConstraints(PLIType.LPSolve);
		Files2.toFile(ct,"ficheros/color_sal.txt");
		Locale.setDefault(new Locale("en", "US"));
		SolutionLpSolve s = AlgoritmoLpSolve.getSolutionFromFile("ficheros/color_sal.txt");
		System.out.println(s.getGoal());
		System.out.println(s.solutions()
				.entrySet().stream()
				.filter(e->e.getKey().startsWith("x") && e.getValue()>0.)
				.map(e->String.format("%s",e.getKey()))
				.collect(Collectors.joining("\n")));
	}
}
