package us.lsi.pli.gurobi;

import static us.lsi.pli.AuxiliaryPLI.*;


import java.util.Locale;

import us.lsi.common.Files2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;

public class ReinasGurobiPLI {
	
	public static Integer n = 100;

	public static String getConstraints() {	
		StringBuilder r = new StringBuilder();
		r.append(min);
		r.append(goal(sum_2_1_v(n,0,"x")));
		r.append(constraintsSection);
		r.append(forAll_1(n,"c",i->constraintEq(sum_2_2_v(n,i,"x")," 1")));
		r.append(forAll_1(n,"d",j->constraintEq(sum_2_1_v(n,j,"x")," 1")));
		r.append(forAll_1_r(-n+1,n-1,"e",d->constraintLe(sum_2_p(n,n,"x",(i,j) -> j-i == d)," 1")));
		r.append(forAll_1(2*n-1,"f",d->constraintLe(sum_2_p(n,n,"x",(i,j) -> j+i == d)," 1")));
		r.append(binaryVars);
		r.append(vars_2(n,n,"x"));
		r.append(lastEnd);
		return r.toString();
	}
	
	
	public static void main(String[] args) {
		String ct = ReinasGurobiPLI.getConstraints();
		Files2.toFile(ct,"ficheros/reinas_sal.lp");	
		Locale.setDefault(new Locale("en", "US"));
		GurobiSolution solution = GurobiLp.gurobi("ficheros/reinas_sal.lp");
		System.out.println(solution.toString((s,d)->d>0.));
	}

}
