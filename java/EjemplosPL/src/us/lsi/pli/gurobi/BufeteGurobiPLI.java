package us.lsi.pli.gurobi;

import java.io.IOException;
import java.util.Locale;
import java.util.stream.Collectors;

import us.lsi.bufete.datos.DatosAbogados;
import us.lsi.bufete.datos.SolucionAbogados;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class BufeteGurobiPLI {
	
	public static Integer getNumAbogados() {
		return DatosAbogados.NUM_ABOGADOS;
	}	

	public static Integer getNumCasos() {
		return DatosAbogados.NUM_CASOS;
	}
	
	public static Integer getHoras(Integer i, Integer j){
		return DatosAbogados.getHoras(i, j);
	}
	
	
	public static void main(String[] args) throws IOException {		
		Locale.setDefault(new Locale("es", "ES"));
		test("bufete.txt");		
//		test("PI6Ej2DatosEntrada2");
//		test("PI6Ej2DatosEntrada3");
	}

	public static void test(String fichero) throws IOException {
		Locale.setDefault(new Locale("es", "ES"));
		DatosAbogados.iniDatos("ficheros/"+fichero);
		AuxGrammar.generate(BufeteGurobiPLI.class,"models/bufete.lsi","ficheros/bufete.lp");
		GurobiSolution gs = GurobiLp.gurobi("ficheros/bufete.lp");		
		DatosAbogados.toConsole();
		SolucionAbogados.create(gs.objVal, gs.values).toConsole();
		System.out.println(gs.values);
		System.out.println(gs.values.entrySet().stream().filter(e->e.getKey().equals("T")).collect(Collectors.toList()));
		System.out.println(gs.values.get("T"));
	}
	
	
}

