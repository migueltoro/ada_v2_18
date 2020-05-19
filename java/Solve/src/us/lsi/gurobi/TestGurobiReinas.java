package us.lsi.gurobi;

import static us.lsi.gurobi.AuxiliaryGurobi.*;
import us.lsi.common.Files2;

public class TestGurobiReinas {
	
	public static Integer n = 300;

	public static String getConstraints() {	
		StringBuilder r = new StringBuilder();
		r.append(goalMin(sum_2_1_v(n,0,"x")));
		r.append("\nSubject To\n");
		r.append(forAll_1(n,i->constraintEq(sum_2_2_v(n,i,"x")," 1",i,"c")));
		r.append(forAll_1(n,j->constraintEq(sum_2_1_v(n,j,"x")," 1",j,"d")));
		r.append(forAll_1_r(-n+1,n-1,d->constraintLe(sum_2_p(n,n,"x",(i,j) -> j-i == d)," 1",d,"e")));
		r.append(forAll_1(2*n-1,d->constraintLe(sum_2_p(n,n,"x",(i,j) -> j+i == d)," 1",d,"f")));
		r.append("\nBinary\n\n");
		r.append(vars(n,n,"x"));
		return r.toString();
	}
	
	public static void main(String[] args) {	
		String ct = TestGurobiReinas.getConstraints();
		Files2.toFile(ct,"ficheros/reinas_sal.lp");
		
	}

}
