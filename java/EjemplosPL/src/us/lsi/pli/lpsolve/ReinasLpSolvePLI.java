package us.lsi.pli.lpsolve;


import static us.lsi.pli.AuxiliaryPLI.binaryVarsSection;
import static us.lsi.pli.AuxiliaryPLI.constraintEq;
import static us.lsi.pli.AuxiliaryPLI.constraintLe;
import static us.lsi.pli.AuxiliaryPLI.constraintsSection;
import static us.lsi.pli.AuxiliaryPLI.endSection;
import static us.lsi.pli.AuxiliaryPLI.forAll;
import static us.lsi.pli.AuxiliaryPLI.goalMinSection;
import static us.lsi.pli.AuxiliaryPLI.listOf;
import static us.lsi.pli.AuxiliaryPLI.sum;
import static us.lsi.pli.AuxiliaryPLI.v;

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

	public static int n = 100;

	public static String getConstraints(PLIType type) {	
		Locale.setDefault(new Locale("en", "US"));
		AuxiliaryPLI.setType(type);
		StringBuilder r = new StringBuilder();
		r.append(goalMinSection(v("x",0,0)));
		r.append(constraintsSection());
		r.append(forAll("c",listOf(0,n,i->constraintEq(sum(listOf(0,n,j->v("x",i,j))),1))));
		r.append(forAll("d",listOf(0,n,j->constraintEq(sum(listOf(0,n,i->v("x",i,j))),1))));
		r.append(forAll("e",listOf(-n+1,n-1,d->constraintLe(sum(listOf(0,n,0,n,(i,j) -> j-i == d,(i,j)->v("x",i,j))),1))));
		r.append(forAll("f",listOf(0,2*n-1,d->constraintLe(sum(listOf(0,n,0,n,(i,j) -> j+i == d,(i,j)->v("x",i,j))),1))));
		r.append(binaryVarsSection(listOf(0,n,0,n,(i,j)->v("x",i,j))));
		r.append(endSection());
		return r.toString();
	}
	
	
	public static void main(String[] args) {
		AuxiliaryPLI.setType(AuxiliaryPLI.PLIType.LPSolve);
		ReinasGurobiPLI.n = 8;
		String ct = getConstraints(PLIType.LPSolve);
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

