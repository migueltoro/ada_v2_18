package us.lsi.pli.gurobi;

import static us.lsi.pli.AuxiliaryPLI.*;
import java.util.Locale;

import us.lsi.common.Files2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.pli.AuxiliaryPLI;

public class ReinasGurobiPLI {
	
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
	
	
	
	public static String getConstraints2(PLIType type) {
		Locale.setDefault(new Locale("en", "US"));
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
		r.append(intVarsSection(listOf(0,n,i->v("y",i)),listOf(0,n,i->v("d2",i)),listOf(0,n,i->v("d1",i))));
		r.append(freeVarsSection());
		r.append(generalConstraintsSection());
		r.append(endSection());
		return r.toString();	
	}
	
	public static void main(String[] args) {
		n = 10;
		String ct = ReinasGurobiPLI.getConstraints2(PLIType.Gurobi);
		Files2.toFile(ct,"ficheros/reinas_sal.lp");	
		Locale.setDefault(new Locale("en", "US"));
		GurobiSolution solution = GurobiLp.gurobi("ficheros/reinas_sal.lp");
		System.out.println(solution.toString((s,d)->!s.contains("$")));
	}

}
