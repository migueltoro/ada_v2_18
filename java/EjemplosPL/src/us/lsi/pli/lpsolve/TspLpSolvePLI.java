package us.lsi.pli.lpsolve;


import java.util.Locale;

import us.lsi.common.Files2;
import us.lsi.lpsolve.AlgoritmoLpSolve;
import us.lsi.lpsolve.SolutionLpSolve;
import us.lsi.pli.AuxiliaryPLI;
import us.lsi.pli.gurobi.TspGurobi;

public class TspLpSolvePLI {

	public static void main(String[] args) {
		TspGurobi.graph = TspGurobi.graph(20,1.0);
		TspGurobi.n = TspGurobi.graph.vertexSet().size();
		System.out.println(TspGurobi.graph);
		AuxiliaryPLI.setType(AuxiliaryPLI.TipoPLI.LPSolve);
		String ct = TspGurobi.getConstraints2();
		Files2.toFile(ct,"ficheros/tsp_sal.txt");
		Locale.setDefault(new Locale("en", "US"));
		
		SolutionLpSolve s = AlgoritmoLpSolve.getSolutionFromFile("ficheros/tsp_sal.txt");
		System.out.println("-------------------");	
		System.out.println("________");
		System.out.println(s.getGoal());
		System.out.println("________");
		System.out.println("________");
		for(int i=0;i<s.getNumVar();i++){
			if (s.getSolution(i) > 0) {
				System.out.println(s.getName(i) + " = " + s.getSolution(i));
			}
		}		
		
	}

}
