package us.lsi.pli.gurobi;

import static us.lsi.pli.AuxiliaryPLI.*;

import java.util.List;
import java.util.Locale;

import us.lsi.common.Files2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;

public class AsignacionGurobiPLI {
	
	private static Integer n;
	private static Integer m;
	private static Double[][] costes;
	
	
	public static void leeFichero(String f) {
		List<String> lineas = Files2.linesFromFile(f);
		AsignacionGurobiPLI.n = Integer.parseInt(lineas.get(0));
		AsignacionGurobiPLI.m = Integer.parseInt(lineas.get(1));
		AsignacionGurobiPLI.costes = new Double[n][m];
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
		AsignacionGurobiPLI.leeFichero("ficheros/asignacionDeTareas.txt");
		String ct = AsignacionGurobiPLI.getConstraints();
		Files2.toFile(ct,"ficheros/asignacionDeTareas_sal.lp");
		Locale.setDefault(new Locale("en", "US"));
		GurobiSolution solution = GurobiLp.gurobi("ficheros/asignacionDeTareas_sal.lp");
		System.out.println(solution.toString((s,d)->d>0.));
	}

}
