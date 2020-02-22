package us.lsi.problemas.asignacion;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import us.lsi.pli.AlgoritmoPLI;
import us.lsi.pli.SolutionPLI;
import us.lsi.pli.HelpPLI;



public class AsignacionTareasPLI {

	public static Integer numeroDeReinas = 10;

	public static String getConstraints(AsignaciondeTareasRF a) {

		Integer n = a.getN();
		String r = "min: ";

		boolean first = true;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (first)
					first = false;
				else
					r = r + "+";
				// r = r + String.format("%.2f * x_%d_%d", a.getCoste(i,j),i, j).replaceAll(",",
				// ".");
				r = r + String.format("%s * x_%d_%d", String.valueOf(a.getCoste(i, j)), i, j);
			}
		}

		r = r + "\n\n";

		for (int i = 0; i < n; i++) {
			first = true;
			for (int j = 0; j < n; j++) {
				if (first)
					first = false;
				else
					r = r + "+";
				r = r + String.format("x_%d_%d", i, j);
			}
			r = r + "=";
			r = r + 1;
			r = r + ";\n";
		}

		r = r + "\n\n";

		for (int i = 0; i < n; i++) {
			first = true;
			for (int j = 0; j < n; j++) {
				if (first)
					first = false;
				else
					r = r + "+";
				r = r + String.format("x_%d_%d", j, i);
			}
			r = r + "=";
			r = r + 1;
			r = r + ";\n";
		}

		r = r + "\n\n";

		r = r + "bin ";

		first = true;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (first)
					first = false;
				else
					r = r + ",";
				r = r + String.format("x_%d_%d", i, j);
			}

		}

		r = r + ";\n\n";

		return r;
	}

	public static String getConstraints2(AsignaciondeTareasRF a) {

		String r = "";
		Integer n = a.getN();
		// r += allPairs(n, n).map(p -> String.format("%.2f *
		// x_%d_%d",a.getCoste(p.i,p.j), p.i, p.j).replaceAll(",", "."))
		r+= "min: "+HelpPLI.sum_2(n,n,p->true,p->a.getCoste(p.a, p.b),"id","; \n\n");

		r += IntStream.range(0, n).boxed().map(j -> HelpPLI.sum_2_i(j,n,"x"," = 1; \n")).collect(Collectors.joining("", "", "\n"));

		r += IntStream.range(0, n).boxed().map(i -> HelpPLI.sum_2_j(i,n,"x"," = 1; \n")).collect(Collectors.joining("", "", "\n"));

		r += HelpPLI.tipoVariables_2(n,n,"x","bin ");

		return r;
	}

	public static void main(String[] args) {
//			System.out.println(getConstraints());

		AsignaciondeTareasRF a = AsignaciondeTareasRF.create("ficheros/asignacionDeTareas.txt");

		System.out.println(getConstraints(a));
		System.out.println("________");
		System.out.println(getConstraints2(a));
		System.out.println("________");

		SolutionPLI s = AlgoritmoPLI.getSolution(getConstraints2(a));
		System.out.println("-------------------");
		System.out.println("________");
		System.out.println(s.getGoal());
		System.out.println("________");
		System.out.println("________");
		for (int i = 0; i < s.getNumVar(); i++) {
			if (s.getSolution(i) == 1.0)
				System.out.println(s.getName(i));
		}

	}

}
