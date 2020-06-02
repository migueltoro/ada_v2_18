package us.lsi.pli;

import static us.lsi.pli.AuxiliaryPLI.*;

import java.util.Comparator;
import java.util.Locale;

import us.lsi.common.Files2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;

public class TestAuxiliary {
	
	public static Integer n = 20;

	public static String getConstraints() {
		StringBuilder r = new StringBuilder();
		r.append(min);
		r.append(goal(var_1("x",0)));
		r.append(constraintsSection);		
		r.append(forAll_List("a",allDifferentsVarsValues(listOf(0,n,i->var_1("x",i)))));
		r.append(forAll_1(n,"b",i->constraintEq(var_1("y",i),var_1("x",i),i)));
		r.append(forAll_List("c",allDifferentsVarsValues(listOf(0,n,i->var_1("y",i)))));
		r.append(forAll_1(n,"d",i->constraintEq(var_1("z",i),var_1("x",i),-i)));
		r.append(forAll_List("e",allDifferentsVarsValues(listOf(0,n,i->var_1("z",i)))));
		r.append(boundsSection());
		r.append(forAll_1_bound(n,i->boundGe(var_1("z",i),""+(-1.e30))));
		r.append(forAll_1_bound(n,i->boundLe(var_1("x",i),n-1)));
		r.append(binaryVarsSection());
		r.append(intVarsSection());
		r.append(vars_1(n,"x"));
		r.append(vars_1(n,"y"));
		r.append(vars_1(n,"z"));
		r.append(generalConstraintsSection());
		r.append(lastEnd);
		return r.toString();	
	}
	
	

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		String ct = TestAuxiliary.getConstraints();
		Files2.toFile(ct,"ficheros/pruebas_sal.lp");
		GurobiSolution solution = GurobiLp.gurobi("ficheros/pruebas_sal.lp");
		System.out.println("\n\n\n\n");
		System.out.println(String.format("Objetivo : %.2f",solution.objVal));
		System.out.println("\n\n");
		solution.values.entrySet().stream().filter(e->!e.getKey().startsWith("_") && e.getKey().startsWith("x"))
			.sorted(Comparator.comparing(e->e.getKey()))
			.map(e->String.format("%s = %.0f",e.getKey(),e.getValue()))
			.forEach(e->System.out.println(e.toString()));
//		System.out.println(solution.values);
	}

}
