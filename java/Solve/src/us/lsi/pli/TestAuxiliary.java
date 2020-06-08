package us.lsi.pli;

import static us.lsi.pli.AuxiliaryPLI.*;

import java.util.Comparator;
import java.util.Locale;

import us.lsi.common.Files2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.lpsolve.AlgoritmoLpSolve;
import us.lsi.lpsolve.SolutionLpSolve;
import us.lsi.pli.AuxiliaryPLI.PLIType;

public class TestAuxiliary {
	
	public static int n = 50;

	public static String getConstraints(PLIType type) {
		Locale.setDefault(new Locale("en", "US"));
		Integer n = 3;
		AuxiliaryPLI.setType(type);
		StringBuilder r = new StringBuilder();
		r.append(goalMinSection(v("y",0)));
		r.append(constraintsSection());
		r.append(forAll("a",constraintAllDifferents(listOf(0,n,i->v("y",i)))));
		r.append(forAll("b",listOf(0,n,i->constraintEq(v("d2",i),v("y",i),i))));
		r.append(forAll("c",constraintAllDifferents(listOf(0,n,i->v("d2",i)))));
		r.append(forAll("d",listOf(0,n,i->constraintEq(v("d1",i),v("y",i),-i))));
		r.append(forAll("e",constraintAllDifferents(listOf(0,n,i->v("d1",i)))));
		r.append(boundsSection(
					listOf(0,n,i->varFree(v("d1",i))),
					listOf(0,n,i->constraintLe(v("y",i),n-1))));
		r.append(binaryVarsSection());
		r.append(intVarsSection(listOf(0,n,i->v("y",i)),listOf(0,n,i->v("d1",i)),listOf(0,n,i->v("d2",i))));
		r.append(freeVarsSection());
		r.append(generalConstraintsSection());
		r.append(endSection());
		return r.toString();	
	}
	
	public static String getConstraints2(PLIType type) {
		AuxiliaryPLI.setType(type);
		StringBuilder r = new StringBuilder();
		r.append(goalMaxSection(v("x",0)));
		r.append(constraintsSection());
		r.append(forAll("a",constraintAbs(v("x",0),sum(f(-1,"x",1),f(-1,"x",2),f(-1,"x",3)))));
		r.append(boundsSection(listOf(0,n,i->constraintLe(v("x",i),10))));
		r.append(binaryVarsSection());
		r.append(intVarsSection(listOf(0,n,i->v("x",i))));
		r.append(freeVarsSection());
		r.append(generalConstraintsSection());
		r.append(endSection());
		return r.toString();	
	}
	
	public static void gurobi(String file) {
		GurobiSolution solution = GurobiLp.gurobi(file);
		System.out.println("\n\n\n\n");
		System.out.println(String.format("Objetivo : %.2f",solution.objVal));
		System.out.println("\n\n");
		solution.values.entrySet().stream().filter(e->!e.getKey().contains("$"))
			.sorted(Comparator.comparing(e->e.getKey()))
			.map(e->String.format("%s = %d",e.getKey(),e.getValue().intValue()))
			.forEach(e->System.out.println(e.toString()));
	}
	
	public static void lpSolve(String file) {
		SolutionLpSolve a = AlgoritmoLpSolve.getSolutionFromFile(file);
		System.out.println("-------------------");	
		System.out.println("________");
		System.out.println(String.format("%d",(int) a.getGoal()));
		a.solutions().entrySet().stream().filter(e->!e.getKey().contains("$"))
			.sorted(Comparator.comparing(e->e.getKey()))
			.map(e->String.format("%s = %d",e.getKey(),e.getValue().intValue()))
			.forEach(e->System.out.println(e.toString()));
//		System.out.println(a.solutions());
	}

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		String ct = TestAuxiliary.getConstraints(PLIType.Gurobi);
		Files2.toFile(ct,"ficheros/pruebas_sal.lp");
		gurobi("ficheros/pruebas_sal.lp");
	}

}
