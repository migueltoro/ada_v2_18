package us.lsi.pli.gurobi;



import static us.lsi.pli.AuxiliaryPLI.*;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import us.lsi.common.Files2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.ObjetoMochila;
import us.lsi.pli.AuxiliaryPLI;
import us.lsi.solve.AuxGrammar;

public class MochilaGurobiPLI {
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
	
	public static void mochila_constraint() throws IOException {
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;	
		CI = DatosMochila.capacidadInicial;
		objetos = DatosMochila.getObjetos();
		n = objetos.size();
		String ct = MochilaGurobiPLI.getConstraints(PLIType.Gurobi);
		Files2.toFile(ct,"ficheros/mochila_sal.lp");
		Locale.setDefault(new Locale("en", "US"));
		GurobiSolution solution = GurobiLp.gurobi("ficheros/mochila_sal.lp");
		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	public static void mochila_model() throws IOException {
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;	
		CI = DatosMochila.capacidadInicial;
		objetos = DatosMochila.getObjetos();
		n = objetos.size();
		AuxGrammar.generate(MochilaGurobiPLI.class,"models/mochila.lsi","ficheros/mochila.lp");
		GurobiSolution solution = GurobiLp.gurobi("ficheros/mochila.lp");
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	public static void main(String[] args) throws IOException {	
		mochila_model();
	}

}
