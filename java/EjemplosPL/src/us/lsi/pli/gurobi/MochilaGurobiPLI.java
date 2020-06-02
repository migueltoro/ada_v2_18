package us.lsi.pli.gurobi;



import static us.lsi.pli.AuxiliaryPLI.*;

import java.util.List;
import java.util.Locale;

import us.lsi.common.Files2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.ObjetoMochila;

public class MochilaGurobiPLI {
	public static Integer CI;
	public static List<ObjetoMochila>  objetos;
	public static Integer n;
	public static String getValor(Integer i) {
		return objetos.get(i).getValor().toString();
	}
	public static String getPeso(Integer i) {
		return objetos.get(i).getPeso().toString();
	}
	public static Integer getNMU(Integer i) {
		return objetos.get(i).getNumMaxDeUnidades();
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
		r.append(lastEnd);
		return r.toString();	
	}
	
	public static void main(String[] args) {	
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;	
		CI = DatosMochila.capacidadInicial;
		objetos = DatosMochila.getObjetos();
		n = objetos.size();
		String ct = MochilaGurobiPLI.getConstraints();
		Files2.toFile(ct,"ficheros/mochila_sal.lp");
		Locale.setDefault(new Locale("en", "US"));
		GurobiSolution solution = GurobiLp.gurobi("ficheros/mochila_sal.lp");
		System.out.println(solution.toString((s,d)->d>0.));
	}

}
