package us.lsi.gurobi;

import java.util.List;

import us.lsi.common.Files2;

import static us.lsi.gurobi.AuxiliaryGurobi.*;

public class TestGurobiAsignacion {
	
	private static Integer n;
	private static Integer m;
	private static Double[][] costes;
	
	
	public static void leeFichero(String f) {
		List<String> lineas = Files2.getLines(f);
		TestGurobiAsignacion.n = Integer.parseInt(lineas.get(0));
		TestGurobiAsignacion.m = Integer.parseInt(lineas.get(1));
		TestGurobiAsignacion.costes = new Double[n][m];
		String[] dat;
		Integer i, j;
		for (int k = 2; k < lineas.size(); k++) {
			dat = lineas.get(k).split(",");
			i = Integer.parseInt(dat[0].trim());
			j = Integer.parseInt(dat[1].trim());
			costes[i][j] = Double.parseDouble(dat[2].trim());
		}
	}

	
	public static String getConstraints() {	
		StringBuilder r = new StringBuilder();
		r.append(goalMin(sum_2_f(n,n,"x",(i,j)->costes[i][j].toString())));
		r.append("\nSubject To\n");
		r.append(forAll_1(n,i->constraintEq(sum_2_2_v(n,i,"x")," 1",i,"c")));
		r.append(forAll_1(n,j->constraintEq(sum_2_1_v(n,j,"x")," 1",j,"d")));
		r.append("\nBinary\n");
		r.append(vars(n,n,"x"));
		return r.toString();		
	}

	public static void main(String[] args) {
		TestGurobiAsignacion.leeFichero("ficheros/asignacionDeTareas.txt");
		String ct = TestGurobiAsignacion.getConstraints();
		Files2.toFile(ct,"ficheros/asignacionDeTareas_sal.pl");
	}

}
