package us.lsi.pli.lpsolve;

import static us.lsi.pli.AuxiliaryPLI.boundsSection;
import static us.lsi.pli.AuxiliaryPLI.constraintLe;
import static us.lsi.pli.AuxiliaryPLI.constraintsSection;
import static us.lsi.pli.AuxiliaryPLI.endSection;
import static us.lsi.pli.AuxiliaryPLI.f;
import static us.lsi.pli.AuxiliaryPLI.forAll;
import static us.lsi.pli.AuxiliaryPLI.goalMaxSection;
import static us.lsi.pli.AuxiliaryPLI.intVarsSection;
import static us.lsi.pli.AuxiliaryPLI.listOf;
import static us.lsi.pli.AuxiliaryPLI.sum;
import static us.lsi.pli.AuxiliaryPLI.v;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.lpsolve.AlgoritmoLpSolve;
import us.lsi.lpsolve.SolutionLpSolve;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.ObjetoMochila;
import us.lsi.pli.AuxiliaryPLI;
import us.lsi.pli.AuxiliaryPLI.PLIType;
import us.lsi.pli.gurobi.MochilaGurobiPLI;

public class MochilaLpSolvePLI{	
	
	public static Integer CI;
	public static List<ObjetoMochila>  objetos;
	public static int n;
	
	public static Integer getN() {
		return n;
	}
	public static Integer getCI() {
		return CI;
	}
	public static Integer getValor(Integer i) {
		return objetos.get(i).getValor();
	}
	public static Integer getPeso(Integer i) {
		return objetos.get(i).getPeso();
	}
	public static Integer getNMU(Integer i) {
		return objetos.get(i).getNumMaxDeUnidades();
	}
	
	public static String getConstraints(PLIType type) {
		AuxiliaryPLI.setType(type);
		StringBuilder r = new StringBuilder();
		r.append(goalMaxSection(sum(listOf(0,n,i->f(getValor(i),"x",i)))));
		r.append(constraintsSection());
		r.append(forAll("a",constraintLe(sum(listOf(0,n,i->f(getPeso(i),"x",i))),CI)));
		r.append(boundsSection(listOf(0,n,i->constraintLe(v("x",i),getNMU(i)))));
		r.append(intVarsSection(listOf(0,n,i->v("x",i))));
		r.append(endSection());
		return r.toString();	
	}

	public static void main(String[] args) {
		
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;	
		MochilaGurobiPLI.objetos = DatosMochila.getObjetos();
		MochilaGurobiPLI.n = MochilaGurobiPLI.objetos.size();
		MochilaGurobiPLI.CI = DatosMochila.capacidadInicial;
		String ct = getConstraints(PLIType.LPSolve);
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
