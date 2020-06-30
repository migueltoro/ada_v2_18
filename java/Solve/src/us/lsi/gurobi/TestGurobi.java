package us.lsi.gurobi;

import java.util.Locale;
import java.util.stream.Collectors;

public class TestGurobi {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		GurobiSolution solution = GurobiLp.gurobi("ficheros/model_3.lp");
		System.out.println("\n\n\n\n");
		System.out.println(String.format("Objetivo : %.2f",solution.objVal));
		System.out.println("\n\n");
		System.out.println(solution.values.keySet()
				.stream()
				.filter(e->!e.contains("$"))
				.sorted()
				.map(e->String.format("%s == %.1f, %.1f, %.1f",e,solution.values.get(e),
						solution.values.get(e)+1,solution.values.get(e)-1))
				.collect(Collectors.joining("\n")));
	}

}
