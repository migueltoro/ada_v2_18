package us.lsi.reinas_test;

import java.io.IOException;
import java.util.Comparator;
import java.util.stream.Collectors;

import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.mochila_test.DataMochila;
import us.lsi.solve_test.AuxGrammar2;

public class TestAllInValues {
	
	public static void allInValues() throws IOException {
		AuxGrammar2.generate(DataMochila.class,"ficheros/allInValuesTest.lsi","ficheros/allInValuesTest.lp");
		GurobiSolution s = GurobiLp.solveSolution("ficheros/allInValuesTest.lp");
		System.out.println("\n\n\n\n");
		System.out.println(String.format("Objetivo : %.2f",s.objVal));
		System.out.println("\n\n");
		System.out.println(s.values.keySet()
				.stream()
				.filter(e->!e.contains("$"))
//				.filter(e->s.values.get(e)>0)
				.sorted(Comparator.naturalOrder())
				.map(e->String.format("%s == %.0f",e,s.values.get(e)))
				.collect(Collectors.joining("\n")));
	}
	
	public static void test1(String file) throws IOException {
		AuxGrammar2.generate(DataMochila.class,"ficheros/"+file+".lsi","ficheros/"+file+".lp");
		GurobiSolution s = GurobiLp.solveSolution("ficheros/"+file+".lp");
		System.out.println("\n\n\n\n");
		System.out.println(String.format("Objetivo : %.2f",s.objVal));
		System.out.println("\n\n");
		System.out.println(s.values.keySet()
				.stream()
				.filter(e->!e.contains("$"))
//				.filter(e->s.values.get(e)>0)
				.sorted(Comparator.naturalOrder())
				.map(e->String.format("%s == %.0f",e,s.values.get(e)))
				.collect(Collectors.joining("\n")));
	}

	public static void main(String[] args) throws IOException {
		test1("prueba_2");
	}

}
