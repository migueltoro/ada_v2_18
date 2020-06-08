package us.lsi.pli.gurobi;

import static us.lsi.pli.AuxiliaryPLI.*;

import java.util.List;
import java.util.Locale;

import us.lsi.common.Files2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.pli.AuxiliaryPLI;

public class AsignacionGurobiPLI {
	
	private static int n;
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

	
	public static String getConstraints(PLIType type) {	
		AuxiliaryPLI.setType(type);
		Locale.setDefault(new Locale("en", "US"));
		StringBuilder r = new StringBuilder();
		r.append(goalMinSection(sum(listOf(0,n,0,n,(i,j)->f(costes[i][j],"x",i,j)))));
		r.append(constraintsSection());
		r.append(forAll("c",listOf(0,n,i->constraintEq(sum(listOf(0,n,j->v("x",i,j))),1))));
		r.append(forAll("d",listOf(0,n,j->constraintEq(sum(listOf(0,n,i->v("x",i,j))),1))));
		r.append(binaryVarsSection(listOf(0,n,0,n,(i,j)->v("x",i,j))));
		r.append(endSection());
		return r.toString();		
	}


	public static void main(String[] args) {
		
		AsignacionGurobiPLI.leeFichero("ficheros/asignacionDeTareas.txt");
		String ct = AsignacionGurobiPLI.getConstraints(PLIType.Gurobi);
		Files2.toFile(ct,"ficheros/asignacionDeTareas_sal.lp");
		Locale.setDefault(new Locale("en", "US"));
		GurobiSolution solution = GurobiLp.gurobi("ficheros/asignacionDeTareas_sal.lp");
		System.out.println(solution.toString((s,d)->d>0.));
	}

}
