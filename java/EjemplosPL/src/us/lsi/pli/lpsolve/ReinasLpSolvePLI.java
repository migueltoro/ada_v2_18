package us.lsi.pli.lpsolve;


import java.util.Comparator;
import java.util.Locale;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.lpsolve.AlgoritmoLpSolve;
import us.lsi.lpsolve.SolutionLpSolve;
import us.lsi.pli.AuxiliaryPLI;
import us.lsi.pli.AuxiliaryPLI.PLIType;
import us.lsi.pli.gurobi.ReinasGurobiPLI;



public class ReinasLpSolvePLI {

		
	
	public static void main(String[] args) {
		AuxiliaryPLI.setType(AuxiliaryPLI.PLIType.LPSolve);
		ReinasGurobiPLI.n = 8;
		String ct = ReinasGurobiPLI.getConstraints2(PLIType.LPSolve);
		Files2.toFile(ct,"ficheros/reinas_sal.txt");	
		Locale.setDefault(new Locale("en", "US"));
		SolutionLpSolve ss = AlgoritmoLpSolve.getSolutionFromFile("ficheros/reinas_sal.txt");
//		System.out.println(ss.toString((s,d)->!s.contains("$")&&d>0));
		System.out.println(ss.solutions().entrySet().stream()
				.filter(e->!e.getKey().contains("$"))
				.sorted(Comparator.comparing(e->e.getKey()))
				.map(e->String.format("%s = %.0f",e.getKey(),e.getValue()))
				.collect(Collectors.joining("\n")));
	}
	
	
}

