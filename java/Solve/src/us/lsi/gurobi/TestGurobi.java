package us.lsi.gurobi;

import java.util.Locale;
import java.util.stream.Collectors;

public class TestGurobi {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		GurobiSolution solution = GurobiLp.gurobi("ficheros/reinas_sal.lp");
		System.out.println("\n\n\n\n");
		System.out.println(String.format("Objetivo : %.2f",solution.objVal));
		System.out.println("\n\n");
		System.out.println(solution.values.keySet()
				.stream()
				.filter(e->solution.values.get(e)>0.)
				.map(e->String.format("%s == %.1f",e,solution.values.get(e)))
				.collect(Collectors.joining("\n")));
	}

}
