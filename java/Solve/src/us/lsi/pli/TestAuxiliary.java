package us.lsi.pli;

import static us.lsi.pli.AuxiliaryPLI.*;


import java.util.Locale;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;

public class TestAuxiliary {
	
	public static Integer n = 5;
	
	public static String getConstraints() {
		StringBuilder r = new StringBuilder();
		r.append(max);
		r.append(goal(sum_1_v(n,"x")));
		r.append(constraintsSection);
		r.append(forAll_2(n,2,"c",(i,j)->valueOf(var_1("x",i),2,7,20,45,50,75,101).get(j)));
		r.append(forAll_List("b",allDifferents(var_1("x",0),var_1("x",1),var_1("x",2),var_1("x",3),var_1("x",4))));
		r.append(binaryVars);
		r.append(vars_1(AuxiliaryPLI.nBinary,"_x"));
		r.append(intVars);
		r.append(vars_1(n,"x"));
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
		solution.values.entrySet().stream().filter(e->!e.getKey().startsWith("_") && e.getValue()>0.)
			.map(e->String.format("%s = %.0f",e.getKey(),e.getValue()))
			.forEach(e->System.out.println(e.toString()));
	}

}
