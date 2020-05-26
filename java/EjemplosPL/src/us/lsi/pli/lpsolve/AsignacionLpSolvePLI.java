package us.lsi.pli.lpsolve;


import static us.lsi.pli.AuxiliaryPLI.*;

import java.util.List;
import java.util.Locale;

import us.lsi.common.Files2;
import us.lsi.lpsolve.AlgoritmoLpSolve;
import us.lsi.lpsolve.SolutionLpSolve;
import us.lsi.pli.AuxiliaryPLI;


public class AsignacionLpSolvePLI {

	private static Integer n;
	private static Integer m;
	private static Double[][] costes;
	
	
	public static void leeFichero(String f) {
		List<String> lineas = Files2.linesFromFile(f);
		AsignacionLpSolvePLI.n = Integer.parseInt(lineas.get(0));
		AsignacionLpSolvePLI.m = Integer.parseInt(lineas.get(1));
		AsignacionLpSolvePLI.costes = new Double[n][m];
		String[] dat;
		Integer i, j;
		for (int k = 2; k < lineas.size(); k++) {
			dat = lineas.get(k).split(",");
			i = Integer.parseInt(dat[0].trim());
			j = Integer.parseInt(dat[1].trim());
			costes[i][j] = Double.parseDouble(dat[2].trim());
		}
	}

	
	public static String getConstraints() {	
		StringBuilder r = new StringBuilder();
		r.append(min);
		r.append(goal(sum_2_f(n,n,"x",(i,j)->costes[i][j].toString())));
		r.append(constraintsSection);
		r.append(forAll_1(n,"c",i->constraintEq(sum_2_2_v(n,i,"x")," 1")));
		r.append(forAll_1(n,"d",j->constraintEq(sum_2_1_v(n,j,"x")," 1")));
		r.append(binaryVars);
		r.append(vars_2(n,n,"x"));
		r.append(lastEnd);
		return r.toString();		
	}


	public static void main(String[] args) {
		AuxiliaryPLI.setType(AuxiliaryPLI.TipoPLI.LPSolve);
		AsignacionLpSolvePLI.leeFichero("ficheros/asignacionDeTareas.txt");
		String ct = AsignacionLpSolvePLI.getConstraints();
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
