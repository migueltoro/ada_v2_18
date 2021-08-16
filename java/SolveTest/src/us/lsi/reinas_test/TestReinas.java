package us.lsi.reinas_test;

import java.io.IOException;
import java.util.Locale;
import java.util.stream.Collectors;

import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.mochila_test.DataMochila;
import us.lsi.solve_test.AuxGrammar2;

public class TestReinas {
	
	public static void reinas() throws IOException {
		AuxGrammar2.generate(DataMochila.class,"ficheros/reinas.lsi","ficheros/reinas.lp");
		GurobiSolution s = GurobiLp.solveSolution("ficheros/reinas.lp");
		System.out.println("\n\n\n\n");
		System.out.println(String.format("Objetivo : %.2f",s.objVal));
		System.out.println("\n\n");
		System.out.println(s.values.keySet()
				.stream()
				.filter(e->!e.contains("$"))
				.map(e->String.format("%s == %.0f == %.1f == %.0f",e,s.values.get(e),s.values.get(e)+1,s.values.get(e)-1))
				.collect(Collectors.joining("\n")));
	}
	
	public static void main(String[] args) throws IOException {
		Locale.setDefault(new Locale("en", "US"));
		reinas();
	}

}
