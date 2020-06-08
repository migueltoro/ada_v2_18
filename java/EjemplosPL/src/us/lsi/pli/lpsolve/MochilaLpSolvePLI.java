package us.lsi.pli.lpsolve;

import java.util.Locale;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.lpsolve.AlgoritmoLpSolve;
import us.lsi.lpsolve.SolutionLpSolve;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.pli.AuxiliaryPLI.PLIType;
import us.lsi.pli.gurobi.MochilaGurobiPLI;

public class MochilaLpSolvePLI{	
	
	

	public static void main(String[] args) {
		
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;	
		MochilaGurobiPLI.objetos = DatosMochila.getObjetos();
		MochilaGurobiPLI.n = MochilaGurobiPLI.objetos.size();
		MochilaGurobiPLI.CI = DatosMochila.capacidadInicial;
		String ct = MochilaGurobiPLI.getConstraints(PLIType.LPSolve);
		Files2.toFile(ct,"ficheros/mochila_sal.txt");
		Locale.setDefault(new Locale("en", "US"));
		SolutionLpSolve s = AlgoritmoLpSolve.getSolutionFromFile("ficheros/mochila_sal.txt");
		System.out.println(s.getGoal());
		System.out.println(s.solutions()
				.entrySet().stream()
				.filter(e->e.getKey().startsWith("x") && e.getValue()>0.)
				.map(e->String.format("%s = %.0f",e.getKey(),e.getValue()))
				.collect(Collectors.joining("\n")));
	}

	
	
}
