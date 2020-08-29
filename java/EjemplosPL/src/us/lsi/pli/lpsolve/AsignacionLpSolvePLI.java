package us.lsi.pli.lpsolve;


import static us.lsi.pli.AuxiliaryPLI.binaryVarsSection;
import static us.lsi.pli.AuxiliaryPLI.constraintEq;
import static us.lsi.pli.AuxiliaryPLI.constraintsSection;
import static us.lsi.pli.AuxiliaryPLI.endSection;
import static us.lsi.pli.AuxiliaryPLI.f;
import static us.lsi.pli.AuxiliaryPLI.forAll;
import static us.lsi.pli.AuxiliaryPLI.goalMinSection;
import static us.lsi.pli.AuxiliaryPLI.listOf;
import static us.lsi.pli.AuxiliaryPLI.sum;
import static us.lsi.pli.AuxiliaryPLI.v;

import java.util.Locale;

import us.lsi.common.Files2;
import us.lsi.lpsolve.AlgoritmoLpSolve;
import us.lsi.lpsolve.SolutionLpSolve;
import us.lsi.pli.AuxiliaryPLI;
import us.lsi.pli.AuxiliaryPLI.PLIType;
import us.lsi.pli.gurobi.AsignacionGurobiPLI;


public class AsignacionLpSolvePLI {
	
	private static int n;
//	private static Integer m;
	private static Double[][] costes;

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
		String ct = getConstraints(PLIType.LPSolve);
		Files2.toFile(ct,"ficheros/asignacionDeTareas_sal.txt");
		Locale.setDefault(new Locale("en", "US"));
		SolutionLpSolve s = AlgoritmoLpSolve.getSolutionFromFile("ficheros/asignacionDeTareas_sal.txt");
		System.out.println(s.solutions());
	}

}
