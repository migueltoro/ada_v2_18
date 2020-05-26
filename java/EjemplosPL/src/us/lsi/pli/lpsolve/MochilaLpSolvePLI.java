package us.lsi.pli.lpsolve;

import static us.lsi.pli.AuxiliaryPLI.*;

import java.util.List;
import java.util.Locale;
import us.lsi.common.Files2;
import us.lsi.lpsolve.AlgoritmoLpSolve;
import us.lsi.lpsolve.SolutionLpSolve;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.ObjetoMochila;
import us.lsi.pli.AuxiliaryPLI;

public class MochilaLpSolvePLI{	
	
	public static String CI;
	public static List<ObjetoMochila>  objetos;
	public static Integer n;
	public static String getValor(Integer i) {
		return objetos.get(i).getValor().toString();
	}
	public static String getPeso(Integer i) {
		return objetos.get(i).getPeso().toString();
	}
	public static String getNMU(Integer i) {
		return objetos.get(i).getNumMaxDeUnidades().toString();
	}
	
	public static String getConstraints() {		
		StringBuilder r = new StringBuilder();
		r.append(max);
		r.append(goal(sum_1_f(n,"x",i->getValor(i))));
		r.append(constraintsSection);
		r.append(constraint("a",constraintLe(sum_1_f(n,"x",i->getPeso(i)),CI)));
		r.append(boundsSection);
		r.append(forAll_1_bound(n,i->boundLe(var_1("x",i),getNMU(i))));
		r.append(intVars);
		r.append(vars_1(n,"x"));
		return r.toString();	
	}
	
	
	public static void main(String[] args) {
		AuxiliaryPLI.setType(AuxiliaryPLI.TipoPLI.LPSolve);;
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;	
		MochilaLpSolvePLI.objetos = DatosMochila.getObjetos();
		MochilaLpSolvePLI.n = MochilaLpSolvePLI.objetos.size();
		CI = DatosMochila.capacidadInicial.toString();
		String ct = MochilaLpSolvePLI.getConstraints();
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
