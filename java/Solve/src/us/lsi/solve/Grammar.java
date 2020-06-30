package us.lsi.solve;



import java.io.IOException;
import java.util.stream.Collectors;

import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;

public class Grammar {
	
	public static void mochila() throws IOException {
		DataMochila.iniMochila();
		AuxGrammar.generate(DataMochila.class,"ficheros/mochila.lsi","ficheros/mochila.lp");
		GurobiLp.solve("ficheros/mochila.lp");
	}
	
	public static void example() throws IOException {
		AuxGrammar.generate(DataMochila.class,"ficheros/model_3.txt","ficheros/model_3.lp");
		GurobiLp.solve("ficheros/model_3.lp");
	}
	
	public static void reinas() throws IOException {
		AuxGrammar.generate(DataMochila.class,"ficheros/reinas.lsi","ficheros/reinas.lp");
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
		reinas();
    }
	
}

