package us.lsi.pli.lpsolve;

import java.util.Locale;
import us.lsi.common.Files2;
import us.lsi.lpsolve.AlgoritmoLpSolve;
import us.lsi.lpsolve.SolutionLpSolve;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.pli.AuxiliaryPLI;
import us.lsi.pli.gurobi.MochilaGurobiPLI;

public class MochilaLpSolvePLI{	
	
	

	public static void main(String[] args) {
		
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;	
		MochilaGurobiPLI.objetos = DatosMochila.getObjetos();
		MochilaGurobiPLI.n = MochilaGurobiPLI.objetos.size();
		MochilaGurobiPLI.CI = DatosMochila.capacidadInicial.toString();
		AuxiliaryPLI.setType(AuxiliaryPLI.TipoPLI.LPSolve);
		String ct = MochilaGurobiPLI.getConstraints();
		Files2.toFile(ct,"ficheros/mochila_sal.txt");
		Locale.setDefault(new Locale("en", "US"));
		SolutionLpSolve s = AlgoritmoLpSolve.getSolutionFromFile("ficheros/mochila_sal.txt");
		System.out.println("-------------------");	
		System.out.println("________");
		System.out.println(s.getGoal());
		System.out.println("________");
		System.out.println("________");
		for(int i=0;i<s.getNumVar();i++){
			if (s.getSolution(i) > 0) {
				System.out.println(s.getName(i) + " = " + s.getSolution(i));
			}
		}
	}

	
	
}
